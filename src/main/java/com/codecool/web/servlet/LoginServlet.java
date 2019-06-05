package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.PasswordHashService;
import com.codecool.web.service.simple.SimpleLoginService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/login")
public final class LoginServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            LoginService loginService = new SimpleLoginService(userDao);
            PasswordHashService pwh = new PasswordHashService();

            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String hashPassword = userDao.findPasswordByEmail(email);
            try {
                if (pwh.validatePassword(password, hashPassword)) {
                    User user = loginService.loginUser(email);
                    req.getSession().setAttribute("user", user);

                    sendMessage(resp, HttpServletResponse.SC_OK, user);

                } else {
                    throw new ServiceException("Bad login");
                }
            } catch (NoSuchAlgorithmException ex) {
                ex.getMessage();
            } catch (InvalidKeySpecException ex) {
                ex.getMessage();
            }

        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
