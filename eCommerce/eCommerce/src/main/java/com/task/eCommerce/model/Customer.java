package com.task.eCommerce.model;

public class Customer {
    private int id;
    private String name;
    private String email;
    private double balance;
    private Cart cart;

    public Customer(int id, String name, String email, double balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.cart = new Cart();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    public Cart getCart() {
        return cart;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean deductBalance(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}