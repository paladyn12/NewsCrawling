package com.example.newsCrawling.service;

import com.example.newsCrawling.domain.entity.News;
import com.example.newsCrawling.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsCrawlerService {

    private final NewsRepository newsRepository;

    public void crawlNews() {
        String url = "https://www.allkpop.com/?view=a&feed=a&sort=b";

        try {
            // 웹 페이지 가져오기
            Document doc = Jsoup.connect(url).get();

            // 필요한 요소 선택
            Elements element = doc.select("div.more_stories_scr article.list");

            for (Element newsList : element) {
                if(!newsRepository.existsByTitle(newsList.select("div.title").text())) {
                    String title = newsList.select("div.title").text();

                    Element aTag = newsList.selectFirst("a");
                    String link = aTag.attr("href");
                    String imgLink = aTag.selectFirst("img.b-lazy").attr("data-src");

                    News news = News.builder()
                            .title(title)
                            .link("https://www.allkpop.com" + link)
                            .imageLink(imgLink)
                            .build();

                    newsRepository.save(news);

                    log.info(String.valueOf(news));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Page<News> getNewsPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return newsRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

}
