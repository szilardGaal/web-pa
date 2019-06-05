package com.codecool.web.model;

import java.util.List;
import java.util.ArrayList;

public final class User extends AbstractModel {

    private final String userName;
    private final String email;
    private List<Order> orders = new ArrayList<>();

    public User(int id, String userName, String email) {
        super(id);
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public List<Order> getOrders() {
        List<Order> copy = new ArrayList<>();
        copy.addAll(orders);
        return copy;
    }

    public void addToOrders(Order newOrder) {
        orders.add(newOrder);
    }

    public void removeFromOrders(Order orderToRemove) {
        orders.remove(orderToRemove);
    }

}
