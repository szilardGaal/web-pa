package com.codecool.web.service.simple;

import com.codecool.web.dao.CategoriesDao;
import com.codecool.web.dto.CategoriesDto;

import java.sql.SQLException;

public class SimpleCategoriesService {

    private final CategoriesDao categoriesDao;

    public SimpleCategoriesService(CategoriesDao categoriesDao) {
        this.categoriesDao = categoriesDao;
    }

    public CategoriesDto getAllCategories() throws SQLException {
        return categoriesDao.getAllCategories();
    }
}
