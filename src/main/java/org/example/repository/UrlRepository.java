package org.example.repository;

import org.example.repository.dao.UrlDao;

import java.sql.SQLException;
import java.util.Optional;

public interface UrlRepository {

    Optional<UrlDao> findUrlById(String id) throws SQLException;

    Optional<UrlDao> findUrlByLongUrl(String longURL) throws SQLException;

    Optional<UrlDao> findUrlByShortUrl(String shortUrl) throws SQLException;

    UrlDao save(UrlDao urlDao) throws SQLException;

    String getNextId() throws SQLException;

    String getCurrentId() throws SQLException;

}
