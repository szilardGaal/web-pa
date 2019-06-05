package com.codecool.web.servlet;

import com.codecool.web.dao.CategoriesDao;
import com.codecool.web.dao.database.DatabaseCategoriesDao;
import com.codecool.web.dto.CategoriesDto;
import com.codecool.web.service.simple.SimpleCategoriesService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/categories")
public class CategoriesServlet extends AbstractServlet {

    @Override
    public void doGet (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            CategoriesDao categoriesDao = new DatabaseCategoriesDao(connection);
            SimpleCategoriesService categoriesService = new SimpleCategoriesService(categoriesDao);

            CategoriesDto dto = categoriesService.getAllCategories();

            sendMessage(resp, HttpServletResponse.SC_OK, dto);

        } catch (SQLException sqlEx) {
            handleSqlError(resp, sqlEx);
        }
    }
}
