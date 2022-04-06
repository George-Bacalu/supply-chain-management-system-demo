package com.project.config;

import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.project.constant.UserAuthorityConstants.*;

@Configuration
@ComponentScan({"com.project.aspect", "com.project.repository", "com.project.service", "com.project.controller"})
@EnableAspectJAutoProxy
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   @Lazy
   @Autowired
   private UserService userService;

   @Bean
   public BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
      auth.setUserDetailsService(userService);
      auth.setPasswordEncoder(passwordEncoder());
      return auth;
   }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) {
      auth.authenticationProvider(authenticationProvider());
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
              .mvcMatchers("/admin").hasRole(ROLE_ADMIN)
              .mvcMatchers("/manufacturer").hasAnyRole(ROLE_ADMIN, ROLE_MANUFACTURER)
              .mvcMatchers("/client").hasAnyRole(ROLE_ADMIN, ROLE_CLIENT)
              .mvcMatchers("/registration").permitAll()
              .anyRequest()
              .authenticated()
              .and()
              .formLogin()
              .loginPage("/login")
              .permitAll()
              .and()
              .logout()
              .invalidateHttpSession(true)
              .clearAuthentication(true)
              .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
              .logoutSuccessUrl("/login?logout")
              .permitAll();
   }
}
