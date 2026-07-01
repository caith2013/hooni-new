package com.hooni.controller;

import com.hooni.db.AdCategory;
import com.hooni.db.Ads;
import com.hooni.db.IpAddress;
import com.hooni.db.Product;
import com.hooni.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Replaces HomePageController + ControllerUrl.WWW dispatcher.
 *
 * Original URL: /www?op={hooni|news|foods|products|ads|shares}
 * Spring Boot:  GET /www?op={hooni|news|foods|products|ads|shares}
 */
@Controller
public class HomeController {

    private final ProductRepository  productRepo;
    private final NewsRepository     newsRepo;
    private final FoodRepository     foodRepo;
    private final AdsRepository      adsRepo;
    private final ShareRepository    shareRepo;
    private final AdCategoryRepository adCatRepo;

    public HomeController(ProductRepository productRepo, NewsRepository newsRepo,
                          FoodRepository foodRepo, AdsRepository adsRepo,
                          ShareRepository shareRepo, AdCategoryRepository adCatRepo) {
        this.productRepo = productRepo;
        this.newsRepo    = newsRepo;
        this.foodRepo    = foodRepo;
        this.adsRepo     = adsRepo;
        this.shareRepo   = shareRepo;
        this.adCatRepo   = adCatRepo;
    }

    /** Root "/" — redirect to /www?op=hooni (same as original) */
    @GetMapping("/")
    public String root() {
        return "redirect:/www?op=hooni";
    }

    @GetMapping("/www")
    public String home(@RequestParam(name = "op", defaultValue = "products") String op,
                       @AuthenticationPrincipal UserDetails principal,
                       Model model, HttpServletRequest request) {

        populateCommonModel(model, principal);

        switch (op.toUpperCase()) {
            case "NEWS" -> {
                model.addAttribute("HooniItems", newsRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, 20)));
                return "news_home";
            }
            case "FOODS" -> {
                // Original redirected to food.hooni.org; map to local food home instead
                return "redirect:/food";
            }
            case "PRODUCTS" -> {
                Pageable pageable = PageRequest.of(0, 50);
                Page<Product> page = productRepo.findAllProducts(pageable);
                model.addAttribute("HooniItems", page.getContent());
                return "products_home";
            }
            case "ADS" -> {
                populateAdsByCategory(model);
                return "ads_home";
            }
            case "SHARES" -> {
                model.addAttribute("HooniItems", shareRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, 20)));
                return "shares_home";
            }
            case "HOONI" -> {
                model.addAttribute("products", productRepo.findAll(PageRequest.of(0, 20)).getContent());
                model.addAttribute("news",     newsRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, 20)));
                model.addAttribute("foods",    foodRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, 20)));
                model.addAttribute("ads",      adsRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, 20)));
                model.addAttribute("shares",   shareRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, 20)));
                return "hooni_home";
            }
            case "INDEX" -> {
                model.addAttribute("name", "Thomas");
                return "index";
            }
            default -> {
                model.addAttribute("HooniItems", productRepo.findAll());
                return "products_home";
            }
        }
    }

    // ── helpers ──────────────────────────────────────────────────────────

    private void populateCommonModel(Model model, UserDetails principal) {
        if (principal != null) {
            model.addAttribute("loggedInUser", principal.getUsername());
        }
        model.addAttribute("brands",      productRepo.findDistinctBrands());
        model.addAttribute("categories",  productRepo.findDistinctCategories());
    }

    private void populateAdsByCategory(Model model) {
        var adsByCat = new java.util.TreeMap<String, List<String>>();
        for (AdCategory cat : adCatRepo.findAll()) {
            List<Ads> ads = adsRepo.findByAdcat(cat);
            adsByCat.put(cat.getName(),
                ads.stream()
                   .flatMap(a -> a.getAdKeywords().stream().map(k -> k.getKeyword()))
                   .limit(20)
                   .toList());
        }
        model.addAttribute("adsByCat", adsByCat);
        model.addAttribute("max", 20);
    }

}
