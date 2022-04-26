package com.example.course13;

import com.example.course13.config.DBProperties;
import com.example.course13.dao.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Course13ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private DBProperties dbProperties;

    @Test
    public void getDBProperties() {
        System.out.println(GsonUtil.toJson(dbProperties));
    }

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testSlave() {
        System.out.println(GsonUtil.toJson(orderMapper.getOrderFromSlave()));
    }

    @Test
    public void testMaster() {
        System.out.println(GsonUtil.toJson(orderMapper.getOrderFromMaster()));
    }

    @Test
    public void testDefault() {
        System.out.println(GsonUtil.toJson(orderMapper.getOrder()));
    }
}
