package com.hooni.controller;

import com.hooni.repository.NewsRepository;
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
    public String news(Model model) {
        model.addAttribute("HooniItems", newsRepo.findAllByOrderByTimeCreatedDesc(PageRequest.of(0, 20)));
        return "news_home";
    }
    @GetMapping("/{id}")
    public String newsDetail(@PathVariable long id, Model model) {
        model.addAttribute("item", newsRepo.findById(id).orElse(null));
        return "news_detail";
    }
}
