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
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("9799");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/testdb1?createDatabaseIfNotExist=true&useUnicode=true&charaterEncoding=UTF-8&characterSetResults=UTF-8&allowPublicKeyRetrieval=true&useSSL=false");
        return dataSourceBuilder.build();
    }
}
