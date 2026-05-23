package com.rodya.sequence.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SeqInAspect {
    @Before("@annotation(com.rodya.sequence.aspect.annotation.SeqBefore_2)")
    public void before_2() {
        System.out.println("before_2");
    }


    @Before("@annotation(com.rodya.sequence.aspect.annotation.SeqBefore_1)")
    public void before_1() {
        System.out.println("before_1");
    }

    @Before("@annotation(com.rodya.sequence.aspect.annotation.SeqBefore_3)")
    public void before_3() {
        System.out.println("before_3");
    }


    @After("@annotation(com.rodya.sequence.aspect.annotation.SeqAfter_1)")
    public void after_1() {
        System.out.println("after_1");
    }
    @After("@annotation(com.rodya.sequence.aspect.annotation.SeqAfter_2)")
    public void after_2() {
        System.out.println("after_2");
    }

    @After("@annotation(com.rodya.sequence.aspect.annotation.SeqAfter_3)")
    public void after_3() {
        System.out.println("after_3");
    }


    @Around("@annotation(com.rodya.sequence.aspect.annotation.SeqAround_3)")
    public Object around_3(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around_3");
        Object result = joinPoint.proceed();
        System.out.println("around_3 end");
        return result;
    }
    @Around("@annotation(com.rodya.sequence.aspect.annotation.SeqAround_1)")
    public Object bround_1(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around_1");
        Object result = joinPoint.proceed();
        System.out.println("around_1 end");
        return result;
    }


    @Around("@annotation(com.rodya.sequence.aspect.annotation.SeqAround_2)")
    public Object around_2(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around_2");
        Object result = joinPoint.proceed();
        System.out.println("around_2 end");
        return result;
    }
}
