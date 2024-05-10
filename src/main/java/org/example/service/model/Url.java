package org.example.service.model;

import org.example.controller.dto.UrlDto;

public record Url(Long id, String longURL, String shortURL) {
    public Url(String longURL) {
        this(null, longURL, "");
    }

    public Url(String longURL, String shortURL) {
        this(null, longURL, shortURL);
    }

    public Url(UrlDto urlDto) {
        this (urlDto.id(), urlDto.longURL(), urlDto.shortURL());
    }
}
