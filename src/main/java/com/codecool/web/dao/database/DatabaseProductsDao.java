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

    @Override
    public List<Product> getProductsWithId(int[] ids) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE";
        for (int id : ids) {
            sql += " type = ? OR";
            if (id == (ids[ids.length-1])) {
                sql = sql.substring(0, sql.length() - 3);
            }
        }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < ids.length; i ++) {
                statement.setInt(i + 1, ids[i]);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(fetchProduct(resultSet));
                }
            }
        }
        return products;
    }

    @Override
    public List<Product> getProductsWithoutId(int[] ids) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE";
        for (int id : ids) {
            sql += " type != ? AND";
            if (id == (ids[ids.length-1])) {
                sql = sql.substring(0, sql.length() - 3);
            }
        }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < ids.length; i ++) {
                statement.setInt(i + 1, ids[i]);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(fetchProduct(resultSet));
                }
            }
        }
        return products;
    }

    public List<Product> searchProducts(String regex) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ? OR manufacturer LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, regex);
            statement.setString(2, regex);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    products.add(fetchProduct(resultSet));
                }
            }
        }
        return products;
    }

    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchProduct(resultSet);
                }
            }
        }
        return null;
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
