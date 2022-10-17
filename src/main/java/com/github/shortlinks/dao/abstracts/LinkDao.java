package com.github.shortlinks.dao.abstracts;

import com.github.shortlinks.dto.LinkStatDto;
import com.github.shortlinks.entity.Link;

import java.util.Optional;

public interface LinkDao extends GenericDao<Link, Long> {
    Optional<Link> getLinkByOriginalLink(String originalLink);

    Optional<Link> getLinkByShortLink(String shortLink);

    void increaseStat(Link link);

    LinkStatDto getLinkStat(String link);

}
