package org.example.service.model;

public record Cat(String id, String longURL, String shortURL) {
    public Cat(String longURL) {
        this(null, longURL, "");
    }

    public Cat(String longURL, String shortURL) {
        this(null, longURL, shortURL);
    }
}
