package com.task.eCommerce.service;

import com.task.eCommerce.model.Shippable;
import java.util.List;
import java.util.Map;

public class ShippingService {
    private static final double SHIPPING_RATE_PER_KG = 10.0;
    private static final double BASE_SHIPPING_FEE = 5.0;

    public double calculateShippingCost(Map<Shippable, Integer> shippableItems) {
        if (shippableItems.isEmpty()) {
            return 0.0;
        }

        double totalWeight = shippableItems.entrySet().stream()
                                         .mapToDouble(entry -> entry.getKey().getWeight() * entry.getValue())
                                         .sum();
        
        return BASE_SHIPPING_FEE + (totalWeight * SHIPPING_RATE_PER_KG);
    }

    public void processShipment(Map<Shippable, Integer> shippableItems) {
        if (shippableItems.isEmpty()) {
            return;
        }

        System.out.println("** Shipment notice **");
        
        double totalWeight = 0.0;
        for (Map.Entry<Shippable, Integer> entry : shippableItems.entrySet()) {
            Shippable item = entry.getKey();
            int quantity = entry.getValue();
            double itemWeight = item.getWeight();
            double totalItemWeight = itemWeight * quantity;
            totalWeight += totalItemWeight;
            System.out.println(quantity + "x " + item.getName() + " " + (totalItemWeight * 1000) + "g");
        }
        
        System.out.println("Total package weight " + totalWeight + "kg");
        System.out.println();
    }
}