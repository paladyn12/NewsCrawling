package com.example.newsCrawling.controller;

import com.example.newsCrawling.domain.entity.News;
import com.example.newsCrawling.service.NewsCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsCrawlerController {

    private final NewsCrawlerService newsCrawlerService;

    @GetMapping
    public String getNewsPage(@RequestParam(defaultValue = "0") int page, Model model) {
        int size = 6;
        newsCrawlerService.crawlNews();
        Page<News> newsPage = newsCrawlerService.getNewsPage(page, size);
        model.addAttribute("newsPage", newsPage);
        return "news";
    }
}
