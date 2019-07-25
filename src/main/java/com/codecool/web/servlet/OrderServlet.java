package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.simple.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

@WebServlet("/orders")
public class OrderServlet extends AbstractServlet {

    @Override
    public void doPost (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new UserService(userDao);

            String idsString = req.getParameter("ids");
            String pcsString = req.getParameter("pcs");

            int[] productIds = Arrays.stream(idsString.split(",")).mapToInt(Integer::parseInt).toArray();
            int[] pcs = Arrays.stream(pcsString.split(",")).mapToInt(Integer::parseInt).toArray();

            int userId = ((User) req.getSession().getAttribute("user")).getId();

            userService.createOrder(userId, productIds, pcs);

        } catch (SQLException sqlEx) {
            handleSqlError(resp, sqlEx);
        }
    }
}
