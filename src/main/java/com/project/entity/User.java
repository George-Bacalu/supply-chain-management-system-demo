package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "emailId"))
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long userId;

   private String firstName;
   private String lastName;
   private String emailId;
   private String password;

   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinTable(
           name = "users_roles",
           joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
           inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId")
   )
   private List<Role> roles;

   public User(String firstName, String lastName, String emailId, String password, List<Role> roles) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.emailId = emailId;
      this.password = password;
      this.roles = roles;
   }
}
