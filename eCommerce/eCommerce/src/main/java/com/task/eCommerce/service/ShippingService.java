package com.task.eCommerce.service;

import com.task.eCommerce.model.Shippable;
import java.util.List;

public class ShippingService {
    private static final double SHIPPING_RATE_PER_KG = 10.0;
    private static final double BASE_SHIPPING_FEE = 5.0;


    public double calculateShippingCost(List<Shippable> shippableItems) {
        if (shippableItems.isEmpty()) {
            return 0.0;
        }

        double totalWeight = shippableItems.stream()
                                         .mapToDouble(Shippable::getWeight)
                                         .sum();
        
        return BASE_SHIPPING_FEE + (totalWeight * SHIPPING_RATE_PER_KG);
    }

    public void processShipment(List<Shippable> shippableItems) {
        if (shippableItems.isEmpty()) {
            return;
        }

        System.out.println("** Shipment notice **");
        
        double totalWeight = 0.0;
        for (Shippable item : shippableItems) {
            double itemWeight = item.getWeight();
            totalWeight += itemWeight;
            System.out.println("1x " + item.getName() + " " + (itemWeight * 1000) + "g");
        }
        
        System.out.println("Total package weight " + totalWeight + "kg");
        System.out.println();
    }
}