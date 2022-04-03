package com.oldflag.course10.db.classic;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author oldFlag
 * @since 2022/4/3
 */
@ConfigurationProperties(prefix = "db")
@PropertySource("classpath:application.properties")
public class DBProperties {

    private String address;
    private String user;
    private String password;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
