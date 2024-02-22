package com.example.aop.aop;

import com.example.aop.dto.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Aspect
@Component
@Order(value = 1)
public class DecodeAop {

    @Pointcut("execution(* com.example.aop.controller..*.*(..))")
    private void cut() {

    }

    @Pointcut("@annotation(com.example.aop.annotation.Decode)")
    private void decode(){

    }

   @Before("cut()&&decode()")
    private void before(JoinPoint joinPoint) throws UnsupportedEncodingException {
       Object[] args = joinPoint.getArgs();

       for (Object arg : args) {
           if(arg instanceof User) {
               User user = User.class.cast(arg);
               String base64Email = user.getEmail();
               String email = new String(Base64.getDecoder().decode(base64Email),"UTF-8");
               user.setEmail(email);

               System.out.println(user);
           }
       }

   }

   @AfterReturning(value = "cut() && decode()", returning ="object" )
   private void afterReturning(Object object) {

       if(object instanceof User) {
           User user = User.class.cast(object);
           String base64Email = user.getEmail();
           String email = new String(Base64.getEncoder().encode(base64Email.getBytes()));
           user.setEmail(email);
           System.out.println(user);
       }


   }
}
