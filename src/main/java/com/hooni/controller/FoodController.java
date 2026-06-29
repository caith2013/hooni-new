package com.hooni.controller;

import com.hooni.db.Food;
import com.hooni.db.User;
import com.hooni.db.UserFavoriteFood;
import com.hooni.repository.*;
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

    private static final int PAGE_SIZE = 10;

    private final FoodRepository        foodRepo;
    private final UserRepository        userRepo;
    private final UserFavoriteFoodRepository favRepo;

    public FoodController(FoodRepository foodRepo, UserRepository userRepo,
                          UserFavoriteFoodRepository favRepo) {
        this.foodRepo = foodRepo;
        this.userRepo = userRepo;
        this.favRepo  = favRepo;
    }

    // ── Food home (browse) ────────────────────────────────────────────────

    @GetMapping("/food")
    public String foodHome(@AuthenticationPrincipal UserDetails principal, Model model) {
        List<Food> foods = foodRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, PAGE_SIZE));
        model.addAttribute("HooniItems", foods);
        model.addAttribute("todayspecials", foodRepo.findTodaySpecials());
        if (principal != null) {
            model.addAttribute("ff", foodRepo.findFavoriteFoodsByUsername(principal.getUsername()));
        }
        return "foods_home";
    }

    // ── Food detail ───────────────────────────────────────────────────────

    @GetMapping("/foods")
    public String foodDetail(@RequestParam(name = "fid", required = false) Long fid,
                             @AuthenticationPrincipal UserDetails principal,
                             Model model) {
        if (fid != null) {
            Food food = foodRepo.findById(fid).orElse(null);
            model.addAttribute("theday", food);
            model.addAttribute("HooniItems", foodRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, PAGE_SIZE)));
            model.addAttribute("todayspecials", foodRepo.findTodaySpecials());
            if (principal != null) {
                model.addAttribute("ff", foodRepo.findFavoriteFoodsByUsername(principal.getUsername()));
            }
            return "food/foods_home";
        }
        // Show the "add recipe" form (requires login — enforced by SecurityConfig)
        return "food/foods";
    }

    // ── Add recipe (POST) ─────────────────────────────────────────────────

    @PostMapping("/foods")
    @Transactional
    public String addFood(@RequestParam String title,
                          @RequestParam String description,
                          @RequestParam(required = false) String foodType,
                          @RequestParam(name = "snap_shot", required = false) MultipartFile snapshot,
                          @AuthenticationPrincipal UserDetails principal,
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

        // TODO: persist snapshot to file system (port HooniFileSystem / HooniImage logic here)

        return "redirect:/foods?fid=" + saved.getId();
    }

    // ── Start cooking (step-by-step view) ────────────────────────────────

    @GetMapping("/startcooking")
    public String startCooking(@RequestParam(name = "fid") long fid, Model model) {
        Food food = foodRepo.findById(fid).orElse(null);
        model.addAttribute("food", food);
        return "food/start_cooking";
    }

    // ── Favorite toggle (AJAX) ────────────────────────────────────────────

    @PostMapping("/favoriteajax")
    @ResponseBody
    @Transactional
    public String toggleFavorite(@RequestParam(name = "fid") long fid,
                                 @AuthenticationPrincipal UserDetails principal) {
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
            return "removed";
        } else {
            UserFavoriteFood fav = new UserFavoriteFood();
            fav.setUser(user);
            fav.setFood(food);
            favRepo.save(fav);
            return "added";
        }
    }

    // ── Meal plan AJAX ────────────────────────────────────────────────────

    @PostMapping("/addtomealplanajax")
    @ResponseBody
    public String addToMealPlan(@RequestParam(name = "fid") long fid,
                                @AuthenticationPrincipal UserDetails principal) {
        if (principal == null) return "not_logged_in";
        // TODO: port MealsPlanCart logic
        return "ok";
    }

    @GetMapping("/mealplanajax")
    @ResponseBody
    public String getMealPlan(@AuthenticationPrincipal UserDetails principal) {
        if (principal == null) return "not_logged_in";
        // TODO: port MealsPlanCart logic
        return "[]";
    }
}
