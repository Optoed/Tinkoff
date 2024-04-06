package org.example.repository.dao;

public record CatDao(String id, String longURL, String shortURL) {

    public CatDao(String longURL, String shortURL) {
        this(null, longURL, shortURL);
    }

    public CatDao(String longURL) {
        this(null, longURL, "");
    }

}
