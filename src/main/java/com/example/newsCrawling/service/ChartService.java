package com.example.newsCrawling.service;

import com.example.newsCrawling.domain.dto.SongDto;
import com.example.newsCrawling.domain.entity.Song;
import com.example.newsCrawling.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChartService {

    private final SongRepository songRepository;

    public List<SongDto> fetchChart() {

        String url = "https://www.melon.com/chart/index.htm";
        List<SongDto> songList = new ArrayList<>();

        try {
            // 웹 페이지 가져오기
            Document doc = Jsoup.connect(url).get();
            Elements tmp;

            // 1~50위
            Elements element = doc.select("div#tb_list");

            for (int i=0; i<100; i++) {
                tmp = element.select("div.ellipsis.rank01");
                String name_temp = tmp.get(i).text();
                tmp = element.select("div.ellipsis.rank02").select("span");
                String singer_temp = tmp.get(i).text();
                tmp = element.select("div.ellipsis.rank03");
                String album_temp = tmp.get(i).text();
                String imgLink = element.select("a.image_typeAll img").get(i).attr("src");

                SongDto songDto = new SongDto(name_temp, singer_temp, album_temp, imgLink);
                songList.add(songDto);

                saveSong(songDto);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songList;
    }

    public void saveSong(SongDto songDto) {

        if (!songRepository.existsByNameAndSingerAndAlbum(songDto.getName(), songDto.getSinger(), songDto.getAlbum())) {
            Song song = Song.builder()
                    .name(songDto.getName())
                    .singer(songDto.getSinger())
                    .album(songDto.getAlbum())
                    .imgLink(songDto.getImgLink())
                    .build();

            songRepository.save(song);
        }
    }
}
