package com.github.shortlinks.service.implementations;

import com.github.shortlinks.dao.abstracts.GenericDao;
import com.github.shortlinks.dao.abstracts.ShortLinkDao;
import com.github.shortlinks.dto.LinkStatDto;
import com.github.shortlinks.entity.ShortLink;
import com.github.shortlinks.service.abstracts.LinkGenerator;
import com.github.shortlinks.service.abstracts.LinkService;
import com.github.shortlinks.service.components.Cache;
import com.github.shortlinks.service.components.LruCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LinkServiceImpl implements LinkService {

    private static final String LINK_PREFIX = "/l/";

    private final ShortLinkDao shortLinkDao;

    private final LinkGenerator linkGenerator;
    private final Cache<String, String> cache;
    @Value("${app.shortlinklength}")
    private int linkLength;

    public LinkServiceImpl(ShortLinkDao shortLinkDao, LinkGenerator linkGenerator) {
        this.shortLinkDao = shortLinkDao;
        this.linkGenerator = linkGenerator;
        this.cache =  new LruCache(shortLinkDao::getOriginalLinkByShort);
    }

    @Override
    @Transactional
    public String generateShortLink(String originalLink) {

        String link =  shortLinkDao.getByOriginalLink(originalLink)
                .map(ShortLink::getShortLink)
                .orElseGet(() -> generateAndSaveShortLink(originalLink));
        return link;
    }

    @Override
    public String getOriginalLinkByShort(String shortLink) {
        return cache.readOrCompute(shortLink);
    }



    private String generateAndSaveShortLink(String originalLink) {
        String shortLink = LINK_PREFIX.concat(linkGenerator.generate(linkLength));
        shortLinkDao.create(ShortLink.builder().originalLink(originalLink).shortLink(shortLink).build());
        return shortLink;
    }

}
