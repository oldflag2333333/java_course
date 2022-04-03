package com.oldflag.course10.config;

import com.oldflag.course10.model.School;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author oldFlag
 * @since 2022/4/2
 */
@Configuration
@EnableConfigurationProperties(value = SchoolProperties.class)
public class SchoolConfig {

    @Autowired
    private SchoolProperties schoolProperties;

    @Bean
    public School school() {
        Gson gson = new Gson();

        System.out.println(gson.toJson(schoolProperties));
        School school = new School();
        school.setClasses(schoolProperties.getClasses());
        school.setCollageName(schoolProperties.getCollageName());

        return school;
    }


}
