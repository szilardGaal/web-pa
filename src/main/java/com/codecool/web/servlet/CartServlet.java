package com.codecool.web.servlet;

import com.codecool.web.dao.ProductsDao;
import com.codecool.web.dao.database.DatabaseProductsDao;
import com.codecool.web.model.Product;
import com.codecool.web.service.simple.SimpleProductsService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/cart")
public class CartServlet extends AbstractServlet {

    @Override
    public void doGet (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ProductsDao productsDao = new DatabaseProductsDao(connection);
            SimpleProductsService productsService = new SimpleProductsService(productsDao);

            int productId = Integer.parseInt(req.getParameter("id"));

            Product product = productsService.getProductById(productId);

            sendMessage(resp, HttpServletResponse.SC_OK, product);

        } catch (SQLException sqlEx) {
            handleSqlError(resp, sqlEx);
        }
    }
}
