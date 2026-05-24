package com.rodya.spring.aspect.selfInvocation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SelfInvAspect {
    @Around("@annotation(com.rodya.spring.aspect.selfInvocation.aspect.annotation.SelfInvLog)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("========自调用AOP日志========");
        return joinPoint.proceed();
    }
}
