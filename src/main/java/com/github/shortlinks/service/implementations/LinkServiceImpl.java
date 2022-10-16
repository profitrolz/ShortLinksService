package com.github.shortlinks.service.implementations;

import com.github.shortlinks.dao.abstracts.LinkDao;
import com.github.shortlinks.dto.LinkStatDto;
import com.github.shortlinks.entity.Link;
import com.github.shortlinks.exceptions.LinkNotFoundException;
import com.github.shortlinks.service.components.LinkGenerator;
import com.github.shortlinks.service.abstracts.LinkService;
import com.github.shortlinks.service.components.Cache;
import com.github.shortlinks.service.components.LruCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LinkServiceImpl implements LinkService {

    private static final String LINK_PREFIX = "/l/";

    private final LinkDao linkDao;
    private final LinkGenerator linkGenerator;
    private final Cache<String, Link> cache;
    @Value("${app.shortlinklength}")
    private int linkLength;


    public LinkServiceImpl(LinkDao linkDao, LinkGenerator linkGenerator) {
        this.linkDao = linkDao;
        this.linkGenerator = linkGenerator;
        this.cache =  new LruCache<>(this::getLinkByShortLink);
    }

    @Override
    @Transactional
    public String generateShortLink(String originalLink) {
        return linkDao.getLinkByOriginalLink(originalLink)
                .map(Link::getShortLink)
                .orElseGet(() -> generateAndSaveShortLink(originalLink));
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String getOriginalLinkByShort(String shortLink) {
        Link link =  cache.readOrCompute(shortLink);
        linkDao.increaseStat(link);
        return link.getOriginalLink();
    }

    @Override
    public LinkStatDto getLinkStat(String link) {
        return linkDao.getLinkStat(link);
    }

    private Link getLinkByShortLink(String shortLink) {
        return linkDao.getLinkByShortLink(shortLink).orElseThrow(LinkNotFoundException::new);
    }


    private String generateAndSaveShortLink(String originalLink) {
        String shortLink = LINK_PREFIX.concat(linkGenerator.generate(linkLength));
        linkDao.create(Link.builder().originalLink(originalLink).shortLink(shortLink).build());
        return shortLink;
    }

}
