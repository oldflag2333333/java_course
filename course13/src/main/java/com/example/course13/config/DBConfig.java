package com.example.course13.config;

import com.example.course13.db.DBConstants;
import com.example.course13.db.DataSourceRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author oldFlag
 * @since 2022/4/25
 */
@Configuration
@EnableConfigurationProperties(value = DBProperties.class)
public class DBConfig {

    @Autowired
    private DBProperties dbProperties;

    @Bean
    public DataSourceRouter classicConnManager() {
        Map<Object, Object> targetDataSources = new HashMap<>();

        dbProperties.getAddress().forEach((key, value) -> targetDataSources.put(key,
                this.dataSource(dbProperties.getUser(), dbProperties.getPassword(), value)));

        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        dataSourceRouter.setDefaultTargetDataSource(targetDataSources.get(DBConstants.MASTER));
        dataSourceRouter.setTargetDataSources(targetDataSources);
        return dataSourceRouter;
    }

    private DataSource dataSource(String user, String password, String url) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setUrl(url);

        return dataSource;
    }

}
