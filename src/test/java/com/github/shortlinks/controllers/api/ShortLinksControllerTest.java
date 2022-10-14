package com.github.shortlinks.controllers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.shortlinks.ShortLinksApplication;
import com.github.shortlinks.ShortLinksApplicationTests;
import com.github.shortlinks.dto.LinkGenerateDto;
import com.github.shortlinks.service.abstracts.LinkGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ShortLinksControllerTest extends ShortLinksApplicationTests {

    @MockBean
    private LinkGenerator linkGenerator;

    @Test
    void generateLik_getOk() throws Exception {
        Mockito.when(linkGenerator.generate(8)).thenReturn("abcdefgh");

        LinkGenerateDto linkGenerateDto = LinkGenerateDto.builder().original("http://some.original.link").build();
        mockMvc.perform(post("/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(linkGenerateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link").value("/l/abcdefgh"));
    }

    @Test
    void performShortLink_getOk() throws Exception {
        mockMvc.perform(
                        get("/l/abcdefgh"))
                .andExpect(redirectedUrl("http://some.original.link"));
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
    void getLinkStat_getOk() throws Exception {
        mockMvc.perform(get("/stats/abcdefgh"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.link").value("/l/abcdefgh)"))
                .andExpect(jsonPath("$.original").value("http://some.original.link"))
                .andExpect(jsonPath("$.rank").value(1))
                .andExpect(jsonPath("$.count").value(1));
    }

}
