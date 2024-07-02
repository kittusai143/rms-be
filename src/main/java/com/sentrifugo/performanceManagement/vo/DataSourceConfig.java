//package com.sentrifugo.pmodashboard.vo;
//import javax.sql.DataSource;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Primary
//    @Bean(name = "primaryDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource primaryDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "trainingDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.training")
//    public DataSource trainingDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//}
