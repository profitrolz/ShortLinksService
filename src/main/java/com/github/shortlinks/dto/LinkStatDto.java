package com.github.shortlinks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkStatDto {

    private String link;

    private String original;

    private long rank;

    private long count;

}
