package com.project.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.project.mocks.ProductMock.getMockedProduct1;
import static com.project.mocks.ProductMock.getMockedProduct2;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductRepositoryTest {

   @Autowired
   private ProductRepository productRepository;

   @Test
   @DisplayName("saveProduct")
   public void saveProduct_shouldReturnSavedProduct() {
      productRepository.saveAll(List.of(getMockedProduct1(), getMockedProduct2()));
   }

   @Test
   @DisplayName("getAllProducts")
   public void getAllProducts_shouldReturnAllProducts() {
      assertThat(productRepository.findAll().toString()).isEqualTo(List.of(getMockedProduct1(), getMockedProduct2()).toString());
   }

   @Test
   @DisplayName("findProductByName")
   void findProductByName_shouldReturnProduct_whenGivenName() {
      assertThat(productRepository.findProductByName("Shoes order").toString()).isEqualTo(List.of(getMockedProduct1()).toString());
   }

   @Test
   @DisplayName("findProductByPriceBetween")
   void findProductByPriceBetween_shouldReturnProduct_whenGivenPrices() {
      assertThat(productRepository.findProductByPriceBetween(40.0, 60.0).toString()).isEqualTo(getMockedProduct2().toString());
   }
}