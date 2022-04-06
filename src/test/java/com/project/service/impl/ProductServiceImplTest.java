package com.project.service.impl;

import com.project.repository.ProductRepository;
import com.project.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

   @Mock
   private ProductRepository productRepositoryTest;

   @InjectMocks
   private ProductService productServiceTest;

   @Test
   void getAllProducts() {
   }

   @Test
   void getProductById() {
   }

   @Test
   void saveProduct() {
   }
}