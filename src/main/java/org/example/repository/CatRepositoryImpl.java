package org.example.repository;

import org.example.database.Database;
import org.example.repository.dao.CatDao;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;

public class CatRepositoryImpl implements CatRepository {

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

    @Override
    public Optional<CatDao> findCatById(String id) {
        return Optional.ofNullable(dataBase.getCat(id));
    }

    @Override
    public String save(CatDao catDao) {
        String catDaoId = catDao.id();
        if (catDaoId != null) {
            dataBase.saveCat(catDao);
            return catDaoId;
        }

        idCount = idCount + 1L;

        String id = String.valueOf(idCount);

        String base62HashString = base62HashCode(id);

        dataBase.saveCat(new CatDao(id, catDao.longURL(), base62HashString));
        return id;
    }
}
