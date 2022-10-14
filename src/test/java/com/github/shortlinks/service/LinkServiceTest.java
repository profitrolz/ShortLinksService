package com.github.shortlinks.service;


import com.github.shortlinks.ShortLinksApplicationTests;
import com.github.shortlinks.dao.abstracts.ShortLinkDao;
import com.github.shortlinks.service.abstracts.LinkService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

public class LinkServiceTest extends ShortLinksApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    ShortLinkDao shortLinkDao;

    @Test
    void getOriginalLinkByShort_shouldReturnFromCache_twoSameLinks() {

        LinkService linkService = applicationContext.getBean(LinkService.class);
        String originalLink = "http://orig.link";
        String shortLink = linkService.generateShortLink(originalLink);
        Mockito.when(shortLinkDao.getOriginalLinkByShort(shortLink)).thenReturn("http://orig.link");
        linkService.getOriginalLinkByShort(shortLink);
        Mockito.when(shortLinkDao.getOriginalLinkByShort(shortLink)).thenReturn("http://fake.link");
        String newOriginalLink = linkService.getOriginalLinkByShort(shortLink);
        Assertions.assertSame(originalLink, newOriginalLink);
    }

}
