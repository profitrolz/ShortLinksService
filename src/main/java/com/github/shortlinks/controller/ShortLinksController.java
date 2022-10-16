package com.github.shortlinks.controller;


import com.github.shortlinks.dto.LinkGenerateDto;
import com.github.shortlinks.dto.LinkResponseDto;
import com.github.shortlinks.dto.LinkStatDto;
import com.github.shortlinks.service.abstracts.LinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("")
public class ShortLinksController {

    private final LinkService linkService;

    public ShortLinksController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/generate")
    public ResponseEntity<LinkResponseDto> generate(@RequestBody LinkGenerateDto linkGenerateDto) {
        return ResponseEntity.ok(LinkResponseDto.builder().link(linkService.generateShortLink(linkGenerateDto.getOriginal())).build());
    }

    @GetMapping("/l/{shortLink}")
    public RedirectView redirectTo(@PathVariable String shortLink) {

        String redirectLink = linkService.getOriginalLinkByShort("/l/" + shortLink);
        return new RedirectView(redirectLink);

    }

    @GetMapping("/stats/{shortLink}")
    public ResponseEntity<LinkStatDto> getLinkStat(@PathVariable String shortLink) {
        return ResponseEntity.ok(linkService.getLinkStat("/l/" + shortLink));
    }


}
