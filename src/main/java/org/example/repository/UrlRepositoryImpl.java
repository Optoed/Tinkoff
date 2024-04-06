package org.example.repository;

import org.example.database.Database;
import org.example.repository.dao.UrlDao;

import java.util.Optional;

public class UrlRepositoryImpl implements UrlRepository {

    private static final Database dataBase = Database.getInstance();

    private static long idCount = 0L;

    private static final String allBase62Characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    //Получение хэш-строки из id (переводим в десятичное число и потом в 62-ричное)
    private String base62HashCode(String id) {
        long base10HashCode = Long.parseLong(id);
        StringBuilder base62HashCodeString = new StringBuilder();
        while (base10HashCode > 0) {
            base62HashCodeString.append(allBase62Characters.charAt((int)(base10HashCode % 62)));
            base10HashCode = base10HashCode / 62;
        }
        return base62HashCodeString.toString();
    }

    private static String getMyServer() {
        return dataBase.getMyServer();
    }

    @Override
    public Optional<UrlDao> findUrlById(String id) {
        return Optional.ofNullable(dataBase.getUrl(id));
    }

    @Override
    public String save(UrlDao urlDao) {
        String urlDaoId = urlDao.id();
        if (urlDaoId != null) {
            dataBase.saveUrl(urlDao);
            return urlDaoId;
        }

        idCount = idCount + 1L;

        String id = String.valueOf(idCount);

        String base62HashString = base62HashCode(id);

        String newShortURl = getMyServer() + base62HashString;

        dataBase.saveUrl(new UrlDao(id, urlDao.longURL(), newShortURl));
        return id;
    }
}
