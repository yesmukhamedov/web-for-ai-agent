package com.example.aiservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/admin/**", "/api/v1/admin/**").authenticated()
            .requestMatchers("/h2-console/**").permitAll()
            .anyRequest().permitAll()
        )
        .httpBasic(Customizer.withDefaults())
        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService(
      @Value("${admin.username}") String username,
      @Value("${admin.password}") String password
  ) {
    UserDetails user = User.withUsername(username)
        .password("{noop}" + password)
        .roles("ADMIN")
        .build();
    return new InMemoryUserDetailsManager(user);
  }
}
