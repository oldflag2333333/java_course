package com.oldflag.course10.db.classic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * @author oldFlag
 * @since 2022/4/3
 */
@Configuration
@EnableConfigurationProperties(value = DBProperties.class)
public class DBConfig {

    @Autowired
    private DBProperties dbProperties;

    @Bean
    public ClassicConnManager classicConnManager() throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.jdbc.Driver");
        return new ClassicConnManager(dbProperties.getAddress(), dbProperties.getUser(), dbProperties.getPassword());
    }

}
