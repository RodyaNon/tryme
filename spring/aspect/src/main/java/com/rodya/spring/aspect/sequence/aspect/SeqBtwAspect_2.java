package com.rodya.spring.aspect.sequence.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class SeqBtwAspect_2 {
    @Before("@annotation(com.rodya.spring.aspect.sequence.aspect.annotation.SeqBeforeBtw)")
    public void before() {
        System.out.println("SeqBtwAspect_2");
    }
}
