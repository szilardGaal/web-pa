package com.codecool.web.model;

import java.util.Date;

public class Order extends AbstractModel {

    private Date date;
    private int total;

    public Order(int id, Date date, int total) {
        super(id);
        this.date = date;
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public int getTotal() {
        return total;
    }
}
