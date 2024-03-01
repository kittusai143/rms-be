package com.sentrifugo.performanceManagement.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtTokenAuthenticationFilter jwtTokenFilter;

    @Bean
    public FilterRegistrationBean<JwtTokenAuthenticationFilter> jwtFilter() {
        FilterRegistrationBean<JwtTokenAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(jwtTokenFilter);
        // Ensure the URL pattern is correctly specified. For all URLs, use "/*" instead of "*"
        registrationBean.addUrlPatterns("/*"); // Apply the filter to specific URL patterns
        return registrationBean;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Use specific origins as per your requirements for better security
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}