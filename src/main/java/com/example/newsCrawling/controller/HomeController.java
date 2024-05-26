package com.example.newsCrawling.controller;

import com.example.newsCrawling.domain.entity.News;
import com.example.newsCrawling.repository.NewsRepository;
import com.example.newsCrawling.service.NewsCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final NewsRepository newsRepository;
    private final NewsCrawlerService newsCrawlerService;

    @GetMapping
    public String home(Model model) {
        newsCrawlerService.crawlNews();
        List<News> newsList = newsRepository.findAll();

        model.addAttribute("newsList", newsList);
        return "home";
    }
}
