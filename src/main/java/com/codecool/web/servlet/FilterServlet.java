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
import java.util.Arrays;
import java.util.List;

@WebServlet("/filter")
public class FilterServlet extends AbstractServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ProductsDao productsDao = new DatabaseProductsDao(connection);
            SimpleProductsService productsService = new SimpleProductsService(productsDao);

            String name = req.getParameter("name");
            String regex = "%" + name + "%";
            List<Product> productList = productsService.searchProducts(regex);

            sendMessage(resp, HttpServletResponse.SC_OK, productList);

        } catch (SQLException sqlEx) {
            handleSqlError(resp, sqlEx);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            ProductsDao productsDao = new DatabaseProductsDao(connection);
            SimpleProductsService productsService = new SimpleProductsService(productsDao);

            String includeOrExclude = req.getParameter("inOrEx");
            String idsString = req.getParameter("ids");
            int[] ids = Arrays.stream(idsString.split(",")).mapToInt(Integer::parseInt).toArray();

            List<Product> productList;
            if (includeOrExclude.equalsIgnoreCase("include")) {
                productList = productsService.getProductsWithId(ids);
            } else {
                productList = productsService.getProductsWithoutId(ids);
            }

            sendMessage(resp, HttpServletResponse.SC_OK, productList);
        } catch (SQLException sqlEx) {
            handleSqlError(resp, sqlEx);
        }
    }
}
