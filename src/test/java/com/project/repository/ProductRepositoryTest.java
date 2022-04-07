package com.project.repository;

import com.project.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.project.mocks.ProductMock.getMockedProduct;
import static com.project.mocks.ProductMock.getMockedProductList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductRepositoryTest {

   Product product = getMockedProduct();
   List<Product> products = getMockedProductList();

   @Autowired
   private ProductRepository productRepositoryTest;

   @BeforeEach
   void setUp() {
      productRepositoryTest.save(product);
   }

   @Test
   @DisplayName("getAllProductsRepository")
   public void getAllProducts_repository_shouldReturnAllProducts() {
      assertThat(productRepositoryTest.findAll().toString()).isEqualTo(products.toString());
   }

   @Test
   @DisplayName("findProductByName")
   void findProductByName_shouldReturnProduct_whenGivenName() {
      assertThat(productRepositoryTest.findProductByName("Shoes order").toString()).isEqualTo(product.toString());
   }

   @Test
   @DisplayName("findProductByPriceBetween")
   void findProductByPriceBetween_shouldReturnProduct_whenGivenPrices() {
      assertThat(productRepositoryTest.findProductByPriceBetween(70.0, 90.0).toString()).isEqualTo(product.toString());
   }
}