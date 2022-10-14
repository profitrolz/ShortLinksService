package com.github.shortlinks.service.abstracts;

import com.github.shortlinks.dto.LinkStatDto;

import java.util.List;

public interface LinkService {
    String generateShortLink(String originalLink);

    String getOriginalLinkByShort(String shortLink);

}
