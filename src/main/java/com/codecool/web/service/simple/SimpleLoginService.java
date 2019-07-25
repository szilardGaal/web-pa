package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.ServiceException;

import javax.sql.rowset.serial.SerialException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SimpleLoginService implements LoginService {

    private final UserDao userDao;

    public SimpleLoginService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User loginUser(String email) throws SQLException, ServiceException {
        try {
            User user = userDao.findByEmail(email);
            return user;
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public String findPasswordByEmail(String email) throws SQLException {
        return userDao.findPasswordByEmail(email);
    }
}
