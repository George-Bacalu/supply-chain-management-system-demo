package com.project.repository;

import com.project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

   Product findProductByName(String name);
   Product findProductByPriceBetween(Double lowerBound, Double upperBound);


}
