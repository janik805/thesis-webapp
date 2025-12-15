package com.awesome.thesis.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity chainBuilder, AppUserService appUserService) throws Exception {
        chainBuilder.authorizeHttpRequests(
                        configurer -> configurer
                                .anyRequest().authenticated()
                )
                .oauth2Login(config ->
                        config.userInfoEndpoint(
                                info -> info.userService(appUserService))
                        );
        return chainBuilder.build();
    }
}
