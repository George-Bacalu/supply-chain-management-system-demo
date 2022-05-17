package com.project.repository;

import com.project.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

   Customer findCustomerByName(String name);

   Customer findCustomerByPhoneNumber(String phoneNumber);
}
