package com.example.course09;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author oldFlag
 * @since 2022/4/2
 */

@SpringBootTest(classes = Course09Application.class)
public class SchoolTest {

    @Autowired
    private School school;

    void testSchool() {


    }


}
