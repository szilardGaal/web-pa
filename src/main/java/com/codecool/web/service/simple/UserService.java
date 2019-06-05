package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;

import java.sql.SQLException;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(String userName, String email, String password) throws SQLException {
        userDao.addUser(userName, email, password);
    }
}
