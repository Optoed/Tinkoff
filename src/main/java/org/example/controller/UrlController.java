package org.example.controller;

import org.example.controller.dto.UrlDto;

import org.example.controller.dto.request.UrlCreationRequest;
import org.example.controller.dto.response.UrlResponse;
import org.example.controller.dto.response.UrlCreationResponse;

import org.example.exception.EntityNotFoundException;

import org.example.service.UrlService;
import org.example.service.model.Url;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/url")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService)  {
        this.urlService = urlService;
    }

    @PostMapping(value = "/create")
    public UrlCreationResponse addUrl(@RequestBody UrlCreationRequest urlDto) throws SQLException {
        Url addedUrl =  urlService.addUrl(new Url(urlDto.longURL()));
        return new UrlCreationResponse(addedUrl.id(), addedUrl.longURL(), addedUrl.shortURL());
    }

    @GetMapping("/{shortURL}")
    public UrlResponse getUrl(@PathVariable("shortURL") String shortURL) throws EntityNotFoundException, SQLException {
        if (shortURL == null) {
            throw new EntityNotFoundException("Short URL is null");
        }
        Url url = urlService.findUrl(shortURL);
        return new UrlResponse(url.id(), url.longURL(), url.shortURL());
    }
}
