package com.codecool.web.dao.database;

import com.codecool.web.dao.ProductsDao;
import com.codecool.web.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseProductsDao extends AbstractDao implements ProductsDao {

    public DatabaseProductsDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Product> getProductsByTypeId(int typeId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE type = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, typeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(fetchProduct(resultSet));

                }
            }
        }
        return products;
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        List<Product> allProducts = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTS";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                allProducts.add(fetchProduct(resultSet));
            }
        }
        return allProducts;
    }

    private Product fetchProduct(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String manufacturer = resultSet.getString("manufacturer");
        int price = resultSet.getInt("price");
        int stock = resultSet.getInt("stock");
        int typeId = resultSet.getInt("type");
        String imgLink = resultSet.getString("picture");
        return new Product(id, name, manufacturer, price, typeId, imgLink, stock);
    }
}
