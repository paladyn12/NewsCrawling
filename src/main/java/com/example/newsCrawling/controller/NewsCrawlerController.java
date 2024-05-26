package com.example.newsCrawling.controller;

import com.example.newsCrawling.service.NewsCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NewsCrawlerController {

    private final NewsCrawlerService newsCrawlerService;

    @GetMapping("/crawl")
    public String crawl() {
        newsCrawlerService.crawlNews();

        return "home";
    }
}
