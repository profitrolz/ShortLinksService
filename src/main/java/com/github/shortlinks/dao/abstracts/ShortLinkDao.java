package com.github.shortlinks.dao.abstracts;

import com.github.shortlinks.entity.ShortLink;

import java.util.Optional;

public interface ShortLinkDao extends GenericDao<ShortLink, Long> {
    Optional<ShortLink> getByOriginalLink(String originalLink);

    String getOriginalLinkByShort(String shortLink);

}
