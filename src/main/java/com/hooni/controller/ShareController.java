package com.hooni.controller;

import com.hooni.repository.ShareRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/** Replaces the original ShareController + BlogController. */
@Controller
@RequestMapping("/share")
public class ShareController {
    private final ShareRepository shareRepo;
    public ShareController(ShareRepository shareRepo) { this.shareRepo = shareRepo; }

    @GetMapping
    public String shares(Model model) {
        model.addAttribute("HooniItems", shareRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, 20)));
        return "shares_home";
    }
    @GetMapping("/{id}")
    public String shareDetail(@PathVariable long id, Model model) {
        model.addAttribute("share", shareRepo.findById(id).orElse(null));
        return "share_detail";
    }
}
