package com.project.config;

import com.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.project.constant.UserAuthorityConstants.ROLE_ADMIN;
import static com.project.constant.UserAuthorityConstants.ROLE_CLIENT;
import static com.project.constant.UserAuthorityConstants.ROLE_MANUFACTURER;

@Configuration
@ComponentScan({"com.project.aspect", "com.project.repository", "com.project.service", "com.project.controller"})
@RequiredArgsConstructor
@EnableAspectJAutoProxy
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   //TODO: extends the security so my app can accept different types of users and provide access to resources based on their role

   @Lazy
   private final UserService userService;

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
      http.httpBasic();
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
