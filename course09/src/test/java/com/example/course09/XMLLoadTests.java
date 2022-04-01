package com.example.course09;

import com.example.course09.model.Crew;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
class XMLLoadTests implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testSpike() {
        Object obj = applicationContext.getBean("Spike");
        Crew spike = (Crew) obj;
        System.out.println(spike.getName());
    }

}
