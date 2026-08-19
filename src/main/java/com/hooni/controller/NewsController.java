package com.hooni.controller;

import com.hooni.repository.NewsRepository;
import com.hooni.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/** Replaces the original NewsController. */
@Controller
@RequestMapping("/news")
public class NewsController {
    private final NewsRepository newsRepo;
    public NewsController(NewsRepository newsRepo) { this.newsRepo = newsRepo; }

    @GetMapping
    public String news(HttpSession session, Model model) {
        SessionUtils.setSessionAttribute(session, "currentPage", "news");
        model.addAttribute("HooniItems", newsRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, 20)));
        return "news_home";
    }
    
    @GetMapping("/{id}")
    public String newsDetail(@PathVariable long id, HttpSession session, Model model) {
        SessionUtils.setSessionAttribute(session, "viewedNewsId", id);
        model.addAttribute("news", newsRepo.findById(id).orElse(null));
        return "news_detail";
    }
}
