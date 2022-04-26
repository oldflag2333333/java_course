package com.example.course13.aspect;

import com.example.course13.anno.Load;
import com.example.course13.db.DBContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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


    @Around("@annotation(load)")
    public Object wrapper(ProceedingJoinPoint proceedingJoinPoint, Load load) throws Throwable {
        String node = load.value();

        System.out.println("setting context");
        DBContextHolder.setContext(node);
        return proceedingJoinPoint.proceed();
    }


}
