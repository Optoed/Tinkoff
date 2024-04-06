package org.example.database;

import org.example.repository.dao.CatDao;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private static Database instance;
    private final Map<String, CatDao> catsMap;

    //Сервер для хранения URL-ссылок
    public static final String MyServer = "https://MyServer.com/";

    private Database() {
        catsMap = new HashMap<>();
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

    public String saveCat(CatDao catDao) {
        String id = catDao.id();
        getInstance().catsMap.put(id, catDao);
        return id;
    }

    public CatDao getCat(String id) {
        return getInstance().catsMap.get(id);
    }
}
