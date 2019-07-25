package com.codecool.web.dao;

import com.codecool.web.model.User;

import java.sql.SQLException;

public interface UserDao {

    User findByEmail(String email) throws SQLException;

    String findPasswordByEmail(String email) throws SQLException;

    void addUser(String userName, String email, String password) throws SQLException;

    void createOrder(int userId, int[] productIds, int[] pcs) throws SQLException;

    User getUserById(int userId) throws SQLException;

    void updateUserName(int userId, String newName) throws SQLException;

    void updateUserPassword(int userId, String newPassword) throws SQLException;
}
