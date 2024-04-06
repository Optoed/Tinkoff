package org.example.service.model;

public record Url(String id, String longURL, String shortURL) {
    public Url(String longURL) {
        this(null, longURL, "");
    }

    public Url(String longURL, String shortURL) {
        this(null, longURL, shortURL);
    }
}
