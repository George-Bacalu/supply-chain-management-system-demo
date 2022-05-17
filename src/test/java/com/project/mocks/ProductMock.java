package com.project.mocks;

import com.project.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMock {

    public static Product getMockedProduct1() {
        return Product.builder()
                .productId(1L)
                .name("Shoes order")
                .price(80.0)
                .quantity(100)
                .build();
    }

    public static Product getMockedProduct2() {
        return Product.builder()
                .productId(2L)
                .name("T-shirts order")
                .price(50.0)
                .quantity(200)
                .build();
    }

    public static List<Product> getMockedProductList() {
        return List.of(getMockedProduct1(), getMockedProduct2());
    }
}
