package org.example.service;

import org.example.exception.EntityNotFoundException;

import org.example.dao.entity.UrlEntity;
import org.example.dao.repository.UrlRepository;


import org.example.service.model.Url;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    //Сервер для хранения URL-ссылок
    //public static final String MyServer = "https://MyServer.com/";

    private static final String allBase62Characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    //Получение хэш-строки из id (переводим в десятичное число и потом в 62-ричное)
    private String getBase62HashCode(Long id) {
        long base10HashCode = id.longValue();
        StringBuilder base62HashCodeString = new StringBuilder();
        while (base10HashCode > 0) {
            base62HashCodeString.append(allBase62Characters.charAt((int)(base10HashCode % 62)));
            base10HashCode = base10HashCode / 62;
        }
        return base62HashCodeString.toString();
    }

    private void makeNextId() throws SQLException {
        //Вызываем один раз
        urlRepository.getNextId();
    }

    private Long getCurrentId() throws SQLException {
        //можем вызывать сколько угодно раз
        return urlRepository.getCurrentId();
    }

    private String getNewShortURl() throws SQLException {
        //но на момент вызова мы предварительно вызовем makeNextId(), тем самым сделав Id актуальным
        Long id = getCurrentId();
        //String newShortURl = MyServer + base62HashString;
        return getBase62HashCode(id);
    }


    public Url addUrl(Url url) throws IllegalStateException, SQLException {

        Optional<UrlEntity> urlDao = urlRepository.findUrlByLongURL(url.longURL());

        // Проверяем, есть ли значение в Optional urlDao
//        urlDao.ifPresent(entity -> {
//            System.out.println("Содержимое объекта urlDao:");
//            System.out.println("ID: " + entity.getId());
//            System.out.println("Long URL: " + entity.getLongURL());
//            System.out.println("Short URL: " + entity.getShortURL());
//        });

        //то есть не нашли такой в базе данных, поэтому добавляем
        if (urlDao.isEmpty()) {
            //увеличиваем счётчик в DataBase на 1 (для нового Id)
            makeNextId();
            UrlEntity savedUrlDao = urlRepository.save(new UrlEntity(getCurrentId(), url.longURL(), getNewShortURl()));
            return new Url(savedUrlDao.id(), savedUrlDao.longURL(), savedUrlDao.shortURL());
        }
        else {
            return new Url(urlDao.get().id(), urlDao.get().longURL(), urlDao.get().shortURL());
        }
    }

    //Помни, что возможно тут стоит использовать Optional и подумай над throws EntityNotFoundException;
    @Override
    public Url findUrl(Url url) throws EntityNotFoundException, SQLException {
        if (url.id() != null) {
            Optional<UrlEntity> urlDao = urlRepository.findUrlById(url.id());
            return urlDao.map(newUrl -> new Url(newUrl.id(), newUrl.longURL(), newUrl.shortURL())).orElseThrow(
                    () -> new EntityNotFoundException("A URL with such a ID: " + url.id() + " was not found")
            );

        } else if (!Objects.equals(url.longURL(), "")) {
            Optional<UrlEntity> urlDao = urlRepository.findUrlByLongURL(url.longURL());
            return urlDao.map(newUrl -> new Url(newUrl.id(), newUrl.longURL(), newUrl.shortURL())).orElseThrow(
                    () -> new EntityNotFoundException("A URL with such a long Url: " + url.longURL() + " was not found")
            );

        } else if (!Objects.equals(url.shortURL(), "")) {
            Optional<UrlEntity> urlDao = urlRepository.findUrlByShortURL(url.shortURL());
            return urlDao.map(newUrl -> new Url(newUrl.id(), newUrl.longURL(), newUrl.shortURL())).orElseThrow(
                    () -> new EntityNotFoundException("A URL with such a short Url: " + url.shortURL() + " was not found")
            );
        }

        throw new EntityNotFoundException("There is not enough (no) data to look up the URL");
    }

    @Override
    public Url findUrl(String shortURL) throws EntityNotFoundException, SQLException {
        if (shortURL != null) {
            try {
                Optional<UrlEntity> urlDao = urlRepository.findUrlByShortURL(shortURL);
                return urlDao.map(newUrl -> new Url(newUrl.id(), newUrl.longURL(), newUrl.shortURL())).orElseThrow(
                        () -> new EntityNotFoundException("A URL with such a short Url: " + shortURL + " was not found")
                );
            } catch (SQLException e) {
                // Handle SQLException or rethrow as RuntimeException
                throw new RuntimeException("Error occurred while searching URL by short URL: " + shortURL, e);
            }
        }
        throw new EntityNotFoundException("There is not enough (no) data to look up the URL");
    }
}
