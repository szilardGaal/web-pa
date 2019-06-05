package com.codecool.web.dao;

import com.codecool.web.dto.CategoriesDto;

import java.sql.SQLException;

public interface CategoriesDao {

    CategoriesDto getAllCategories() throws SQLException;
}
