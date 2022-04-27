package com.example.course13.aspect;

import com.example.course13.anno.Load;
import com.example.course13.db.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author oldFlag
 * @since 2022/4/25
 */
@Aspect
@Order(1)
@Component
public class LoadAspect {


    @Before("@annotation(load)")
    public void before(Load load) {
        String node = load.value();

        System.out.println("setting context");
        DBContextHolder.setContext(node);
    }


}
