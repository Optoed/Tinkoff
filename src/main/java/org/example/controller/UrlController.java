package org.example.controller;

import org.example.controller.dto.UrlDto;
import org.example.exception.EntityNotFoundException;
import org.example.service.UrlService;
import org.example.service.model.Url;

public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    public String addUrl(UrlDto urlDto) {
        return urlService.addUrl(new Url(urlDto.longURL()));
    }

    public UrlDto getUrl(String id) throws EntityNotFoundException {
        Url url = urlService.findUrl(id);
        return new UrlDto(url.id(), url.longURL(), url.shortURL());
    }
}
