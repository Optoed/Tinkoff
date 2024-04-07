package org.example.service;

import org.example.controller.dto.UrlDto;
import org.example.exception.EntityNotFoundException;
import org.example.service.model.Url;

public interface UrlService {

    /**
     * save new url or update existing url
     *
     * @param url - model with url parameters
     * @return - id of saved url
     */
    Url addUrl(Url url);

    /**
     * find exist url by provided id
     *
     * @param id - of url to look for
     * @return {@link Url} by id
     * @throws EntityNotFoundException - if url with provided id not found
     */
    Url findUrl(String id) throws EntityNotFoundException;

    // 1) Возможно стоит выбрать Optional? 2) Возможно throws EntityNotFoundException; может не подойти
    Url findUrl(Url url) throws EntityNotFoundException;
}
