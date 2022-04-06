package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long addressId;

   @Column(nullable = false)
   @Size(max = 30, message = "{address.country.invalid}")
   private String country;

   @Column(nullable = false)
   @Size(max = 30, message = "{address.city.invalid}")
   private String city;

   @Column(nullable = false)
   @Size(max = 30, message = "{address.street.invalid}")
   private String street;

   @Column(nullable = false)
   @Range(min = 1, max = 1000, message = "{address.number.invalid}")
   private Integer number;

   @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "address")
   @Size(max = 50, message = "{address.order.invalid}")
   private List<Order> order;
}
