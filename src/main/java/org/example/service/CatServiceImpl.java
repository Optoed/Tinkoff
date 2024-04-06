package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.repository.CatRepository;
import org.example.repository.dao.CatDao;
import org.example.service.model.Cat;

import java.util.Optional;

public class CatServiceImpl implements CatService {

    private final CatRepository catRepository;

    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    public String addCat(Cat cat) {
        CatDao catDao = new CatDao(cat.longURL(), cat.shortURL());
        return catRepository.save(catDao);
    }

    @Override
    public Cat findCat(String id) throws EntityNotFoundException {
        Optional<CatDao> catDao = catRepository.findCatById(id);
        return catDao.map(cat -> new Cat(cat.id(), cat.longURL(), cat.shortURL())).orElseThrow(() -> new EntityNotFoundException("котик не найден"));
    }
}
