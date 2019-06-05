package com.codecool.web.dao;

import com.codecool.web.model.User;

import java.sql.SQLException;

public interface UserDao {

    User findByEmail(String email) throws SQLException;

    String findPasswordByEmail(String email) throws SQLException;

    void addUser(String userName, String email, String password) throws SQLException;
}
