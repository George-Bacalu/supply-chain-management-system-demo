package com.project.utils;

import com.project.entity.Product;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class PriceUtils {

    public static Double getTotalPrice(List<Product> products) {
        return products.stream().map(product -> product.getPrice() * product.getQuantity()).reduce(0.0, Double::sum);
    }
}
