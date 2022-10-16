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
    void performShortLink_getOk() throws Exception {

        mockMvc.perform(
                        get("/l/short_link1"))
                .andExpect(redirectedUrl("http://original.link1"));
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


}
