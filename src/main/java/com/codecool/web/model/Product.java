package com.codecool.web.model;

public final class Product extends AbstractModel {

    private final String name;
    private final String manufacturer;
    private final int price;
    private final int typeId;
    private final String imgLink;
    private boolean isInStock;

    public Product(int id, String name, String manufacturer, int price, int typeId, String imgLink, int inStock) {
        super(id);
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.typeId = typeId;
        this.imgLink = imgLink;
        this.isInStock = inStock > 0;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getPrice() {
        return price;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getImgLink() {
        return imgLink;
    }

    public boolean isInStock() {
        return isInStock;
    }
}
