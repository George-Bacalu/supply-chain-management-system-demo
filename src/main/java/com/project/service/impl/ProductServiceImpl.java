package com.project.service.impl;

import com.project.entity.Product;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.ProductRepository;
import com.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.constant.ClientConstants.PRODUCT_WITH_ID_NOT_FOUND;

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
      return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PRODUCT_WITH_ID_NOT_FOUND, id));
   }

   @Override
   public Product saveProduct(Product product) {
      return productRepository.save(product);
   }
}
