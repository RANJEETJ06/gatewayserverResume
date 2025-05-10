package com.analyzer.gatewayserverresume.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // Route for ResumeAnalysis microservice
                .route(r -> r
                        .path("/resume/ai/**")  // Match this full external path
                        .filters(f -> f
                                .rewritePath("/resume/ai/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://RESUMEANALYSIS")  // Eureka service name
                )

                // Route for Resume microservice
                .route(r -> r
                        .path("/resume/api/**")
                        .filters(f -> f.rewritePath("/resume/api/(?<segment>.*)", "/${segment}"))
                        .uri("lb://RESUME")
                )

                .build();
    }
}
