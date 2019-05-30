package com.codecool.web.dao.database;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.Order;
import com.codecool.web.model.User;

import java.sql.*;

public final class DatabaseUserDao extends AbstractDao implements UserDao {

    public DatabaseUserDao(Connection connection) {
        super(connection);
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        if (email == null || "".equals(email)) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User newUser = fetchUser(resultSet);
                    getUserOrders(newUser);
                    return newUser;
                }
            }
        }
        return null;
    }

    @Override
    public void addUser(String userName, String email, boolean isAdmin) throws SQLException {
        String sql = "INSERT INTO users(userName, password, isAdmin) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            statement.setString(2, email);
            statement.setBoolean(3, isAdmin);
            executeInsert(statement);
        }
    }

    private User fetchUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("userName");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        boolean isAdmin = resultSet.getBoolean("isAdmin");
        return new User(id, name, email, password, isAdmin);
    }

    private void getUserOrders(User user) throws SQLException {
        String sql = "SELECT * FROM orders WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    Date date = resultSet.getDate("dateOfCreation");
                    int total = resultSet.getInt("total");

                    user.addToOrders(new Order(id, date, total));
                }
            }
        }
    }
}
