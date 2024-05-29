package com.example.newsCrawling.service;

import com.example.newsCrawling.domain.entity.News;
import com.example.newsCrawling.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<News> crawlChart() {
        List<News> newsList = new ArrayList<>();
        String url = "https://www.melon.com/chart/index.htm";

        try {
            // 웹 페이지 가져오기
            Document doc = Jsoup.connect(url).get();

            // 필요한 요소 선택
            Elements element = doc.select("tbody tr.lst50, tbody tr.lst100");

            List<HashMap<String, Object>> list = new ArrayList<>();
            for (Element chartList : element) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("rank", chartList.select("span.rank").html());
                map.put("imgSrc", chartList.select("a.image_typeAll img").attr("src"));
                // 이미지 a 태그 href 에서 숫자만 출력 (앨범 번호)
                Elements albHref = chartList.select("a.image_typeAll");
                String[] albNumber = albHref.attr("href").split("\\D+");
                String albNo = "";
                for (String no : albNumber) {
                    if (!no.isEmpty()) {
                        albNo += no;
                    }
                }
                map.put("albNo", albNo);
                log.info(chartList.select("span.rank").html());
                log.info(albNo);

                list.add(map);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return newsList;
    }



}
