package com.project.service.impl;

import com.project.entity.Product;
import com.project.exception.InvalidProductException;
import com.project.exception.ProductNotFoundException;
import com.project.repository.ProductRepository;
import com.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.constant.ClientConstants.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

   private final ProductRepository productRepository;

   @Override
   public List<Product> getAllProducts() {
      return productRepository.findAll();
   }

   @Override
   public Product getProductById(Long id) {
      if (id < 0) {
         throw new InvalidProductException(PRODUCT_WITH_INVALID_ID, id);
      }
      return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(PRODUCT_WITH_ID_NOT_FOUND, id));
   }

   @Override
   public Product saveProduct(Product product) {
      if (product == null) {
         throw new ProductNotFoundException(NO_PRODUCT_FOUND);
      }
      return productRepository.save(product);
   }
}
