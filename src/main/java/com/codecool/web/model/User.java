package com.codecool.web.model;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public final class User extends AbstractModel {

    private final String userName;
    private final String email;
    private final String password;
    private final boolean isAdmin;
    private List<Order> orders = new ArrayList<>();

    public User(int id, String userName, String email, String password, boolean isAdmin) {
        super(id);
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) &&
            Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userName, password);
    }
}
