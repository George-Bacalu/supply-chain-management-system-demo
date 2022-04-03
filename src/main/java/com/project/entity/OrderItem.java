package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long orderItemId;

   @NotNull(message = "* Please enter the item name")
   @Size(max = 30, message = "{orderItem.name.invalid}")
   @NotBlank
   private String name;

   @NotNull(message = "* Please enter the quantity")
   @Range(min = 1, max = 1000, message = "{orderItem.quantity.invalid}")
   private Integer quantity;

   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @JoinColumn(name = "order_id", referencedColumnName = "orderId")
   @NotNull
   @NotEmpty
   private Order order;
}