package com.security.springsecuritytest.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfig {
    @Bean
    public DataSource dataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.username("waganawa");
        dataSourceBuilder.password("gachonackr3");
        dataSourceBuilder.url("jdbc:mysql://database-1.c78kyz1h0q9p.us-east-2.rds.amazonaws.com:3306/testDB_new_test1?createDatabaseIfNotExist=true&serverTimezone=UTC&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=False");
        return dataSourceBuilder.build();
    }
}
