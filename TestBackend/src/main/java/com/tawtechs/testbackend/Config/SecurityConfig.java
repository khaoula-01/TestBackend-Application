package com.tawtechs.testbackend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF protection
                .authorizeRequests(auth -> auth
                        .requestMatchers("/customers").permitAll()
                        .requestMatchers("/customers/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/customers").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/customers/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/customers/**").authenticated()
                )
                .httpBasic(basic -> basic.disable());  // Disable HTTP Basic Authentication

        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}
