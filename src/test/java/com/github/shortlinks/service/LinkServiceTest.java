package com.github.shortlinks.service;


import com.github.shortlinks.ShortLinksApplicationTests;
import com.github.shortlinks.dao.abstracts.LinkDao;
import com.github.shortlinks.entity.Link;
import com.github.shortlinks.service.components.LinkGenerator;
import com.github.shortlinks.service.abstracts.LinkService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

class LinkServiceTest extends ShortLinksApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private LinkGenerator linkGenerator;

    @MockBean
    LinkDao linkDao;

    @Test
    void getOriginalLinkByShort_shouldReturnFromCache_twoSameLinks() {

        Mockito.when(linkGenerator.generate(8)).thenReturn("abcdefgh");

        LinkService linkService = applicationContext.getBean(LinkService.class);
        String originalLink = "http://orig.link";

        String shortLink = linkService.generateShortLink(originalLink);
        Mockito.when(linkDao.getLinkByShortLink(shortLink)).thenReturn(Optional.of(Link.builder().shortLink("/l/abcdefgh").originalLink("http://orig.link").build()));
        String newOriginalLink1 = linkService.getOriginalLinkByShort(shortLink);
        //Mockito.when(shortLinkDao.getLinkMatchingByShortLink(shortLink)).thenReturn(Optional.of(LinkMatchingEntity.builder().shortLink("/l/abcdefgh").originalLink("http://orig.link").build()));

        String newOriginalLink2 = linkService.getOriginalLinkByShort(shortLink);
        Assertions.assertSame(newOriginalLink1, newOriginalLink2);
    }

}
