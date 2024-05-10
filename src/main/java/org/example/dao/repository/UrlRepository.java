package org.example.dao.repository;

import org.example.dao.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

//Давай все id переделаем из String в Long, и в бд, и в коде
//1) перенеси нужные данные из org.example.repository в org.example.dao.repository - готово
//2) Убери из проекта org.example.repository - готово
//3) переведи все UrlDao в UrlEntity
@Repository
//@Transactional(readOnly = true)
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    Optional<UrlEntity> findUrlById(Long id) throws SQLException;

    Optional<UrlEntity> findUrlByLongURL(String longUrl) throws SQLException;

    Optional<UrlEntity> findUrlByShortURL(String shortUrl) throws SQLException;

    //UrlEntity save(UrlEntity urlEntity);
    @Transactional
    <S extends UrlEntity> S save(S urlEntity);

    @Query(nativeQuery = true, value = "SELECT nextval('urls_id_seq')")
    void getNextId() throws SQLException;

    @Query(nativeQuery = true, value = "SELECT currval('urls_id_seq')")
    Long getCurrentId() throws SQLException;
}
