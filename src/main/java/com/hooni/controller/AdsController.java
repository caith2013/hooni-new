package com.hooni.controller;

import com.hooni.db.AdCategory;
import com.hooni.db.Ads;
import com.hooni.repository.AdCategoryRepository;
import com.hooni.repository.AdsRepository;
import com.hooni.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/** Replaces the original AdsController + AdsReplyController. */
@Controller
@RequestMapping("/ads")
public class AdsController {
    private final AdsRepository adsRepo;
    private final AdCategoryRepository adCatRepo;

    public AdsController(AdsRepository adsRepo, AdCategoryRepository adCatRepo) { this.adsRepo = adsRepo; this.adCatRepo = adCatRepo; }

    @GetMapping
    public String adsList(HttpSession session, Model model) {
        model.addAttribute("HooniItems", adsRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, 20)));
        populateAdsByCategory(model);
        SessionUtils.setSessionAttribute(session, "currentPage", "ads");
        return "ads_home";
    }
    
    @GetMapping("/{id}")
    public String adsDetail(@PathVariable long id, HttpSession session, Model model) {
        model.addAttribute("ad", adsRepo.findById(id).orElse(null));
        SessionUtils.setSessionAttribute(session, "viewedAdId", id);
        return "ad_detail";
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
