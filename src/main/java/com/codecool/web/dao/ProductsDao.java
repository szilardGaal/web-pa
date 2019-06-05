package com.codecool.web.dao;

import com.codecool.web.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductsDao {

    List<Product> getProductsByTypeId(int id) throws SQLException;

    List<Product> getAllProducts() throws SQLException;
}
