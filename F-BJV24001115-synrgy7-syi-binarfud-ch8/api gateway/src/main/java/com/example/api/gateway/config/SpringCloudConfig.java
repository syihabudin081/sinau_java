package com.example.api.gateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("binarfud", r-> r.path("/binarfud/**")
                        .uri("http://localhost:8080/")

                )
                .route("notification-service", r-> r.path("/notification/**")
                        .uri("http://localhost:8086/")
                )
                .build();
    }
}
