package org.example.database;

import org.example.controller.dto.UrlDto;
import org.example.repository.dao.UrlDao;
import org.w3c.dom.css.Counter;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private static Database instance;
    private final Map<String, UrlDao> urlsMap;
    private final Map<String, UrlDao> shortUrlsIndexMap;
    private final Map<String, UrlDao> longUrlsIndexMap;

    private static long idCount = 0L;

    //Сервер для хранения URL-ссылок
    public static final String MyServer = "https://MyServer.com/";

    public String getMyServer() {
        return MyServer;
    }

    private Database() {
        urlsMap = new HashMap<>();
        shortUrlsIndexMap = new HashMap<>();
        longUrlsIndexMap = new HashMap<>();
    }

    //для обеспечения потокобезопасности при работе с общими данными в многопоточной среде.
    // Когда метод или блок кода помечается как synchronized,
    // это означает, что только один поток может выполнять этот метод или блок кода одновременно,
    // что предотвращает конфликты при доступе к общим данным.
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    //возможно static тут нужен
    public synchronized long getNextCounter() {
        idCount = idCount + 1L;
        return idCount;
    }

    public  synchronized long getCurrentCounter() {
        return idCount;
    }

    public UrlDao saveUrl(UrlDao urlDao) {
        String id = urlDao.id();
        String longUrl = urlDao.longURL();
        String shortUrl = urlDao.shortURL();

        getInstance().urlsMap.put(id, urlDao);
        getInstance().longUrlsIndexMap.put(longUrl, urlDao);
        getInstance().shortUrlsIndexMap.put(shortUrl, urlDao);

        return urlDao;
    }

    public UrlDao getUrlById(String id) {
        return getInstance().urlsMap.get(id);
    }

    public UrlDao getUrlByLongUrl(String longURL) {
        return getInstance().longUrlsIndexMap.get(longURL);
    }

    public UrlDao getUrlByShortUrl(String shortURL) {
        return getInstance().shortUrlsIndexMap.get(shortURL);
    }
}
