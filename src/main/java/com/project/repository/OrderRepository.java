package com.project.repository;

import com.project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

   List<Order> findOrderByCustomer_Name(String name);

   List<Order> findOrderByAddress_Country(String country);

   List<Order> findOrderByAddress_City(String city);

   List<Order> findOrderByAddress_AddressId(Long addressId);

   List<Order> findOrderByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
