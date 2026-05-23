package com.rodya.performance.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    @Pointcut("@annotation(com.rodya.performance.aspect.annotation.TestAround)")
    public void testAround() {
    }
    @Pointcut("@annotation(com.rodya.performance.aspect.annotation.TestBefore)")
    public void testBefore() {
    }
    @Pointcut("@annotation(com.rodya.performance.aspect.annotation.TestAfter)")
    public void testAfter() {
    }

    @Before("testBefore()")
    public void beforeAdvice() {
    }

    @After("testAfter()")
    public void afterAdvice() {
    }

    @Around("testAround()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
