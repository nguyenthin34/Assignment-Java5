package com.poly.config;

import com.poly.service.DotEnvService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Autowired
    DotEnvService dotEnvService;
    @Bean("dataSource")
    public DataSource getDataSource(){
        //config bằng hikariDataSource
        HikariConfig    hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(dotEnvService.getValueDotenv("DB_CLASSNAME"));
        hikariConfig.setJdbcUrl(dotEnvService.getValueDotenv("DB_URL"));
        hikariConfig.setUsername(dotEnvService.getValueDotenv("DB_USERNAME"));
        hikariConfig.setPassword(dotEnvService.getValueDotenv("DB_PASSWORD"));
        return new HikariDataSource(hikariConfig);
    }
}
