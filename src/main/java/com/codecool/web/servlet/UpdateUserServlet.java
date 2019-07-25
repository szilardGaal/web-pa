package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.PasswordHashService;
import com.codecool.web.service.simple.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/update")
public class UpdateUserServlet extends AbstractServlet {

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new UserService(userDao);
            PasswordHashService pwh = new PasswordHashService();

            int userId = Integer.parseInt(req.getParameter("userId"));
            String newUserName = req.getParameter("name");
            String newPassword = req.getParameter("password");

            if (!newPassword.equals("")) {
                newPassword = pwh.getHashedPassword(newPassword);
            }

            int sessionUserId = ((User) req.getSession().getAttribute("user")).getId();

            if (userId == sessionUserId) {
                User user = userService.updateUser(userId, newUserName, newPassword);
                sendMessage(resp, HttpServletResponse.SC_OK, user);
            } else {
                throw new ServiceException("UserId miss-match");
            }

        } catch (SQLException sqlEx) {
            handleSqlError(resp, sqlEx);
        } catch (ServiceException servEx) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, servEx.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            ex.getMessage();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new UserService(userDao);

            int userId = Integer.parseInt(req.getParameter("user"));
            int sessionUserId = ((User) req.getSession().getAttribute("user")).getId();

            if (userId == sessionUserId) {
                User user = userService.refreshUser(userId);
                sendMessage(resp, HttpServletResponse.SC_OK, user);
            } else {
                throw new ServiceException("UserId miss-match");
            }

        } catch (SQLException sqlEx) {
            handleSqlError(resp, sqlEx);
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }
    }
}
