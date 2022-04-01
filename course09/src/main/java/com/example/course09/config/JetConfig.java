package com.example.course09.config;

import com.example.course09.model.Crew;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author oldFlag
 * @since 2022/4/1
 */
@Configuration
public class JetConfig {

    @Bean
    public Crew jet() {
        Crew jet = new Crew();
        jet.setName("jet");
        jet.setSex(1);
        jet.setSpecies("human");
        return jet;
    }

}
