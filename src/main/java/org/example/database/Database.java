package org.example.database;

import org.example.repository.dao.UrlDao;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private static Database instance;
    private final Map<String, UrlDao> urlsMap;

    //Сервер для хранения URL-ссылок
    public static final String MyServer = "https://MyServer.com/";

    private Database() {
        urlsMap = new HashMap<>();
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

    public String saveUrl(UrlDao urlDao) {
        String id = urlDao.id();
        getInstance().urlsMap.put(id, urlDao);
        return id;
    }

    public UrlDao getUrl(String id) {
        return getInstance().urlsMap.get(id);
    }
}
