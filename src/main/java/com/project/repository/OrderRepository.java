package com.project.repository;

import com.project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

   List<Order> findByCustomer(String customer);
   List<Order> findByCustomerContaining(String substr);

   @Query("select o.customerId from Order o where o.customerId = ?1")
   Long getOrderCustomerByCustomerId(Long customerId);

}
