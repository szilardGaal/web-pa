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
    public String findPasswordByEmail(String email) throws SQLException {
        String sql = "SELECT password FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("password");
                }
            }
        }
        return null;
    }

    @Override
    public void addUser(String userName, String email, String password) throws SQLException {
        String sql = "INSERT INTO users(userName, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            statement.setString(2, email);
            statement.setString(3, password);
            executeInsert(statement);
        }
    }

    @Override
    public void createOrder(int userId, int[] productIds, int[] pcs) throws SQLException {
        int orderId = createBlankOrder(userId);

        String sql = "INSERT INTO order_rows (orderId, productId, quantity) VALUES ";
        for (int i = 0; i < productIds.length; i ++) {
            if (i < productIds.length-1) {
                sql += "(?, ?, ?), ";
            } else {
                sql += "(?, ?, ?);";
            }
        }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int indexCounter = 1;
            for (int i = 0; i < pcs.length; i ++) {
                statement.setInt(indexCounter, orderId);
                indexCounter += 1;
                statement.setInt(indexCounter, productIds[i]);
                indexCounter += 1;
                statement.setInt(indexCounter, pcs[i]);
                indexCounter += 1;
            }
            statement.executeUpdate();
        }
    }

    @Override
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
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
    public void updateUserName(int userId, String newName) throws SQLException {
        String sql = "UPDATE users SET username = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newName);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }

    @Override
    public void updateUserPassword(int userId, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newPassword);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }

    private User fetchUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("userName");
        String email = resultSet.getString("email");
        return new User(id, name, email);
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

    private int createBlankOrder(int userId) throws SQLException {
        String sql = "INSERT INTO orders (userId) VALUES (?) RETURNING id;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }
        return 0;
    }
}
