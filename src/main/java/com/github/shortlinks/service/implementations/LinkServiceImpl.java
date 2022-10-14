package com.github.shortlinks.service.implementations;

import com.github.shortlinks.dao.abstracts.GenericDao;
import com.github.shortlinks.dao.abstracts.ShortLinkDao;
import com.github.shortlinks.dto.LinkStatDto;
import com.github.shortlinks.entity.ShortLink;
import com.github.shortlinks.service.abstracts.LinkGenerator;
import com.github.shortlinks.service.abstracts.LinkService;
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
    @Value("${app.shortlinklength}")
    private int linkLength;

    public LinkServiceImpl(GenericDao<ShortLink, Long> genericDao, ShortLinkDao shortLinkDao, LinkGenerator linkGenerator) {
        this.shortLinkDao = shortLinkDao;
        this.linkGenerator = linkGenerator;
    }

    @Override
    @Transactional
    public String generateShortLink(String originalLink) {
        return shortLinkDao.getByOriginalLink(originalLink)
                .map(ShortLink::getShortLink)
                .orElseGet(() -> generateAndSaveShortLink(originalLink));
    }

    @Override
    public String getOriginalLinkByShort(String shortLink) {
        return shortLinkDao.getOriginalLinkByShort(shortLink);
    }


    private String generateAndSaveShortLink(String originalLink) {
        String shortLink = LINK_PREFIX.concat(linkGenerator.generate(linkLength));
        shortLinkDao.create(ShortLink.builder().originalLink(originalLink).shortLink(shortLink).build());
        return shortLink;
    }

}
