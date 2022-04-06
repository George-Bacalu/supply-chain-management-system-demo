package com.project.mocks;

import com.project.entity.Product;

import java.util.List;

public class ProductMock {

    public static Product getMockedProduct() {
        return Product.builder().productId(1L).name("Shirts").price(50.0).quantity(100).build();
    }

    public static List<Product> getProducts() {
        return List.of(
                Product.builder().productId(1L).name("Shirts").price(50.0).quantity(100).build(),
                Product.builder().productId(1L).name("Shoes").price(150.0).quantity(50).build()
        );
    }
}
