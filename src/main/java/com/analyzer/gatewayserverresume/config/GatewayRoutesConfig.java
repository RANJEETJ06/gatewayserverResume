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
                .route("ResumeAnalysis", r -> r
                        .path("/Resume/api/ai/**")
                        .filters(f -> f.rewritePath("/Resume/api/ai/(?<segment>.*)", "/api/ai/${segment}"))
                        .uri("lb://RESUMEANALYSIS")  // App name in Eureka
                )

                // Route for Resume microservice
                .route("Resume", r -> r
                        .path("/Resume/api/resume/**")
                        .filters(f -> f.rewritePath("/Resume/api/resume/(?<segment>.*)", "/api/resume/${segment}"))
                        .uri("lb://RESUME")  // App name in Eureka
                )

                .build();
    }
}
