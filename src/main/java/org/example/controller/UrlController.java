package org.example.controller;

import org.example.controller.dto.UrlDto;
import org.example.exception.EntityNotFoundException;
import org.example.service.UrlService;
import org.example.service.model.Url;

import java.sql.SQLException;

public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    public UrlDto addUrl(UrlDto urlDto) throws SQLException {
        Url addedUrl =  urlService.addUrl(new Url(urlDto.longURL()));
        return new UrlDto(addedUrl.id(), addedUrl.longURL(), addedUrl.shortURL());
    }

    //получаем по shortURL или по longURL => сразу по urlDto
    public UrlDto getUrl(UrlDto urlDto) throws EntityNotFoundException, SQLException {
        Url url = urlService.findUrl(new Url(urlDto));
        return new UrlDto(url.id(), url.longURL(), url.shortURL());
    }
}
