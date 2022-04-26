package com.example.course13.aspect;

import com.example.course13.anno.SQL;
import com.example.course13.db.DBContextHolder;
import com.example.course13.db.DataSourceRouter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author oldFlag
 * @since 2022/4/26
 */
@Aspect
@Order(2)
@Component
public class SQLAspect {

    @Autowired
    private DataSourceRouter dataSourceRouter;

    @Around("@annotation(SQL)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, SQL SQL) {
        Class clazz = SQL.value();
        try {
            System.out.println("executing sql");
            String sql = (String) proceedingJoinPoint.proceed();
            Connection conn = dataSourceRouter.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println(conn.getMetaData().getURL());

            resultSet.next();
            Integer id = resultSet.getInt(1);
            Field field = clazz.getDeclaredField("id");
            field.setAccessible(true);
            Object o = clazz.newInstance();
            field.set(o, id);

            return o;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            DBContextHolder.clear();
        }
    }

}
