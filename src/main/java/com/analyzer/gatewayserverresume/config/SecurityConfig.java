package com.analyzer.gatewayserverresume.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity security){
        security.authorizeExchange(exchanges->exchanges
                .pathMatchers(HttpMethod.POST).permitAll()
                .anyExchange().authenticated()
        ).oauth2Login(Customizer.withDefaults());
        security.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return security.build();
    }
}
