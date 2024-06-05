package com.example.newsCrawling.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SongDto {
    private String name;
    private String singer;
    private String album;
    private String imgLink;

}
