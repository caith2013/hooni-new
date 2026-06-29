package com.hooni.controller;

import com.hooni.repository.AdsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/** Replaces the original AdsController + AdsReplyController. */
@Controller
@RequestMapping("/ads")
public class AdsController {
    private final AdsRepository adsRepo;
    public AdsController(AdsRepository adsRepo) { this.adsRepo = adsRepo; }

    @GetMapping
    public String adsList(Model model) {
        model.addAttribute("HooniItems", adsRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, 20)));
        return "ads_home";
    }
    @GetMapping("/{id}")
    public String adsDetail(@PathVariable long id, Model model) {
        model.addAttribute("item", adsRepo.findById(id).orElse(null));
        return "ads_detail";
    }
}
