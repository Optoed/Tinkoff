package org.example.dao.entity;

import jakarta.persistence.*;

@Entity(name = "urls")
public class UrlEntity {

    @Id
    @GeneratedValue(generator = "urls_id_seq")
    @SequenceGenerator(name = "urls_id_seq", sequenceName = "urls_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "longurl")
    private String longURL;
    @Column(name = "shorturl")
    private String shortURL;

    public UrlEntity() {}

    public UrlEntity(Long id, String longURL, String shortURL) {
        this.id = id;
        this.longURL = longURL;
        this.shortURL = shortURL;
    }

    public UrlEntity(String longURL, String shortURL) {
        this.longURL = longURL;
        this.shortURL = shortURL;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLongURL() {
        return this.longURL;
    }

    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }

    public String getShortURL() {
        return this.shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    public Long id() {
        return this.id;
    }

    public String longURL() {
        return this.longURL;
    }

    public String shortURL() {
        return this.shortURL;
    }
}
