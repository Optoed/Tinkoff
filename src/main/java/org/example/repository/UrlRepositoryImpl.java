package org.example.repository;

import org.example.database.Database;
import org.example.repository.dao.UrlDao;

import java.util.Optional;

public class UrlRepositoryImpl implements UrlRepository {

    private static final Database dataBase = Database.getInstance();

    @Override
    public Optional<UrlDao> findUrlById(String id) {
        return Optional.ofNullable(dataBase.getUrlById(id));
    }

    //поиск по longURL
    @Override
    public Optional<UrlDao> findUrlByLongUrl(String longURL) {
        return Optional.ofNullable(dataBase.getUrlByLongUrl(longURL));
    }

    //поиск по shortURL
    @Override
    public Optional<UrlDao> findUrlByShortUrl(String shortURL) {
        return Optional.ofNullable(dataBase.getUrlByShortUrl(shortURL));
    }

    @Override
    public UrlDao save(UrlDao urlDao) {
        return dataBase.saveUrl(urlDao);
    }

    @Override
    public String getNextId() {
        long nextId = dataBase.getNextCounter();
        return String.valueOf(nextId);
    }

    @Override
    public String getCurrentId() {
        long currentId = dataBase.getCurrentCounter();
        return String.valueOf(currentId);
    }
}
