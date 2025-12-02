package com.example.lawson_prevost_tp1.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public FilterRegistrationBean<ApiKeyFilter> apiKeyFilterRegistration(ApiKeyFilter apiKeyFilter) {
        FilterRegistrationBean<ApiKeyFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(apiKeyFilter);
        // On applique le filtre sur toutes les routes, il décidera lui-même en fonction de la méthode HTTP
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1); // priorité haute

        return registrationBean;
    }
}
