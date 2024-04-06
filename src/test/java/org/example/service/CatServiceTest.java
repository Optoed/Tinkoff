package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.repository.CatRepositoryImpl;
import org.example.service.model.Cat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatServiceTest {

    private final CatService catService = new CatServiceImpl(new CatRepositoryImpl());

    @Test
    void testAddCat() throws EntityNotFoundException {
        //given:
        Cat cat = new Cat(12, "синий");
        //when:
        String id = catService.addCat(cat);
        Cat savedCat = catService.findCat(id);
        //then:
        assertEquals(cat.age(), savedCat.age());
        assertEquals(cat.color(), savedCat.color());
    }
}
