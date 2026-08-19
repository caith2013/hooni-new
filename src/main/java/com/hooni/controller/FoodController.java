package com.hooni.controller;

import com.hooni.db.*;
import com.hooni.repository.*;
import com.hooni.service.FoodService;
import com.hooni.service.MealsPlanService;
import com.hooni.util.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
        if (principal == null) {
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
    // ── Add recipe (POST) ─────────────────────────────────────────────────

    @PostMapping("/foods")
    @Transactional
    public String addFood(@RequestParam String title,
                          @RequestParam String description,
                          @RequestParam(required = false) String foodType,
                          @RequestParam(name = "snap_shot", required = false) MultipartFile snapshot,
                          @AuthenticationPrincipal UserDetails principal,
                          HttpSession session,
                          Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userRepo.findById(principal.getUsername()).orElseThrow();
        Food food = new Food(title, description, snapshot != null && !snapshot.isEmpty(), new Date(), new Date());
        food.setUser(user);
        if (foodType != null && !foodType.isEmpty()) {
            food.setKind(Integer.parseInt(foodType));
        }

        Food saved = foodRepo.save(food);
        
        SessionUtils.setSessionAttribute(session, "lastCreatedFoodId", saved.getId());
        SessionUtils.storeUserInSession(session, principal);

        // TODO: persist snapshot to file system (port HooniFileSystem / HooniImage logic here)

        return "redirect:/foods?fid=" + saved.getId();
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
}
