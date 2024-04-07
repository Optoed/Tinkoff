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

    @Override
    public Url addUrl(Url url) {
        UrlDao savedUrlDao = urlRepository.save(new UrlDao(url.longURL(), url.shortURL()));
        return new Url(savedUrlDao.id(), savedUrlDao.longURL(), savedUrlDao.shortURL());
    }

    @Override
    public Url findUrl(String id) throws EntityNotFoundException {
        Optional<UrlDao> urlDao = urlRepository.findUrlById(id);
        return urlDao.map(url -> new Url(url.id(), url.longURL(), url.shortURL())).orElseThrow(
                () -> new EntityNotFoundException("URL-адрес с таким идентификатором id не найден")
            );
    }

    //Помни, что возможно тут стоит использовать Optional и подумай над throws EntityNotFoundException;
    @Override
    public Url findUrl(Url url) throws EntityNotFoundException {
        if (url.id() != null) {
            Optional<UrlDao> urlDao = urlRepository.findUrlById(url.id());
            return urlDao.map(newUrl -> new Url(newUrl.id(), newUrl.longURL(), newUrl.shortURL())).orElseThrow(
                    () -> new EntityNotFoundException("URL-адрес с таким идентификатором id не найден")
            );

        } else if (!Objects.equals(url.longURL(), "")) {
            Optional<UrlDao> urlDao = urlRepository.findUrlByLongUrl(url.longURL());
            return urlDao.map(newUrl -> new Url(newUrl.id(), newUrl.longURL(), newUrl.shortURL())).orElseThrow(
                    () -> new EntityNotFoundException("URL-адрес с таким идентификатором id не найден")
            );

        } else if (!Objects.equals(url.shortURL(), "")) {
            Optional<UrlDao> urlDao = urlRepository.findUrlByShortUrl(url.shortURL());
            return urlDao.map(newUrl -> new Url(newUrl.id(), newUrl.longURL(), newUrl.shortURL())).orElseThrow(
                    () -> new EntityNotFoundException("URL-адрес с таким идентификатором id не найден")
            );
        }

        throw new EntityNotFoundException("Недостаточно (отсутствие) данных для поиска URL-адреса");
    }
}
