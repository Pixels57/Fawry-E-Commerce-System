package com.task.eCommerce.model;

import java.time.LocalDate;

public class ExpirableProduct extends Product implements Shippable {
    protected LocalDate expirationDate;

    public ExpirableProduct(int id, String name, int quantity, double price, LocalDate expirationDate, boolean shippable, double weight) {
        super(id, name, quantity, price, shippable, weight);
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(this.expirationDate);
    }

    @Override
    public String getProductType() {
        return "Expirable Product";
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public boolean isShippable() {
        return shippable;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}