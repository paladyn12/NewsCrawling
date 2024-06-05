package com.example.newsCrawling.repository;

import com.example.newsCrawling.domain.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
    boolean existsByNameAndSingerAndAlbum(String name, String singer, String album);
}
