package com.task.eCommerce.model;

import com.task.eCommerce.exceptions.InsufficientQuantityException;
import com.task.eCommerce.exceptions.OutOfStockException;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addProduct(Product product, int requestedQuantity) 
    throws OutOfStockException, InsufficientQuantityException {

        if (product.getQuantity() == 0) {
            throw new OutOfStockException("Product '" + product.getName() + "' is out of stock!");
        }

        if (requestedQuantity > product.getQuantity()) {
            throw new InsufficientQuantityException(
                "Insufficient quantity for '" + product.getName() + "'. " +
                "Requested: " + requestedQuantity + ", Available: " + product.getQuantity()
            );
        }

        if (requestedQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive!");
        }

        CartItem existingItem = findCartItem(product);
        if (existingItem != null) {
            int newTotalQuantity = existingItem.getQuantity() + requestedQuantity;
            if (newTotalQuantity > product.getQuantity()) {
                throw new InsufficientQuantityException(
                    "Cannot add " + requestedQuantity + " more of '" + product.getName() + "'. " +
                    "Already in cart: " + existingItem.getQuantity() + ", Available: " + product.getQuantity()
                );
            }
            existingItem.setQuantity(newTotalQuantity);
        } else {
            items.add(new CartItem(product, requestedQuantity));
        }

        System.out.println("Added " + requestedQuantity + "x " + product.getName() + " to cart");
    }

    public boolean removeProduct(Product product) {
        CartItem item = findCartItem(product);
        if (item != null) {
            items.remove(item);
            System.out.println("🗑️ Removed " + product.getName() + " from cart");
            return true;
        }
        return false;
    }

    public void updateQuantity(Product product, int newQuantity) 
            throws InsufficientQuantityException {
        
        if (newQuantity > product.getQuantity()) {
            throw new InsufficientQuantityException(
                "Cannot update quantity. Requested: " + newQuantity + 
                ", Available: " + product.getQuantity()
            );
        }

        CartItem item = findCartItem(product);
        if (item != null) {
            if (newQuantity <= 0) {
                removeProduct(product);
            } else {
                item.setQuantity(newQuantity);
            }
        }
    }

    public double getTotal() {
        return items.stream()
                   .mapToDouble(CartItem::getTotalPrice)
                   .sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items); // Return copy to prevent external modification
    }

    public void clear() {
        items.clear();
        System.out.println("🗑️ Cart cleared");
    }

    private CartItem findCartItem(Product product) {
        return items.stream()
                   .filter(item -> item.getProduct().getId() == product.getId())
                   .findFirst()
                   .orElse(null);
    }

    public void displayCart() {
        if (isEmpty()) {
            System.out.println("🛒 Cart is empty");
            return;
        }

        System.out.println("🛒 Cart Contents:");
        items.forEach(item -> System.out.println("  " + item));
        System.out.println("💰 Total: $" + getTotal());
    }
}