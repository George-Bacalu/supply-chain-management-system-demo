package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long orderId;

   @Column(nullable = false)
   private Double totalPrice;

   @Column(name = "date_created", columnDefinition = "timestamp default now()")
   //@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
   private LocalDateTime createdAt;

   @Column(name = "date_updated", columnDefinition = "timestamp default now()")
   //@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
   private LocalDateTime updatedAt;

   @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
   private Customer customer;

   @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinTable(
           name = "order_products",
           joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "orderId"),
           inverseJoinColumns = @JoinColumn( name = "product_id", referencedColumnName = "productId")
   )
   @Size(max = 50, message = "{order.products.invalid}")
   private List<Product> products;

   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinColumn(name = "address_id", referencedColumnName = "addressId")
   private Address address;
}
