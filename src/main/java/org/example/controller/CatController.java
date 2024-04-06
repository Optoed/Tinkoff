package org.example.controller;

import org.example.controller.dto.CatDto;
import org.example.exception.EntityNotFoundException;
import org.example.service.CatService;
import org.example.service.model.Cat;

public class CatController {

    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    public String addCat(CatDto catDto) {
        return catService.addCat(new Cat(catDto.longURL()));
    }

    public CatDto getCat(String id) throws EntityNotFoundException {
        Cat cat = catService.findCat(id);
        return new CatDto(cat.id(), cat.longURL(), cat.shortURL());
    }
}
