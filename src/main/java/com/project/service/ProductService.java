package com.project.service;

import com.project.entity.Product;

import java.util.List;

public interface ProductService {

   List<Product> getAllProducts();

   Product getProductById(Long id);

   Product saveProduct(Product product);
}
