package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.repository.UrlRepository;
import org.example.repository.dao.UrlDao;
import org.example.service.model.Url;

import java.util.Objects;
import java.util.Optional;

public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    //Сервер для хранения URL-ссылок
    public static final String MyServer = "https://MyServer.com/";

    private static final String allBase62Characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    //Получение хэш-строки из id (переводим в десятичное число и потом в 62-ричное)
    private String getBase62HashCode(String id) {
        long base10HashCode = Long.parseLong(id);
        StringBuilder base62HashCodeString = new StringBuilder();
        while (base10HashCode > 0) {
            base62HashCodeString.append(allBase62Characters.charAt((int)(base10HashCode % 62)));
            base10HashCode = base10HashCode / 62;
        }
        return base62HashCodeString.toString();
    }

    private void makeNextId() {
        //Вызываем один раз
        urlRepository.getNextId();
    }

    private String getCurrentId() {
        //можем вызывать сколько угодно раз
        return urlRepository.getCurrentId();
    }

    private String getNewShortURl() {
        //но на момент вызова мы предварительно вызовем makeNextId(), тем самым сделав Id актуальным
        String id = getCurrentId();
        String base62HashString = getBase62HashCode(id);
        String newShortURl = MyServer + base62HashString;
        return newShortURl;
    }


    public Url addUrl(Url url) throws IllegalStateException {

        Optional<UrlDao> urlDao = urlRepository.findUrlByLongUrl(url.longURL());

        //то есть не нашли такой в базе данных, поэтому добавляем
        if (urlDao.isEmpty()) {
            //увеличиваем счётчик в DataBase на 1 (для нового Id)
            makeNextId();
            UrlDao savedUrlDao = urlRepository.save(new UrlDao(getCurrentId(), url.longURL(), getNewShortURl()));
            return new Url(savedUrlDao.id(), savedUrlDao.longURL(), savedUrlDao.shortURL());
        }
        else {
            return new Url(urlDao.get().id(), urlDao.get().longURL(), urlDao.get().shortURL());
        }

    }

    //Помни, что возможно тут стоит использовать Optional и подумай над throws EntityNotFoundException;
    @Override
    public Url findUrl(Url url) throws EntityNotFoundException {
        if (url.id() != null) {
            Optional<UrlDao> urlDao = urlRepository.findUrlById(url.id());
            return urlDao.map(newUrl -> new Url(newUrl.id(), newUrl.longURL(), newUrl.shortURL())).orElseThrow(
                    () -> new EntityNotFoundException("A URL with such a ID: " + url.id() + " was not found")
            );

        } else if (!Objects.equals(url.longURL(), "")) {
            Optional<UrlDao> urlDao = urlRepository.findUrlByLongUrl(url.longURL());
            return urlDao.map(newUrl -> new Url(newUrl.id(), newUrl.longURL(), newUrl.shortURL())).orElseThrow(
                    () -> new EntityNotFoundException("A URL with such a long Url: " + url.longURL() + " was not found")
            );

        } else if (!Objects.equals(url.shortURL(), "")) {
            Optional<UrlDao> urlDao = urlRepository.findUrlByShortUrl(url.shortURL());
            return urlDao.map(newUrl -> new Url(newUrl.id(), newUrl.longURL(), newUrl.shortURL())).orElseThrow(
                    () -> new EntityNotFoundException("A URL with such a short Url: " + url.shortURL() + " was not found")
            );
        }

        throw new EntityNotFoundException("Недостаточно (отсутствие) данных для поиска URL-адреса");
    }
}
