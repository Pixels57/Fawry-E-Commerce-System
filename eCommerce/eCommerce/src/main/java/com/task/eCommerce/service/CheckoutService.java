package com.task.eCommerce.service;

import com.task.eCommerce.model.*;
import com.task.eCommerce.exceptions.*;
import java.util.ArrayList;
import java.util.List;

public class CheckoutService {
    private ShippingService shippingService;

    public CheckoutService() {
        this.shippingService = new ShippingService();
    }

    public void checkout(Customer customer) throws EmptyCartException, 
            InsufficientBalanceException, ProductExpiredException, OutOfStockException {
        
        Cart cart = customer.getCart();
        
        if (cart.isEmpty()) {
            throw new EmptyCartException("Cart is empty. Cannot proceed with checkout.");
        }

        validateCartItems(cart);

        double subtotal = calculateSubtotal(cart);

        List<Shippable> shippableItems = getShippableItems(cart);
        double shippingFees = shippingService.calculateShippingCost(shippableItems);

        double totalAmount = subtotal + shippingFees;

        if (customer.getBalance() < totalAmount) {
            throw new InsufficientBalanceException(
                "Insufficient balance. Required: $" + totalAmount + 
                ", Available: $" + customer.getBalance()
            );
        }

        customer.deductBalance(totalAmount);

        updateProductQuantities(cart);

        shippingService.processShipment(shippableItems);

        printCheckoutReceipt(cart, subtotal, shippingFees, totalAmount, customer.getBalance());

        cart.clear();
    }

    /**
     * Validate all cart items for expiration and stock
     */
    private void validateCartItems(Cart cart) throws ProductExpiredException, OutOfStockException {
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            
            if (product.isExpired()) {
                throw new ProductExpiredException(
                    "Product '" + product.getName() + "' is expired and cannot be purchased."
                );
            }
            
            if (product.getQuantity() == 0) {
                throw new OutOfStockException(
                    "Product '" + product.getName() + "' is out of stock."
                );
            }
            
            // Check if requested quantity is still available
            if (item.getQuantity() > product.getQuantity()) {
                throw new OutOfStockException(
                    "Insufficient stock for '" + product.getName() + "'. " +
                    "Requested: " + item.getQuantity() + ", Available: " + product.getQuantity()
                );
            }
        }
    }

    /**
     * Calculate subtotal (sum of all items' prices)
     */
    private double calculateSubtotal(Cart cart) {
        return cart.getItems().stream()
                  .mapToDouble(CartItem::getTotalPrice)
                  .sum();
    }

    /**
     * Get shippable items from cart
     */
    private List<Shippable> getShippableItems(Cart cart) {
        List<Shippable> shippableItems = new ArrayList<>();
        
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            
            // Check if product implements Shippable interface
            if (product instanceof Shippable) {
                // Add multiple instances based on quantity
                for (int i = 0; i < item.getQuantity(); i++) {
                    shippableItems.add((Shippable) product);
                }
            }
        }
        
        return shippableItems;
    }

    /**
     * Update product quantities after successful checkout
     */
    private void updateProductQuantities(Cart cart) {
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            int newQuantity = product.getQuantity() - item.getQuantity();
            product.setQuantity(newQuantity);
        }
    }

    /**
     * Print detailed checkout receipt
     */
    private void printCheckoutReceipt(Cart cart, double subtotal, double shippingFees, 
                                    double totalAmount, double remainingBalance) {
        System.out.println("** Checkout receipt **");
        
        // Print individual items
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + 
                             " " + (int)item.getTotalPrice());
        }
        
        System.out.println("----------------------");
        System.out.println("Subtotal " + (int)subtotal);
        System.out.println("Shipping " + (int)shippingFees);
        System.out.println("Amount " + (int)totalAmount);
        System.out.println();
        System.out.println("Customer remaining balance: $" + remainingBalance);
        System.out.println("Thank you for your purchase!");
    }
}