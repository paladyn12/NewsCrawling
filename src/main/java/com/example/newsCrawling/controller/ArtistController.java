package com.example.newsCrawling.controller;

import com.example.newsCrawling.service.ArtistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/artist")
@RequiredArgsConstructor
@Slf4j
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/crawl")
    public String crawlArtist() {
        log.info("controller 호출");
        artistService.crawlArtist();

        return "home";
    }
}
