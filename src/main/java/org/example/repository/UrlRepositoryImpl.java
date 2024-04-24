package org.example.repository;

import org.example.jdbc.JdbcUtils;
import org.example.repository.dao.UrlDao;

import java.sql.*;
import java.util.Optional;

public class UrlRepositoryImpl implements UrlRepository {

    @Override
    public Optional<UrlDao> findUrlById(String id) throws SQLException {

        String sql = "SELECT id,longURL,shortURL FROM urls WHERE id =?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            UrlDao urlDao = new UrlDao(
                    resultSet.getString("id"),
                    resultSet.getString("longURL"),
                    resultSet.getString("shortURL")
            );
            return Optional.of(urlDao);
        }
        return Optional.empty();
    }

    //поиск по longURL
    @Override
    public Optional<UrlDao> findUrlByLongUrl(String longURL) throws SQLException {

        String sql = "SELECT id,longURL,shortURL FROM urls WHERE longURL =?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, longURL);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            UrlDao urlDao = new UrlDao(
                    resultSet.getString("id"),
                    resultSet.getString("longURL"),
                    resultSet.getString("shortURL")
            );
            return Optional.of(urlDao);
        }
        return Optional.empty();
    }

    //поиск по shortURL
    @Override
    public Optional<UrlDao> findUrlByShortUrl(String shortURL) throws SQLException {
        String sql = "SELECT id,longURL,shortURL FROM urls WHERE shortURL =?";
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, shortURL);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            UrlDao urlDao = new UrlDao(
                    resultSet.getString("id"),
                    resultSet.getString("longURL"),
                    resultSet.getString("shortURL")
            );
            return Optional.of(urlDao);
        }
        return Optional.empty();
    }

    @Override
    public UrlDao save(UrlDao urlDao) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "INSERT INTO urls (longURL, shortURL) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, urlDao.longURL());
        preparedStatement.setString(2, urlDao.shortURL());
        preparedStatement.execute();
        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
             preparedStatement) {
            if (generatedKeys.next()) {
                return new UrlDao(generatedKeys.getString(1), urlDao.longURL(), urlDao.shortURL());
            }
        }
        preparedStatement.close();
        return null;
    }

    // через sequence
    @Override
    public String getNextId() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        try (Statement statement = connection.createStatement()) {
            // Создание последовательности, если ее еще нет
            statement.execute("CREATE SEQUENCE IF NOT EXISTS url_sequence START 1");

            // Получение следующего значения из последовательности
            ResultSet resultSet = statement.executeQuery("SELECT NEXTVAL('url_sequence')");
            if (resultSet.next()) {
                Long nextID = resultSet.getLong(1);
                return String.valueOf(nextID);
            }
        }
        return null; // если не удалось получить LASTVAL()
    }

    @Override
    public String getCurrentId() throws SQLException {
        Connection connection = JdbcUtils.getConnection();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT LASTVAL()");
            if (resultSet.next()) {
                return String.valueOf(resultSet.getString(1));
            }
        }
        return null; // если не удалось получить LASTVAL()
    }
}
