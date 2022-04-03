package com.oldflag.course10.db.hikari;

import com.oldflag.course10.db.classic.DBProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author oldFlag
 * @since 2022/4/3
 */
@Configuration
@EnableConfigurationProperties(value = {DBProperties.class, HikariProperties.class})
public class MyHikariConfig {

    @Autowired
    private DBProperties dbProperties;

    @Autowired
    private HikariProperties hikariProperties;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dbProperties.getAddress());
        hikariConfig.setUsername(dbProperties.getUser());
        hikariConfig.setPassword(dbProperties.getPassword());
        hikariConfig.setMaximumPoolSize(hikariProperties.getMaximumPoolSize());
        return new HikariDataSource(hikariConfig);
    }

}
