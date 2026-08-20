package com.hooni.controller;

import com.hooni.component.HooniFileSystem;
import com.hooni.db.*;
import com.hooni.repository.*;
import com.hooni.service.FoodService;
import com.hooni.service.MealsPlanService;
import com.hooni.util.HooniImage;
import com.hooni.util.MultipartFileItemWrapper;
import com.hooni.util.SessionUtils;
import com.hooni.component.UploadFileFormSystem;
import com.hooni.web.util.ImagePath;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.fileupload2.core.FileItem;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Replaces FoodsController + FoodHomeController.
 *
 * Original URLs (via ControllerUrl enum):
 *   /food  → FoodHomeController  (GET  — food landing page)
 *   /foods → FoodsController     (GET  — food detail / add recipe form)
 *             FoodsController     (POST — save new recipe with image upload)
 */
@Controller
public class FoodController {

    private static final int PAGE_SIZE = 30;
    public static final String SNAP_SHOT = "snap_shot";
    static final String STEP = "step";
    public static final String SNAP_STEP = "snap_step";

    private final FoodRepository        foodRepo;
    private final UserRepository        userRepo;
    private final UserFavoriteFoodRepository favRepo;

    private final FoodService foodService;
    private final ShareRepository        shareRepo;
    private final BlogRepository blogRepo;
    private final MealsPlanService mealPlanService;

    public FoodController(FoodRepository foodRepo, UserRepository userRepo,
                          UserFavoriteFoodRepository favRepo, FoodService foodService, ShareRepository shareRepo, BlogRepository blogRepo,
                          MealsPlanService mealPlanService) {
        this.foodRepo = foodRepo;
        this.userRepo = userRepo;
        this.favRepo  = favRepo;
        this.foodService = foodService;
        this.shareRepo = shareRepo;
        this.blogRepo = blogRepo;
        this.mealPlanService = mealPlanService;
    }

    // ── Food home (browse) ────────────────────────────────────────────────

    @GetMapping("/food")
    public String foodHome(@AuthenticationPrincipal UserDetails principal, HttpSession session, Model model) {
        List<Food> foods = foodRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, PAGE_SIZE));
        model.addAttribute("HooniItems", foods);
        model.addAttribute("todayspecials", foodRepo.findTodaySpecials());
        model.addAttribute("theday", foodService.getMyDay(foods));
        model.addAttribute("vblogs", blogRepo.findBlogsByShareId(20L));
        model.addAttribute("mblogs", blogRepo.findBlogsByShareId(21L));
        if (principal != null) {
            SessionUtils.storeUserInSession(session, principal);
            model.addAttribute("ff", foodRepo.findFavoriteFoodsByUsername(principal.getUsername()));
        }
        SessionUtils.setSessionAttribute(session, "currentPage", "food");
        return "foods_home";
    }

    // ── Food detail ───────────────────────────────────────────────────────

    @GetMapping("/foods")
    public String foodDetail(@RequestParam(name = "fid", required = false) Long fid,
                             @AuthenticationPrincipal UserDetails principal,
                             HttpSession session,
                             Model model) {
        if (fid != null) {
            Food food = foodRepo.findById(fid).orElse(null);
            model.addAttribute("theday", food);
            model.addAttribute("HooniItems", foodRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, PAGE_SIZE)));
            model.addAttribute("todayspecials", foodRepo.findTodaySpecials());
            model.addAttribute("vblogs", blogRepo.findBlogsByShareId(20L));
            model.addAttribute("mblogs", blogRepo.findBlogsByShareId(21L));
            if (principal != null) {
                SessionUtils.storeUserInSession(session, principal);
                model.addAttribute("ff", foodRepo.findFavoriteFoodsByUsername(principal.getUsername()));
            }
            SessionUtils.setSessionAttribute(session, "viewedFoodId", fid);
            return "foods_home";
        }
        // Show the "add recipe" form (requires login — enforced by SecurityConfig)
        return "redirect:/addrecipe";
    }

    @GetMapping("/addrecipe")
    public String addRecipeForm(@AuthenticationPrincipal UserDetails principal,
                                HttpSession session,
                                Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        SessionUtils.storeUserInSession(session, principal);
        model.addAttribute("mealType", MealType.VALUES);
        model.addAttribute("foodType", FoodType.values());
      //  model.addAttribute("todayspecials", foodRepo.findTodaySpecials());
        // model.addAttribute("vblogs", blogRepo.findBlogsByShareId(20L));
      //  model.addAttribute("mblogs", blogRepo.findBlogsByShareId(21L));
        return "foods";
    }

    // ── Add recipe (POST) ─────────────────────────────────────────────────

    @PostMapping("/addrecipe")
    @Transactional
    public String addFood(@RequestParam String title,
                          @RequestParam String description,
                          @RequestParam(required = false) String foodType,
                          @RequestPart(name = "snap_shot", required = false) MultipartFile snapshot,
                          @RequestParam Map<String, String> allParams,
                          @RequestPart(name = "step-images", required = false) MultipartFile[] stepImages,
                          @AuthenticationPrincipal UserDetails principal,
                          HttpSession session,
                          Model model) throws IOException {

        if (session.getAttribute("userDetails") == null) {
            return "redirect:/login";
        }

        if (principal == null) {
            principal = (UserDetails) session.getAttribute("userDetails");
        }

        User user = userRepo.findById(principal.getUsername()).orElseThrow();
        Food food = new Food(title, description, snapshot != null && !snapshot.isEmpty(), new Date(), new Date());
        food.setUser(user);
        if (foodType != null && !foodType.isEmpty()) {
            food.setKind(Integer.parseInt(foodType));
        }

        // Use allParams (contains all form fields) instead of parsing manually
        TreeMap<String, String> formFields = new TreeMap<>(allParams);
        setFoodProperties(food, formFields);
        
        // Process steps with form fields and uploaded files
        processFoodSteps(food, formFields, stepImages);
        Food saved = foodRepo.save(food);

        // Write files to file system
        if (writeToFileSystem(saved, snapshot, stepImages))
        {
            SessionUtils.setSessionAttribute(session, "lastCreatedFoodId", saved.getId());
            SessionUtils.storeUserInSession(session, principal);
            return "redirect:/foods?fid=" + saved.getId();

        }
        else
        {
            // Handle file system write failure (rollback transaction)
            HooniFileSystem.rollBack(_addedImages);
            throw new RuntimeException("Failed to write images to file system.");
        }

    }

    @GetMapping("/searchfoodajax")
    public String searchFoodAjax(HttpServletRequest request,
                             @AuthenticationPrincipal UserDetails principal,
                             HttpSession session,
                             Model model) {
        String[] mealType = request.getParameterValues("mealType");
        String foodType = request.getParameter("foodType");
        String[] keywords = request.getParameterValues("keywords");
        String menu = request.getParameter("menu");

        List<Food> foods = this.foodRepo.getFoods(mealType, foodType, keywords);
        model.addAttribute("HooniItems", foods);
        if (menu != null) {
            Food food = foodRepo.findById(Long.parseLong(menu)).orElse(null);
            model.addAttribute("theday", food);
            model.addAttribute("todayspecials", foodRepo.findTodaySpecials());
            model.addAttribute("vblogs", blogRepo.findBlogsByShareId(20L));
            model.addAttribute("mblogs", blogRepo.findBlogsByShareId(21L));
            if (principal != null) {
                SessionUtils.storeUserInSession(session, principal);
                model.addAttribute("ff", foodRepo.findFavoriteFoodsByUsername(principal.getUsername()));
            }
            SessionUtils.setSessionAttribute(session, "lastFoodSearch", foodType);
            return "foods_home";
        }
        // Show the "add recipe" form (requires login — enforced by SecurityConfig)
        return "search_food_ajax";
    }


    // ── Start cooking (step-by-step view) ────────────────────────────────

    @GetMapping("/startcooking")
    public String startCooking(HttpSession session, Model model) {
        model.addAttribute("todayspecials", foodRepo.findTodaySpecials());
        List<Food> meals = null;
        for(MealType mt : MealType.VALUES)
        {
            meals = mealPlanService.getFoodFromCookie(mealPlanService.getCookie(mt));
            if (meals != null)
            {
                model.addAttribute(mt.name().toLowerCase(), meals);
            }
        }
        SessionUtils.setSessionAttribute(session, "currentPage", "cooking");
        return "start_cooking";
    }

    // ── Favorite toggle (AJAX) ────────────────────────────────────────────

    @PostMapping("/favoriteajax")
    @ResponseBody
    @Transactional
    public String toggleFavorite(@RequestParam(name = "fid") long fid,
                                 @AuthenticationPrincipal UserDetails principal,
                                 HttpSession session) {
        if (principal == null) return "not_logged_in";

        User user = userRepo.findById(principal.getUsername()).orElseThrow();
        Food food = foodRepo.findById(fid).orElseThrow();

        var id = new UserFavoriteFood.UserFavoriteFoodId();
        // Check if already favorited and toggle
        List<UserFavoriteFood> existing = favRepo.findByUserUserName(principal.getUsername());
        boolean alreadyFav = existing.stream().anyMatch(f -> f.getFood().getId() == fid);

        if (alreadyFav) {
            existing.stream()
                    .filter(f -> f.getFood().getId() == fid)
                    .findFirst()
                    .ifPresent(favRepo::delete);
            SessionUtils.setSessionAttribute(session, "favoritesChanged", true);
            return "removed";
        } else {
            UserFavoriteFood fav = new UserFavoriteFood();
            fav.setUser(user);
            fav.setFood(food);
            favRepo.save(fav);
            SessionUtils.setSessionAttribute(session, "favoritesChanged", true);
            return "added";
        }
    }

    // ── Meal plan AJAX ────────────────────────────────────────────────────

    @PostMapping("/addtomealplanajax")
    public String addToMealPlan(@RequestParam(name = "fid") long fid,
                                @RequestParam(name = "title") String title,
                                @RequestParam(name = "mealtype") int mealtype,
                                @AuthenticationPrincipal UserDetails principal,
                                HttpSession session,
                                Model model) {
        //if (principal == null) return "not_logged_in";
        MealType mtype = MealType.values()[mealtype];
        mealPlanService.addToCookie(mtype, fid, title);

        model.addAttribute("foodSimple", new FoodSimple(fid, title));
        model.addAttribute("mtype", mealtype);
        SessionUtils.setSessionAttribute(session, "mealPlanModified", true);
        return "menu_food";
    }

    @GetMapping("/mealplanajax")
    public String getMealPlan(@AuthenticationPrincipal UserDetails principal, HttpSession session, Model model) {
        //if (principal == null) return "not_logged_in";

        for(MealType mt : MealType.VALUES)
        {
            model.addAttribute(mt.name().toLowerCase(), mealPlanService.getFoodSimpleFromCookie(mealPlanService.getCookie(mt).getValue()).values());
        }
        
        SessionUtils.setSessionAttribute(session, "mealPlanViewed", true);
        return "meal_plan";
    }

    private void setFoodProperties(Food food, TreeMap<String,String> formFields)
    {
        if (formFields.get(FIELD_FOOD_TYPE) != null)
        {
            food.setKind(Integer.parseInt(formFields.get(FIELD_FOOD_TYPE)));
        }
        else
        {
            food.setKind(FoodType.ALL.ordinal());
        }

        for (MealType mtype : MealType.VALUES)
        {
            setMealType(food, formFields.get(mtype.name().toLowerCase()));
        }

        for (int i=0; i < 6; i++)
        {
            String kword = formFields.get(FIELD_KEYWORD+i+"");
            if (!kword.isEmpty())
            {
                FoodKeyword keyword = new FoodKeyword(kword);
                food.addFoodKeyword(keyword);

            }
        }
    }
    private void setMealType(Food food, String mealName)
    {
        if (mealName != null && !mealName.isEmpty())
        {
            FoodGoodFor goodfor = new FoodGoodFor();
            goodfor.setMealType(Integer.parseInt(mealName));
            //goodfor.setFood(food);
            food.addFoodGoodFor(goodfor);
        }
    }
    private void processFoodSteps(Food food, TreeMap<String, String> formFields, MultipartFile[] images)
    {
        for(String fieldName : formFields.keySet())
        {
            if (fieldName.contains(STEP))
            {
                int stepIndex = Integer.parseInt(fieldName.replace(STEP, "")) - 1;
                boolean hasImage = images.length > 0 && images[stepIndex] != null && !images[stepIndex].isEmpty(); // Simple check: if images provided, assume step has images
                FoodStep fstep = new FoodStep(formFields.get(fieldName), hasImage);
                fstep.setFood(food);
                food.addFoodStep(fstep);
            }
        }
    }
    private boolean writeToFileSystem(Food food, MultipartFile snapshot, MultipartFile[] images) throws IOException {
        if ((snapshot == null || snapshot.isEmpty()) && (images == null || images.length == 0)) {
            return true; // No images to write
        }

        HooniFileSystem fs = new HooniFileSystem(ImagePath.FOOD);
        
        // Write snapshot if available
        if (snapshot != null && !snapshot.isEmpty()) {
            HooniImage hi = new HooniImage(new MultipartFileItemWrapper(snapshot), food.getId()+"", ImagePath.FOOD);
            if (!fs.writeToFileSystem(hi)) return false;
            _addedImages.add(hi);
        }

        // Write remaining images as step images
        java.util.ArrayList<FoodStep> steps = new java.util.ArrayList<>(food.getFoodSteps());
        for (int stepIndex = 0; stepIndex < steps.size(); stepIndex++)
        {
            FoodStep fstep = steps.get(stepIndex);
            if (fstep.getHasPicture())
            {
                HooniImage hi = new HooniImage(new MultipartFileItemWrapper(images[stepIndex]), food.getId()+"_"+fstep.getId(), ImagePath.FOOD);
                if (!fs.writeToFileSystem(hi)) return false;
                _addedImages.add(hi);
            }
        }
        return true;
    }

    private ArrayList<HooniImage> _addedImages = new ArrayList<HooniImage>();

    static final String FIELD_TITLE = "title";
    static final String FIELD_DESCRIPTION = "description";
    static final String FOOD_ID = "fid";
    static final String FIELD_FOOD_TYPE = "foodType";
    static final String FIELD_BREAKFAST = "breakfast";
    static final String FIELD_LUNCH = "lunch";
    static final String FIELD_DINNER = "dinner";
    static final String FIELD_KEYWORD = "word";

}
