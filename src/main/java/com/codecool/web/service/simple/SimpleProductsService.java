package com.codecool.web.service.simple;

import com.codecool.web.dao.ProductsDao;
import com.codecool.web.model.Product;

import java.sql.SQLException;
import java.util.List;

public final class SimpleProductsService {

    private final ProductsDao productsDao;

    public SimpleProductsService(ProductsDao productsDao) {
        this.productsDao = productsDao;
    }

    public List<Product> getAllProducts() throws SQLException {
        return productsDao.getAllProducts();
    }

    public List<Product> getProductsByTypeId(int id) throws SQLException {
        return productsDao.getProductsByTypeId(id);
    }

    public List<Product> getProductsWithId(int[] ids) throws SQLException {
        return productsDao.getProductsWithId(ids);
    }

    public List<Product> getProductsWithoutId(int[] ids) throws SQLException {
        return productsDao.getProductsWithoutId(ids);
    }

    public List<Product> searchProducts(String regex) throws SQLException {
        return productsDao.searchProducts(regex);
    }

    public Product getProductById(int id) throws SQLException {
        return productsDao.getProductById(id);
    }
}
