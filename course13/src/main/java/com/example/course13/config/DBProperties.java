package com.example.course13.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * @author oldFlag
 * @since 2022/4/25
 */
@Data
@ConfigurationProperties(prefix = "db")
@PropertySource("classpath:application.properties")
public class DBProperties {

    private String user;
    private String password;
    private Map<String, String> address;

}
