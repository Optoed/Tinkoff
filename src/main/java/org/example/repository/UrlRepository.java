package org.example.repository;

import org.example.repository.dao.UrlDao;

import java.util.Optional;

public interface UrlRepository {

    Optional<UrlDao> findUrlById(String id);

    String save(UrlDao urlDao);
}
