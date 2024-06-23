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
import java.util.concurrent.Future;

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
    public Map<String, String> playSong(@RequestBody SongDto songDto) {
        Map<String, String> response = new HashMap<>();
        try {
            String query = songDto.getName() + " " + songDto.getSinger();
            Future<String> futureVideoId = youTubeService.searchVideo(query);
            String videoId = futureVideoId.get();

            log.info("videoId in Controller={}", videoId);

            if (videoId != null) {
                response.put("videoId", videoId);
            } else {
                response.put("error", "Video not found");
            }
        } catch (Exception e) {
            log.error("Error occurred while searching for video", e);
            response.put("error", "An error occurred");
        }
        return response;
    }
}