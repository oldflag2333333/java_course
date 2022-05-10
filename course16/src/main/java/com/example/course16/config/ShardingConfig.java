package com.example.course16.config;

import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * @author oldFlag
 * @since 2022/5/10
 */
@Configuration
public class ShardingConfig {

    @Bean("shardingDataSource")
    public DataSource createDataSource() throws SQLException {
        // Build data source map
        try (InputStream resourceAsStream = ShardingConfig.class.getClassLoader().getResourceAsStream("config-sharding.yaml")) {
            int length = resourceAsStream.available();
            byte[] byteArray = new byte[length];
            resourceAsStream.read(byteArray);

            resourceAsStream.close();
            return YamlShardingSphereDataSourceFactory.createDataSource(byteArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
