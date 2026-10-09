package com.hooni.controller;

import com.hooni.db.Share;
import com.hooni.repository.ShareRepository;
import com.hooni.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;

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
    
    @PostMapping("/{id}/blog")
    public String createBlog(@PathVariable long id,
                             @RequestParam(name = "sid") long shareId,
                             @RequestParam(name = "blogging") String content,
                             @RequestParam(name = "news_picture1", required = false) MultipartFile picture1,
                             @RequestParam(name = "news_picture2", required = false) MultipartFile picture2,
                             HttpSession session) {
        Share share = shareRepo.findById(shareId).orElse(null);
        if (share == null) {
            return "redirect:/share";
        }
        
        // TODO: Create blog entry with content and pictures
        // TODO: Save pictures to disk with naming convention: {shareId}_{blogId}_{picIndex}_full.jpg
        
        SessionUtils.setSessionAttribute(session, "viewedShareId", shareId);
        return "redirect:/share/" + shareId;
    }
}
