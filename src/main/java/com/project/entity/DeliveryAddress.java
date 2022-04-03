package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "delivery_address")
public class DeliveryAddress implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long deliveryAddressId;

   @NotNull(message = "* Country")
   @Size(max = 30, message = "{deliveryAddress.country.invalid}")
   @NotBlank
   private String country;

   @NotNull(message = "* City")
   @Size(max = 30, message = "{deliveryAddress.city.invalid}")
   @NotBlank
   private String city;

   @NotNull(message = "* Street")
   @Size(max = 30, message = "{deliveryAddress.street.invalid}")
   @NotBlank
   private String street;

   @NotNull(message = "* Phone Number")
   @Pattern(regexp="(^$|[0-9]{10})", message = "{deliveryAddress.phoneNumber.invalid}")
   @NotBlank
   private String phoneNumber;

   @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "deliveryAddress")
   @Size(max = 50, message = "{order.order.invalid}")
   @NotNull
   @NotEmpty
   private List<Order> order;
}
