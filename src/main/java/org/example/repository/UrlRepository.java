package org.example.repository;

import org.example.repository.dao.UrlDao;

import java.util.Optional;

public interface UrlRepository {

    Optional<UrlDao> findUrlById(String id);

    Optional<UrlDao> findUrlByLongUrl(String longURL);

    Optional<UrlDao> findUrlByShortUrl(String shortUrl);

    UrlDao save(UrlDao urlDao);
}
