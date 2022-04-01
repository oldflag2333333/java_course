package com.example.course09;

import com.example.course09.model.Crew;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author oldFlag
 * @since 2022/4/1
 */
@SpringBootTest
public class ConfigLoadTest {

    @Autowired
    @Qualifier("jet")
    private Crew jet;

    @Test
    public void testJetSex() {
        System.out.println(jet.getSex());
    }
}
