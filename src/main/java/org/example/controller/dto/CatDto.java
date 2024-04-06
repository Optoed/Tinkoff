package org.example.controller.dto;

// Dto = Data transfer object
public record CatDto(String id, String longURL, String shortURL) {

    public CatDto(String longURL) {
        this(null, longURL, "");
    }

    public CatDto(String longURL, String shortURL) {
        this(null, longURL, shortURL);
    }
}
