package com.codecool.web.dao.database;

import com.codecool.web.dao.CategoriesDao;
import com.codecool.web.dto.CategoriesDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseCategoriesDao extends AbstractDao implements CategoriesDao {

    public DatabaseCategoriesDao(Connection connection) {
        super(connection);
    }

    @Override
    public CategoriesDto getAllCategories() throws SQLException {
        List<Integer> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        String sql = "SELECT * FROM types";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ids.add(resultSet.getInt("id"));
                names.add(resultSet.getString("name"));
            }
        }
        return new CategoriesDto(ids, names);
    }
}
