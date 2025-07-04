package com.task.eCommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.task.eCommerce.model.*;
import com.task.eCommerce.service.CheckoutService;
import com.task.eCommerce.exceptions.*;
import java.time.LocalDate;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);

		runECommerceDemo();
	}

	public static void runECommerceDemo() {
        System.out.println("=".repeat(50));
        System.out.println("🛒 FAWRY E-COMMERCE SYSTEM DEMO");
        System.out.println("=".repeat(50));
        
        // Test Case 1: Successful checkout
        testSuccessfulCheckout();
	}

	private static void testSuccessfulCheckout() {
        System.out.println("\n📋 TEST CASE 1: Successful Checkout");
        System.out.println("-".repeat(40));
        
        try {
            // Create products
            ExpirableProduct cheese = new ExpirableProduct(1, "Cheese", 10, 100.0, 
                LocalDate.now().plusDays(7), true, 0.2);
            
            ExpirableProduct biscuits = new ExpirableProduct(2, "Biscuits", 5, 75.0, 
                LocalDate.now().plusDays(5), true, 0.35);
            
            NonExpirableProduct tv = new NonExpirableProduct(3, "Smart TV", 3, 15000.0, true, 25.0);
            
            NonExpirableProduct scratchCard = new NonExpirableProduct(4, "Mobile Card", 50, 100.0, 
                false, 0.0);
            
            // Create customer with sufficient balance
            Customer customer = new Customer(1, "John Doe", "john@email.com", 50000.0);
            
            // Create checkout service
            CheckoutService checkoutService = new CheckoutService();
            
            // Add items to cart
            System.out.println("Adding products to cart...");
            customer.getCart().addProduct(cheese, 2);
            customer.getCart().addProduct(biscuits, 1);
            customer.getCart().addProduct(tv, 1);
            customer.getCart().addProduct(scratchCard, 1);
            
            // Display cart
            customer.getCart().displayCart();
            System.out.println("Customer balance before checkout: $" + customer.getBalance());
            
            // Process checkout
            System.out.println("\nProcessing checkout...");
            checkoutService.checkout(customer);
            
            System.out.println("✅ Test Case 1: PASSED");
            
        } catch (Exception e) {
            System.err.println("❌ Test Case 1: FAILED - " + e.getMessage());
        }
    }
}
