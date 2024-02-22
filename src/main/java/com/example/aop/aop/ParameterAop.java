package com.example.aop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(value = 2)
public class ParameterAop {

    @Pointcut("execution(* com.example.aop.controller..*.*(..))")
    public void cut() {

    }

    @Before("cut()")
    public void beFore(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();

        for (Object obj : args) {
            System.out.println("type : "+ obj.getClass().getSimpleName());
            System.out.println("value : " + obj );
        }
    }

    @AfterReturning(value = "cut()",returning = "returnObj")
    public void afterReturn(JoinPoint joinPoint, Object returnObj) {

        System.out.println("return obj");
        System.out.println(returnObj);
    }
}
