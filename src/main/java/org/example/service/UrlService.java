package org.example.service;

import org.example.controller.dto.UrlDto;
import org.example.exception.EntityNotFoundException;
import org.example.service.model.Url;

import java.sql.SQLException;

public interface UrlService {

    /**
     * save new url or update existing url
     *
     * @param url - model with url parameters
     * @return - url of saved url
     */
    Url addUrl(Url url) throws SQLException;

    /**
     * find exist url by provided id
     *
     * @param url - of url to look for
     * @return {@link Url} by url
     * @throws EntityNotFoundException - if url with provided url not found
     */
    Url findUrl(Url url) throws EntityNotFoundException, SQLException;
    Url findUrl(String shortURL) throws EntityNotFoundException, SQLException;
}
