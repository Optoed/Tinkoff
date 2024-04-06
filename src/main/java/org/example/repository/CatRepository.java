package org.example.repository;

import org.example.repository.dao.CatDao;

import java.util.Optional;

public interface CatRepository {

    Optional<CatDao> findCatById(String id);

    String save(CatDao catDao);
}
