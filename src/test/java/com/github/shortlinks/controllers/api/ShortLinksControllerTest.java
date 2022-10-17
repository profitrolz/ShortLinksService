package com.github.shortlinks.controllers.api;

import com.github.shortlinks.ShortLinksApplicationTests;
import com.github.shortlinks.dto.LinkGenerateDto;
import com.github.shortlinks.service.components.LinkGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql(scripts = "classpath:LinkStat.sql")
class ShortLinksControllerTest extends ShortLinksApplicationTests {

    @MockBean
    private LinkGenerator linkGenerator;

    @Test
    @Transactional
    @Rollback
    void generateLik_getOk() throws Exception {
        Mockito.when(linkGenerator.generate(8)).thenReturn("some_short_link");

        LinkGenerateDto linkGenerateDto = LinkGenerateDto.builder().original("http://some.original.link").build();
        mockMvc.perform(post("/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(linkGenerateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link").value("/l/some_short_link"));
    }

    @Test
    @Transactional
    @Rollback
    void performShortLink_getOk() throws Exception {

        mockMvc.perform(
                        get("/l/short_link1"))
                .andExpect(redirectedUrl("http://original.link1"));

        mockMvc.perform(get("/stats/short_link1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link").value("/l/short_link1"))
                .andExpect(jsonPath("$.original").value("http://original.link1"))
                .andExpect(jsonPath("$.rank").value(5))
                .andExpect(jsonPath("$.count").value(2));
    }

    @Test
    void performShortLink_get404() throws Exception {

        mockMvc.perform(
                        get("/l/some_nonexistence-ref"))
                .andExpect(status().isBadRequest());
    }


    /*
    {
“link”: “/l/some-short-name”,
“original”: “http://some-server.com/some/url”
“rank”: 1,
“count”: 100500
}

     */
    @Test
    void getLinkStat_lastRank_getOk() throws Exception {
        mockMvc.perform(get("/stats/short_link1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link").value("/l/short_link1"))
                .andExpect(jsonPath("$.original").value("http://original.link1"))
                .andExpect(jsonPath("$.rank").value(6))
                .andExpect(jsonPath("$.count").value(1));
    }

    @Test
    void getLinkStat_middleRank_getOk() throws Exception {
        mockMvc.perform(get("/stats/short_link3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link").value("/l/short_link3"))
                .andExpect(jsonPath("$.original").value("http://original.link3"))
                .andExpect(jsonPath("$.rank").value(4))
                .andExpect(jsonPath("$.count").value(3));
    }

    @Test
    void getLinkStat_duplicateCount_getOk() throws Exception {
        mockMvc.perform(get("/stats/short_link4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link").value("/l/short_link4"))
                .andExpect(jsonPath("$.original").value("http://original.link4"))
                .andExpect(jsonPath("$.rank").value(2))
                .andExpect(jsonPath("$.count").value(4));
    }

    @Test
    void getLinkStat_firstRank_getOk() throws Exception {
        mockMvc.perform(get("/stats/short_link6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link").value("/l/short_link6"))
                .andExpect(jsonPath("$.original").value("http://original.link6"))
                .andExpect(jsonPath("$.rank").value(1))
                .andExpect(jsonPath("$.count").value(5));
    }

    @Test
    void getAllStat_page1AndCount2_getOk() throws Exception {
        mockMvc.perform(get("/stats")
                        .param("page", "1")
                        .param("count", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].link").value("/l/short_link6"))
                .andExpect(jsonPath("$[0].original").value("http://original.link6"))
                .andExpect(jsonPath("$[0].rank").value(1))
                .andExpect(jsonPath("$[0].count").value(5))

                .andExpect(jsonPath("$[1].link").value("/l/short_link4"))
                .andExpect(jsonPath("$[1].original").value("http://original.link4"))
                .andExpect(jsonPath("$[1].rank").value(2))
                .andExpect(jsonPath("$[1].count").value(4));
    }

    @Test
    void getAllStat_page2AndCount2_getOk() throws Exception {
        mockMvc.perform(get("/stats")
                        .param("page", "2")
                        .param("count", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].link").value("/l/short_link5"))
                .andExpect(jsonPath("$[0].original").value("http://original.link5"))
                .andExpect(jsonPath("$[0].rank").value(2))
                .andExpect(jsonPath("$[0].count").value(4))


                .andExpect(jsonPath("$[1].link").value("/l/short_link3"))
                .andExpect(jsonPath("$[1].original").value("http://original.link3"))
                .andExpect(jsonPath("$[1].rank").value(4))
                .andExpect(jsonPath("$[1].count").value(3));
    }

    @Test
    void getAllStat_page1AndCount4_getOk() throws Exception {
        mockMvc.perform(get("/stats")
                        .param("page", "1")
                        .param("count", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))

                .andExpect(jsonPath("$[0].link").value("/l/short_link6"))
                .andExpect(jsonPath("$[0].original").value("http://original.link6"))
                .andExpect(jsonPath("$[0].rank").value(1))
                .andExpect(jsonPath("$[0].count").value(5))

                .andExpect(jsonPath("$[1].link").value("/l/short_link4"))
                .andExpect(jsonPath("$[1].original").value("http://original.link4"))
                .andExpect(jsonPath("$[1].rank").value(2))
                .andExpect(jsonPath("$[1].count").value(4))


                .andExpect(jsonPath("$[2].link").value("/l/short_link5"))
                .andExpect(jsonPath("$[2].original").value("http://original.link5"))
                .andExpect(jsonPath("$[2].rank").value(2))
                .andExpect(jsonPath("$[2].count").value(4))

                .andExpect(jsonPath("$[3].link").value("/l/short_link3"))
                .andExpect(jsonPath("$[3].original").value("http://original.link3"))
                .andExpect(jsonPath("$[3].rank").value(4))
                .andExpect(jsonPath("$[3].count").value(3));
    }

    @Test
    void getAllStat_default_getOk() throws Exception {
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6))

                .andExpect(jsonPath("$[0].link").value("/l/short_link6"))
                .andExpect(jsonPath("$[0].original").value("http://original.link6"))
                .andExpect(jsonPath("$[0].rank").value(1))
                .andExpect(jsonPath("$[0].count").value(5))

                .andExpect(jsonPath("$[1].link").value("/l/short_link4"))
                .andExpect(jsonPath("$[1].original").value("http://original.link4"))
                .andExpect(jsonPath("$[1].rank").value(2))
                .andExpect(jsonPath("$[1].count").value(4))


                .andExpect(jsonPath("$[2].link").value("/l/short_link5"))
                .andExpect(jsonPath("$[2].original").value("http://original.link5"))
                .andExpect(jsonPath("$[2].rank").value(2))
                .andExpect(jsonPath("$[2].count").value(4))

                .andExpect(jsonPath("$[3].link").value("/l/short_link3"))
                .andExpect(jsonPath("$[3].original").value("http://original.link3"))
                .andExpect(jsonPath("$[3].rank").value(4))
                .andExpect(jsonPath("$[3].count").value(3))

                .andExpect(jsonPath("$[4].link").value("/l/short_link2"))
                .andExpect(jsonPath("$[4].original").value("http://original.link2"))
                .andExpect(jsonPath("$[4].rank").value(5))
                .andExpect(jsonPath("$[4].count").value(2))


                .andExpect(jsonPath("$[5].link").value("/l/short_link1"))
                .andExpect(jsonPath("$[5].original").value("http://original.link1"))
                .andExpect(jsonPath("$[5].rank").value(6))
                .andExpect(jsonPath("$[5].count").value(1));
    }

}
