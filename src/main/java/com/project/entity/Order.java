package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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

   private Double totalPrice;

   @Column(name = "date_created", updatable = false)
   @DateTimeFormat(pattern = "dd.mm.yyyy hh:mm:ss")
   @CreationTimestamp
   private LocalDateTime createdAt;

   @Column(name = "date_updated")
   @DateTimeFormat(pattern = "dd.mm.yyyy hh:mm:ss")
   @UpdateTimestamp
   private LocalDateTime updatedAt;

   @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
   private Customer customer;

   @OneToMany(cascade = CascadeType.ALL)
   @JoinTable(
           name = "order_products",
           joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "orderId"),
           inverseJoinColumns = @JoinColumn( name = "product_id", referencedColumnName = "productId")
   )
   @Size(max = 30)
   private List<Product> products;

   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinColumn(name = "address_id", referencedColumnName = "addressId")
   private Address address;

   @NotNull
   private OrderStatus orderStatus;
}
