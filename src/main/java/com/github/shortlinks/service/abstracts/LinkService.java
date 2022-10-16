package com.github.shortlinks.service.abstracts;

import com.github.shortlinks.dto.LinkStatDto;


public interface LinkService {
    String generateShortLink(String originalLink);

    String getOriginalLinkByShort(String shortLink);

    LinkStatDto getLinkStat(String link);


}
