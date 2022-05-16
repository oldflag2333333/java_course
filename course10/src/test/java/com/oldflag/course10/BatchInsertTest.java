package com.oldflag.course10;

import com.oldflag.course10.db.classic.ClassicJDBC;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * @author oldFlag
 * @since 2022/4/17
 */
@SpringBootTest
public class BatchInsertTest {

    private static final String TEMPLATE = "insert into `order` (user_id, order_status, order_amount, pay_time, commodity_id," +
            "order_snapshot) value (%s, %s, %s, now(), %s, '{}')";

    private static final String TEMPLATE1 = "insert into `order` (user_id, order_status, order_amount, pay_time, commodity_id," +
            "order_snapshot) values ";

    private static final String TEMPLATE2 = "(%s, %s, %s, now(), %s, '{}')";

    private static final String PREPARED = "insert into `order` (user_id, order_status, order_amount, pay_time, commodity_id," +
            "order_snapshot) values (?, ?, ?, now(), ?, '{}');";

    private static final int AMOUNT = 10_000;

    private static final int UNIT = 10_0000;

    private static final Random random = new Random(1);

    @Autowired
    private ClassicJDBC classicJDBC;

    @Test
    public void test() {
        System.out.println(sqlSupplierSingle(1000));
    }


    /**
     * 单条循环写入
     * 插入1w 110s
     *
     * @throws SQLException
     */
    @Test
    public void test1() throws SQLException {
        long start = System.currentTimeMillis();
        Statement statement = classicJDBC.getStatement();
        for (int i = 0; i < AMOUNT; i++) {
            String sql = sqlSupplierSingle(1);
            classicJDBC.save(statement, sql);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 使用 insert into (...) values (...),(...) 方式插入
     * 插入100w 13.425 s
     *
     * @throws SQLException
     */
    @Test
    public void test2() throws SQLException {
        long start = System.currentTimeMillis();
        boolean flag = AMOUNT % UNIT > 0;
        int times = (AMOUNT / UNIT) + (flag ? 1 : 0);
        Statement statement = classicJDBC.getStatement();
        for (int i = 0; i < times; i++) {
            int amount = ((i == times - 1) && flag) ? AMOUNT % UNIT : UNIT;
            String sql = sqlSupplierMulti(amount);
            classicJDBC.save(statement, sql);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 使用 preparedStatement 插入
     * 插入1w 225.6s
     *
     * @throws SQLException
     */
    @Test
    public void test3() throws SQLException {
        long start = System.currentTimeMillis();
        PreparedStatement statement = classicJDBC.prepare(PREPARED);

        for (int i = 0; i < AMOUNT; i++) {
            prepareStatement(statement);
            classicJDBC.preparedSave(statement);
            statement.clearParameters();
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 优化 preparedStatement 插入
     * 插入1w 条 60s
     *
     * @throws SQLException
     */
    @Test
    public void test4() throws SQLException {
        long start = System.currentTimeMillis();
        PreparedStatement statement = classicJDBC.prepare(PREPARED);

        for (int i = 0; i < AMOUNT; i++) {
            prepareStatement(statement);
            statement.addBatch();
            statement.clearParameters();
        }

        classicJDBC.preparedSaveBatch(statement);
        System.out.println(System.currentTimeMillis() - start);
    }


    private String sqlSupplierSingle(int sum) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sum; i++) {
            sb.append(String.format(TEMPLATE,
                    Math.abs(random.nextInt(10000)),
                    Math.abs(random.nextInt(3)),
                    this.getDecimal(),
                    Math.abs(random.nextInt(10000))));
            sb.append(";").append("\n");
        }

        return sb.toString();
    }

    private String sqlSupplierMulti(int sum) {
        StringBuilder sb = new StringBuilder(TEMPLATE1);
        for (int i = 0; i < sum; i++) {
            sb.append(String.format(TEMPLATE2,
                    Math.abs(random.nextInt(10000)),
                    Math.abs(random.nextInt(100)),
                    this.getDecimal(),
                    Math.abs(random.nextInt(10000))));
            if (i == sum - 1) {
                sb.append(";");
            } else {
                sb.append(",\n");
            }
        }

        return sb.toString();
    }

    private void prepareStatement(PreparedStatement statement) throws SQLException {
        statement.setInt(1, random.nextInt(10000));
        statement.setInt(2, random.nextInt(100));
        statement.setBigDecimal(3, this.getDecimal());
        statement.setInt(4, random.nextInt(10000));
    }

    private BigDecimal getDecimal() {
        return BigDecimal.valueOf(random.nextDouble()).abs().setScale(2, RoundingMode.HALF_UP);
    }


}
