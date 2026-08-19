package com.hooni.controller;

import com.hooni.repository.ShareRepository;
import com.hooni.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
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
    public String shares(HttpSession session, Model model) {
        model.addAttribute("HooniItems", shareRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, 20)));
        SessionUtils.setSessionAttribute(session, "currentPage", "share");
        return "shares_home";
    }
    
    @GetMapping("/{id}")
    public String shareDetail(@PathVariable long id, HttpSession session, Model model) {
        model.addAttribute("share", shareRepo.findById(id).orElse(null));
        SessionUtils.setSessionAttribute(session, "viewedShareId", id);
        return "share_detail";
    }
}
