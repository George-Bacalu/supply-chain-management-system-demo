package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

   @NotNull(message = "* Please enter the customer name")
   @Size(max = 30, message = "{order.customer.invalid}")
   @NotBlank
   private String customer;

   @NotNull(message = "* Please enter the customer id")
   @Range(min = 1, max = 10, message = "{order.customerId.invalid}")
   private Long customerId;

   @Column(name = "date_created", columnDefinition = "timestamp default now()")
   @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
   private LocalDateTime createdAt;

   @Column(name = "date_updated", columnDefinition = "timestamp default now()")
   @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
   private LocalDateTime updatedAt;

   @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
   @Size(max = 50, message = "{order.orderItems.invalid}")
   @NotNull(message = "* Please enter the items you want in your order")
   @NotEmpty
   private List<OrderItem> orderItems;

   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @JoinColumn(name = "delivery_address_id", referencedColumnName = "deliveryAddressId")
   @NotNull
   @NotEmpty
   private DeliveryAddress deliveryAddress;
}
