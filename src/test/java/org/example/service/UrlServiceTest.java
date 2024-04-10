package org.example.service;

import org.example.exception.EntityNotFoundException;

import org.example.repository.UrlRepositoryImpl;
import org.example.service.model.Url;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlServiceTest {

    //ТЕСТ №1
    //добавить longUrl и сохранить, вернув значение
    @Test
    void testAddUrl1() throws EntityNotFoundException {
        UrlService urlService = new UrlServiceImpl(new UrlRepositoryImpl());
        //given:
        String longUrl = "https://www.example.ru/abc123";
        //when:
        Url addedUrl = urlService.addUrl(new Url(longUrl));
        String expectedShortUrl = "https://MyServer.com/1";
        String expectedId = "1";
        //then:
        assertEquals(longUrl, addedUrl.longURL());
        assertEquals(expectedId, addedUrl.id());
        assertEquals(expectedShortUrl, addedUrl.shortURL());
    }

    //ТЕСТ №2
    //попробовать добавить longUrl, когда он уже есть в базе и отдать значение из неё
    @Test
    void testAddUrl2() throws EntityNotFoundException {
        UrlService urlService = new UrlServiceImpl(new UrlRepositoryImpl());
        //given:
        String longUrl = "https://www.example.ru/abc123";
        urlService.addUrl(new Url(longUrl));
        //when:
        Url addedUrl = urlService.addUrl(new Url(longUrl));
        String expectedShortUrl = "https://MyServer.com/1";
        String expectedId = "1";
        //then:
        assertEquals(longUrl, addedUrl.longURL());
        assertEquals(expectedId, addedUrl.id());
        assertEquals(expectedShortUrl, addedUrl.shortURL());
    }

    //ТЕСТ №3
    //найти по shortUrl исходный longUrl
    @Test
    void testAddUrl3() throws EntityNotFoundException {
        UrlService urlService = new UrlServiceImpl(new UrlRepositoryImpl());
        //given:
        String longUrl = "https://www.example.ru/abc123";
        Url addedUrl = urlService.addUrl(new Url(longUrl));
        //when:
        Url foundedUrl = urlService.findUrl(addedUrl);
        String expectedShortUrl = "https://MyServer.com/1";
        String expectedId = "1";
        String expectedFoundedLongUrl = "https://www.example.ru/abc123";
        //then:
        assertEquals(expectedFoundedLongUrl, foundedUrl.longURL());
        assertEquals(expectedId, foundedUrl.id());
        assertEquals(expectedShortUrl, foundedUrl.shortURL());
    }
}
