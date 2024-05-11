package org.example.service;

import org.example.dao.entity.UrlEntity;
import org.example.dao.repository.UrlRepository;
import org.example.exception.EntityNotFoundException;
import org.example.service.model.Url;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CatServiceTest {

    UrlRepository urlRepository = Mockito.mock(UrlRepository.class);
    UrlService urlService = new UrlServiceImpl(urlRepository);

    @Test
    void testAddUrl() throws EntityNotFoundException, SQLException {
        //given:
        UrlEntity newUrl = new UrlEntity(1L, "https://www.youtube.com/", "1");
        Url expected = new Url(1L, "https://www.youtube.com/", "1");

        //when:
        when(urlRepository.findUrlByShortURL("1")).thenReturn(Optional.of(newUrl));
        Url actual = urlService.findUrl("1");

        //then:
        assertEquals(expected, actual);
    }
}
