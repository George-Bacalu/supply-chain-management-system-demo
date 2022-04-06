package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long productId;

   @Column(nullable = false)
   @Size(max = 30, message = "{product.name.invalid}")
   private String name;

   @Column(nullable = false)
   @Range(min = 1, max = 1000, message = "{product.price.invalid}")
   private Double price;

   @Column(nullable = false)
   @Range(min = 1, max = 1000, message = "{product.quantity.invalid}")
   private Integer quantity;
}
