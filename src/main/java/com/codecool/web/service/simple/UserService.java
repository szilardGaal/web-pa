package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;

import java.sql.SQLException;


public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(String userName, String email, String password) throws SQLException {
        userDao.addUser(userName, email, password);
    }

    public void createOrder(int userId, int[] productIds, int[] pcs) throws SQLException {
        userDao.createOrder(userId, productIds, pcs);
    }

    public User refreshUser(int userId) throws SQLException {
        return userDao.getUserById(userId);
    }

    public User updateUser(int userId, String newName, String newPassword) throws SQLException {
        if (!newName.equals("")) userDao.updateUserName(userId, newName);
        if (!newPassword.equals("")) userDao.updateUserPassword(userId, newPassword);

        return userDao.getUserById(userId);
    }
}
