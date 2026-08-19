package com.hooni.controller;

import com.hooni.repository.ProductRepository;
import com.hooni.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Date;

/**
 * Populates template variables that every view needs.
 * Replaces the TemplateController.createTemplateBuilder() common attributes
 * (url, session, hosts, now, mealType, foodType, brands, categories, priceranges).
 */
@ControllerAdvice
public class GlobalModelAdvice {

    private final ProductRepository productRepo;

    public GlobalModelAdvice(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @ModelAttribute("now")
    public Date now() { return new Date(); }

    @ModelAttribute("brands")
    public Object brands() { return productRepo.findDistinctBrands(); }

    @ModelAttribute("categories")
    public Object categories() { return productRepo.findDistinctCategories(); }

    @ModelAttribute("loggedInUser")
    public String loggedInUser(@AuthenticationPrincipal UserDetails principal, HttpSession session) {
        // First check Spring Security context
        if (principal != null) {
            return principal.getUsername();
        }
        // Fallback to session (in case Security context wasn't properly set)
        return SessionUtils.getUserFromSession(session);
    }

    @ModelAttribute("mealType")
    public Object mealType() { return com.hooni.db.MealType.VALUES; }

    @ModelAttribute("foodType")
    public Object foodType() { return com.hooni.db.FoodType.VALUES; }
}
