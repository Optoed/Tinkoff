package org.example.service;

import org.example.database.Database;
import org.example.exception.EntityNotFoundException;

import org.example.repository.UrlRepositoryImpl;
import org.example.service.model.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class UrlServiceTest {

    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.urlsMap.clear();
        database.longUrlsIndexMap.clear();
        database.shortUrlsIndexMap.clear();
        Database.resetIdCounter(); // Добавляем метод сброса счетчика
    }

    // Позитивные сценарии

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


    // Негативные сценарии

    // ТЕСТ №4
    // Попробовать найти по shortURL несуществующий в бд longURL
    @Test
    void testAddUrl4() throws EntityNotFoundException {
        UrlService urlService = new UrlServiceImpl(new UrlRepositoryImpl());
        //given:
        urlService.addUrl(new Url("1","https://google.com" ,"https://MyServer.com/1"));
        String ShortUrlTryToFind = "https://MyServer.com/2";
        //Если сделать String ShortUrlTryToFind = "https://MyServer.com/2";
        // то есть не существующий в бд, то тест будет пройден
        //when:
        Throwable exception = assertThrows(EntityNotFoundException.class, () -> {
            Url foundedUrl = urlService.findUrl(new Url(null, "", ShortUrlTryToFind));
        });

        //then:
        assertNotNull(exception);
        assertEquals("A URL with such a short Url: " + ShortUrlTryToFind + " was not found",
                new String(exception.getMessage()));
    }

    // ТЕСТ №5
    // Попробовать найти по shortURL несуществующий в бд longURL
    @Test
    void testAddUrl5() throws EntityNotFoundException {
        UrlService urlService = new UrlServiceImpl(new UrlRepositoryImpl());
        //given:
        urlService.addUrl(new Url("1","https://google.com" ,"https://MyServer.com/1"));
        String ShortUrlTryToFind = "https://MyServer.com/1";
        //Если сделать String ShortUrlTryToFind = "https://MyServer.com/1";
        // то есть существующий в бд, то тест не будет пройден
        //when:
        Throwable exception = assertThrows(EntityNotFoundException.class, () -> {
            Url foundedUrl = urlService.findUrl(new Url(null, "", ShortUrlTryToFind));
        });

        //then:
        assertNotNull(exception);
        assertEquals("A URL with such a short Url: " + ShortUrlTryToFind + " was not found",
                new String(exception.getMessage()));
    }
}
