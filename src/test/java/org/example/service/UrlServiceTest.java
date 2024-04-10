package org.example.service;

import org.example.exception.EntityNotFoundException;

import org.example.repository.UrlRepositoryImpl;
import org.example.service.model.Url;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlServiceTest {

    // ���������� ��������

    //���� �1
    //�������� longUrl � ���������, ������ ��������
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

    //���� �2
    //����������� �������� longUrl, ����� �� ��� ���� � ���� � ������ �������� �� ��
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

    //���� �3
    //����� �� shortUrl �������� longUrl
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


    // ���������� ��������

    // ���� �4
    // ����������� ����� �� shortURL �������������� � �� longURL
    @Test
    void testAddUrl4() throws EntityNotFoundException {
        UrlService urlService = new UrlServiceImpl(new UrlRepositoryImpl());
        //given:
        String ShortUrlTryToFind = "https://MyServer.com/1";
        //when:
        try {
            Url foundedUrl = urlService.findUrl(ShortUrlTryToFind);
        } catch (Exception ex) {
            System.out.printf("URL-����� � ����� ����������� (short) URl %s �� ������%n", ShortUrlTryToFind);
        }


        //then:
    }
}
