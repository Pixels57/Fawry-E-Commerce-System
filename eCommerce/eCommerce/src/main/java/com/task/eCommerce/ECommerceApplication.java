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
        
        testSuccessfulCheckout();
        testInsufficientBalance();
        testOutOfStock();
        testEmptyCart();
        testExpiredProduct();
	}

	private static void testSuccessfulCheckout() {
        System.out.println("\n📋 TEST CASE 1: Successful Checkout");
        System.out.println("-".repeat(40));
        
        try {
            ExpirableProduct cheese = new ExpirableProduct(1, "Cheese", 10, 100.0, 
                LocalDate.now().plusDays(7), true, 0.2);
            
            ExpirableProduct biscuits = new ExpirableProduct(2, "Biscuits", 5, 75.0, 
                LocalDate.now().plusDays(5), true, 0.35);
            
            NonExpirableProduct tv = new NonExpirableProduct(3, "Smart TV", 3, 15000.0, true, 25.0);
            
            NonExpirableProduct scratchCard = new NonExpirableProduct(4, "Mobile Card", 50, 100.0, 
                false, 0.0);
            
            Customer customer = new Customer(1, "John Doe", "john@email.com", 50000.0);
            
            CheckoutService checkoutService = new CheckoutService();
            
            System.out.println("Adding products to cart...");
            customer.getCart().addProduct(cheese, 2);
            customer.getCart().addProduct(biscuits, 1);
            customer.getCart().addProduct(tv, 1);
            customer.getCart().addProduct(scratchCard, 1);
            
            customer.getCart().displayCart();
            System.out.println("Customer balance before checkout: $" + customer.getBalance());
            
            System.out.println("\nProcessing checkout...");
            checkoutService.checkout(customer);
            
            System.out.println("✅ Test Case 1: PASSED");
            
        } catch (Exception e) {
            System.err.println("❌ Test Case 1: FAILED - " + e.getMessage());
        }
    }

        private static void testInsufficientBalance() {
        System.out.println("\n📋 TEST CASE 2: Insufficient Balance");
        System.out.println("-".repeat(40));
        
        try {
            NonExpirableProduct expensiveTV = new NonExpirableProduct(5, "Premium TV", 2, 25000.0, 
                true, 30.0);
            
            Customer poorCustomer = new Customer(2, "Jane Smith", "jane@email.com", 100.0);
            
            CheckoutService checkoutService = new CheckoutService();
            
            poorCustomer.getCart().addProduct(expensiveTV, 1);
            poorCustomer.getCart().displayCart();
            
            checkoutService.checkout(poorCustomer);
            
            System.err.println("❌ Test Case 2: FAILED - Should have thrown InsufficientBalanceException");
            
        } catch (InsufficientBalanceException e) {
            System.out.println("✅ Test Case 2: PASSED - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Test Case 2: FAILED - Wrong exception: " + e.getMessage());
        }
    }

        private static void testOutOfStock() {
        System.out.println("\n📋 TEST CASE 3: Out of Stock");
        System.out.println("-".repeat(40));
        
        try {
            ExpirableProduct limitedCheese = new ExpirableProduct(6, "Limited Cheese", 2, 50.0, 
                LocalDate.now().plusDays(3), true, 0.3);
            
            Customer customer = new Customer(3, "Bob Wilson", "bob@email.com", 1000.0);
            
            customer.getCart().addProduct(limitedCheese, 5); // Only 2 available
            
            System.err.println("❌ Test Case 3: FAILED - Should have thrown InsufficientQuantityException");
            
        } catch (InsufficientQuantityException e) {
            System.out.println("✅ Test Case 3: PASSED - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Test Case 3: FAILED - Wrong exception: " + e.getMessage());
        }
    }

        private static void testEmptyCart() {
        System.out.println("\n📋 TEST CASE 4: Empty Cart");
        System.out.println("-".repeat(40));
        
        try {
            Customer customer = new Customer(4, "Alice Brown", "alice@email.com", 1000.0);
            CheckoutService checkoutService = new CheckoutService();
            
            checkoutService.checkout(customer);
            
            System.err.println("❌ Test Case 4: FAILED - Should have thrown EmptyCartException");
            
        } catch (EmptyCartException e) {
            System.out.println("✅ Test Case 4: PASSED - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Test Case 4: FAILED - Wrong exception: " + e.getMessage());
        }
    }

       private static void testExpiredProduct() {
        System.out.println("\n📋 TEST CASE 5: Expired Product");
        System.out.println("-".repeat(40));
        
        try {
            ExpirableProduct expiredMilk = new ExpirableProduct(7, "Expired Milk", 5, 30.0, 
                LocalDate.now().minusDays(2), true, 0.5); // Expired 2 days ago
            
            Customer customer = new Customer(5, "Charlie Davis", "charlie@email.com", 1000.0);
            CheckoutService checkoutService = new CheckoutService();
            
            customer.getCart().addProduct(expiredMilk, 1);
            
            checkoutService.checkout(customer);
            
            System.err.println("❌ Test Case 5: FAILED - Should have thrown ProductExpiredException");
            
        } catch (ProductExpiredException e) {
            System.out.println("✅ Test Case 5: PASSED - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Test Case 5: FAILED - Wrong exception: " + e.getMessage());
        }
    }
}
