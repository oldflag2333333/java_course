package com.example.course10;

import com.example.course10.model.School;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Course10ApplicationTests {

    @Autowired
    private School school;

    @Test
    void contextLoads() {
        System.out.println(school);
    }

}
