package org.example.controller.dto;

// Dto = Data transfer object
public record UrlDto(String id, String longURL, String shortURL) {

    public UrlDto(String longURL) {
        this(null, longURL, "");
    }

    public UrlDto(String longURL, String shortURL) {
        this(null, longURL, shortURL);
    }
}
