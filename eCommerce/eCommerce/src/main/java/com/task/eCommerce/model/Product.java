package com.task.eCommerce.model;

public abstract class Product {
    protected int id;
    protected String name;
    protected int quantity;
    protected double price;
    protected boolean shippable;
    protected double weight;

    public Product(int id, String name, int quantity, double price, boolean shippable, double weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.shippable = shippable;
        this.weight = weight;
    }

    public abstract boolean isExpired();
    public abstract String getProductType();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}