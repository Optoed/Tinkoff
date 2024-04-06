package org.example.repository.dao;

public record UrlDao(String id, String longURL, String shortURL) {

    public UrlDao(String longURL, String shortURL) {
        this(null, longURL, shortURL);
    }

    public UrlDao(String longURL) {
        this(null, longURL, "");
    }

}
