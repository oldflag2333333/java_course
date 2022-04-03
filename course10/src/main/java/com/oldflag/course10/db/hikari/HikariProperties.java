package com.oldflag.course10.db.hikari;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author oldFlag
 * @since 2022/4/3
 */
@Data
@ConfigurationProperties(prefix = "hikari")
@PropertySource("classpath:application.properties")
public class HikariProperties {

    private Integer maximumPoolSize;

}
