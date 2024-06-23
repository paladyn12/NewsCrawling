package com.example.newsCrawling.controller;

import com.example.newsCrawling.domain.dto.SongDto;
import com.example.newsCrawling.service.ChartService;
import com.example.newsCrawling.service.YouTubeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chart")
@Slf4j
public class ChartController {

    private final ChartService chartService;
    private final YouTubeService youTubeService;

    @GetMapping
    public String getChart(Model model) {
        List<SongDto> chart = chartService.fetchChart();
        model.addAttribute("chart", chart);
        return "chart";
    }

    @PostMapping("/play")
    @ResponseBody
    public Map<String, String> playSong(@RequestBody SongDto songDto) throws Exception {
        String query = songDto.getName() + " " + songDto.getSinger();
        String videoId = String.valueOf(youTubeService.searchVideo(query));

        Map<String, String> response = new HashMap<>();
        if (videoId != null) {
            response.put("videoId", videoId);
        }

        return response;
    }
}