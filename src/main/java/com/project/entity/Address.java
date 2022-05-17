package com.project.entity;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;

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

   @NotNull
   @Size(max = 30)
   private String country;

   @NotNull
   @Size(max = 30)
   private String city;

   @NotNull
   @Size(max = 30)
   private String street;

   @NotNull
   @Range(min = 1, max = 1000)
   private Integer number;
}
