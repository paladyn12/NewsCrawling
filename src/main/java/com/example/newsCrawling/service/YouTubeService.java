package com.example.newsCrawling.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.Future;

@Service
@Slf4j
public class YouTubeService {

    @Value("${youtube.api.key}")
    private String apiKey;
    private final String YOUTUBE_SEARCH_URL = "https://www.googleapis.com/youtube/v3/search";

    @Async
    public Future<String> searchVideo(String query) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI targetUrl = UriComponentsBuilder.fromUriString(YOUTUBE_SEARCH_URL)
                    .queryParam("part", "snippet")
                    .queryParam("q", query)
                    .queryParam("key", apiKey)
                    .queryParam("maxResults", 1)
                    .queryParam("type", "video")
                    .build()
                    .toUri();

            String response = restTemplate.getForObject(targetUrl, String.class);

            log.info("response={}", response);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            JsonNode itemsNode = rootNode.path("items");

            if (itemsNode.isArray() && !itemsNode.isEmpty()) {
                JsonNode firstItem = itemsNode.get(0);
                JsonNode idNode = firstItem.path("id");

                log.info("videoId={}", idNode.path("videoId").asText());

                return new AsyncResult<>(idNode.path("videoId").asText());
            }
        } catch (Exception e) {
            log.error("Failed to search video", e);
        }
        return new AsyncResult<>(null);
    }
}
