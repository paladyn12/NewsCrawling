package com.example.newsCrawling.service;

import com.example.newsCrawling.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public void crawlArtist() {
        String url = "https://www.kpop-radar.com/artistList";
        log.info("url={}", url);

        try {

            Document document = Jsoup.connect(url).get();
            log.info("document={}", document);


            Element listDiv = document.getElementById("list");
            log.info("listDiv={}", listDiv);

            if (listDiv != null && listDiv.hasClass("list-scroll")) {

                Elements categorySpans = listDiv.select("span.category.category_2 a");

                log.info("spans={}", categorySpans);

                for (Element link : categorySpans) {
                    String href = link.attr("href");
                    String name = link.text();
                    System.out.println("Href: " + href + ", Name: " + name);
                }
            } else {
                System.out.println("Target div not found or does not have the correct class.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
