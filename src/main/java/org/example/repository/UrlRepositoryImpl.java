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
        String urlDaoId = urlDao.id();
        if (urlDaoId != null) {
            return dataBase.saveUrl(urlDao);
        }

//      Можешь менять прибавляемое значение для теста например на + 10000000000000L
        idCount = idCount + 100000000000000L;

        String id = String.valueOf(idCount);

        String base62HashString = base62HashCode(id);

        String newShortURl = getMyServer() + base62HashString;

        return dataBase.saveUrl(new UrlDao(id, urlDao.longURL(), newShortURl));
    }
}
