package com.example.course13;

import com.example.course13.config.DBProperties;
import com.example.course13.dao.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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

    @Autowired
    @Qualifier("shardingDataSource")
    private DataSource shardingDataSource;

    @Test
    public void shardingTest() throws SQLException {
        Connection connection = shardingDataSource.getConnection();
        Statement statement = connection.createStatement();

        String sql1 = "select * from `order` limit 5";
        statement.execute(sql1);
        statement.execute(sql1);

        String sql2 = "insert into `order` (create_time, modify_time, user_id, order_status, order_amount, pay_time,\n" +
                "                     commodity_id, order_snapshot)\n" +
                "VALUES (now(), now(), ceiling(rand() * 100), 1, rand(), now(), ceiling(rand() * 100), '{}')";

        statement.execute(sql2);
        statement.execute(sql1);
//        System.out.println(GsonUtil.toJson(connection));
    }
}
