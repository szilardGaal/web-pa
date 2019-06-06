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
import java.util.List;

@WebServlet("/products")
public class ProductsServlet extends AbstractServlet {

    @Override
    public void doPost (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ProductsDao productsDao = new DatabaseProductsDao(connection);
            SimpleProductsService productsService = new SimpleProductsService(productsDao);

            try {
                int id = Integer.parseInt(req.getParameter("id"));
                List<Product> productList = productsService.getProductsByTypeId(id);
                sendMessage(resp, HttpServletResponse.SC_OK, productList);

            } catch (NumberFormatException ex) {
                List<Product> allProductsList = productsService.getAllProducts();
                sendMessage(resp, HttpServletResponse.SC_OK, allProductsList);
            }

        } catch (SQLException sqlEx) {
            handleSqlError(resp, sqlEx);
        }
    }

}
