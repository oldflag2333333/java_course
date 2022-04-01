package com.example.course09;

import com.example.course09.model.Crew;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author oldFlag
 * @since 2022/4/1
 */
@SpringBootTest
public class AnnoLoadTests {

    @Autowired
    private Crew crew;

    @Test
    public void testCrew() {
        System.out.println(crew);

    }

}
