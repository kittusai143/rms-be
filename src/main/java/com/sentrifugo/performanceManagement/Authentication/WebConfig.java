//package com.sentrifugo.performanceManagement.Authentication;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private JwtTokenAuthenticationFilter jwtTokenFilter;
//
//    @Bean
//    public FilterRegistrationBean<JwtTokenAuthenticationFilter> jwtFilter() {
//        FilterRegistrationBean<JwtTokenAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(jwtTokenFilter);
//        registrationBean.addUrlPatterns("*"); // Apply the filter to specific URL patterns
//        return registrationBean;
//    }
//}
