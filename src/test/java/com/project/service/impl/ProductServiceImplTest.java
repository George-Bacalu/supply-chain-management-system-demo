package com.project.service.impl;

import com.project.entity.Product;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.ProductRepository;
import com.project.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.project.constant.ClientConstants.PRODUCT_WITH_ID_NOT_FOUND;
import static com.project.mocks.ProductMock.getMockedProduct;
import static com.project.mocks.ProductMock.getMockedProductList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

   Product product = getMockedProduct();
   List<Product> products = getMockedProductList();

   @Mock
   private ProductRepository productRepositoryTest;

   @InjectMocks
   private ProductService productServiceTest;

   @BeforeEach
   void setUp() {
      productRepositoryTest.save(product);
   }

   @Test
   @DisplayName("getAllProductsService")
   public void getAllProducts_service_shouldReturnAllProducts() {
      productServiceTest.getAllProducts();

      given(productRepositoryTest.findAll()).willReturn(List.of(product));

      assertThat(productRepositoryTest.findAll().toString()).isEqualTo(products.toString());
      verify(productRepositoryTest).findAll();
   }

   @Test
   @DisplayName("getProductByIdValid")
   public void getProductByIdValid_shouldReturnOrder_whenGivenIdToGetProductIsValid() {
      Long orderId = 1L;
      given(productRepositoryTest.existsById(orderId)).willReturn(true);
      productServiceTest.getProductById(orderId);

      assertThat(productRepositoryTest.getById(orderId).toString()).isEqualTo(List.of(product).toString());
      verify(productRepositoryTest).findById(orderId);
   }

   @Test
   @DisplayName("getProductByIdInvalid")
   public void getProductByIdValid_shouldReturnOrder_whenGivenIdToGetProductIsInvalid() {
      Long orderId = 1L;
      given(productRepositoryTest.existsById(orderId)).willReturn(false);

      assertThatThrownBy(() -> productServiceTest.getProductById(orderId))
              .isInstanceOf(ResourceNotFoundException.class)
              .hasMessageContaining(PRODUCT_WITH_ID_NOT_FOUND);

      verify(productRepositoryTest, never()).findById(orderId);
   }

   @Test
   @DisplayName("saveProduct")
   public void saveProduct_shouldReturnProduct() {
      ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
      verify(productRepositoryTest).save(productArgumentCaptor.capture());

      Product capturedProduct = productArgumentCaptor.getValue();
      assertThat(capturedProduct.toString()).isEqualTo(product.toString());
   }
}