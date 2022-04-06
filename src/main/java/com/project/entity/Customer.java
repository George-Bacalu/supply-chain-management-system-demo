package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer implements Serializable {

   @Serial
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long customerId;

   @Column(nullable = false)
   @Size(max = 30, message = "{customer.name.invalid}")
   private String name;

   @Column(nullable = false)
   @Pattern(regexp="(^$|[0-9]{10})", message = "{customer.phoneNumber.invalid}")
   private String phoneNumber;
}
