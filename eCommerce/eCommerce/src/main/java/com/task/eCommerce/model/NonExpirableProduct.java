package com.task.eCommerce.model;

public class NonExpirableProduct extends Product  implements Shippable{
    public NonExpirableProduct(int id, String name, int quantity, double price, boolean shippable, double weight) {
        super(id, name, quantity, price, shippable, weight);
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public String getProductType() {
        return "Non-Expirable Product";
    }

    public boolean isShippable() {
        return shippable;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}