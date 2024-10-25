package com.newhms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfiguration {
    private JWTFilter jwtFilter;

    public SecurityConfiguration(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity
    )throws Exception {
        //h(cd)2
        httpSecurity.csrf().disable().cors().disable();
        httpSecurity.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        //haap
        //httpSecurity.authorizeHttpRequests().anyRequest().permitAll();
        httpSecurity.authorizeHttpRequests().requestMatchers("/api/v1/user/login","/api/v1/user/signup","/api/v1/user/signup-property-owner")
                .permitAll().requestMatchers("/api/v1/country/addCountry")
                .hasAnyRole("OWNER","ADMIN").anyRequest().authenticated();

        return httpSecurity.build();
    }
}
