package com.oldflag.course10;

import com.oldflag.course10.db.classic.ClassicJDBC;
import com.oldflag.course10.model.School;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class Course10ApplicationTests {

    @Autowired
    private School school;

    @Autowired
    private ClassicJDBC classicJDBC;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
        System.out.println(school);
    }

    @Test
    void selectTest() throws SQLException {
        List<String> keySet = new ArrayList<>();
        keySet.add("id");
        keySet.add("name");
        classicJDBC.select("select * from crew", keySet);
    }

    @Test
    void saveTest1() throws SQLException {
        System.out.println(classicJDBC.save("insert into crew (id, name) values (1, 'spike');"));
        System.out.println(classicJDBC.save("insert into crew (id, name) values (2, 'jet');"));
        System.out.println(classicJDBC.save("insert into crew (id, name) values (3, 'faye');"));
        System.out.println(classicJDBC.save("insert into crew (id, name) values (4, 'ain');"));
    }

    @Test
    void saveTest2() throws SQLException {
        classicJDBC.prepareSave("insert into crew (id, name)\n" +
                "values (1, 'spike'),\n" +
                "       (2, 'jet'),\n" +
                "       (3, 'faye'),\n" +
                "       (4, 'ain');\n");
    }


    @Test
    void testHikari() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from crew");
        System.out.println(GsonUtil.toJson(maps));
    }
}
