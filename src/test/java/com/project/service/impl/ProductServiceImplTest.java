package com.project.service.impl;

import com.project.entity.Product;
import com.project.exception.ProductNotFoundException;
import com.project.repository.ProductRepository;
import com.project.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.project.constant.ClientConstants.PRODUCT_WITH_ID_NOT_FOUND;
import static com.project.mocks.ProductMock.getMockedProduct1;
import static com.project.mocks.ProductMock.getMockedProductList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

   private static final Long productId = 1L;

   @Mock
   private ProductRepository productRepository;
   @InjectMocks
   private ProductService productService;

   @Captor
   ArgumentCaptor<List<Product>> productCaptor;

   @Test
   @DisplayName("saveProduct")
   public void saveProduct_shouldReturnUser() {
      /*
      ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
      verify(productRepository).save(productArgumentCaptor.capture());

      Product capturedProduct = productArgumentCaptor.getValue();
      assertThat(capturedProduct.toString()).isEqualTo(getMockedProduct1().toString());
       */

      given(productRepository.saveAll(getMockedProductList())).willReturn(getMockedProductList());

      verify(productRepository).saveAll(productCaptor.capture());

      assertThat(productCaptor.getValue()).isEqualTo(getMockedProductList());
   }

   @Test
   @DisplayName("getAllProducts")
   public void getAllProducts_shouldReturnAllProducts() {
      given(productRepository.findAll()).willReturn(getMockedProductList());
      productService.getAllProducts();

      assertThat(productRepository.findAll().toString()).isEqualTo(getMockedProductList().toString());
      verify(productRepository).findAll();
   }

   @Test
   @DisplayName("whenProductWithIdExists_getProductById")
   public void getProductById_shouldReturnProduct_whenProductWithIdExists() {
      given(productRepository.existsById(productId)).willReturn(true);
      productService.getProductById(productId);

      assertThat(productRepository.getById(productId).toString()).isEqualTo(List.of(getMockedProduct1()).toString());
      verify(productRepository).findById(productId);
   }

   @Test
   @DisplayName("whenProductWithIdDoesNotExist_getProductById_throwsException")
   public void getProductById_shouldThrowException_whenProductWithIdDoesNotExist() {
      given(productRepository.existsById(productId)).willReturn(false);

      /*
      assertThatThrownBy(() -> productService.getProductById(productId))
              .isInstanceOf(ProductNotFoundException.class)
              .hasMessageContaining(PRODUCT_WITH_ID_NOT_FOUND);

      verify(productRepository, never()).findById(productId);
       */

      ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));

      verify(productRepository, never()).findById(productId);

      assertThat(exception.getMessage()).isEqualTo(PRODUCT_WITH_ID_NOT_FOUND, productId);
   }
}