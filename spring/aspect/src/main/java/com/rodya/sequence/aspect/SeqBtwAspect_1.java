package com.rodya.sequence.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(2)
public class SeqBtwAspect_1 {
    @Before("@annotation(com.rodya.sequence.aspect.annotation.SeqBeforeBtw)")
    public void before() {
        System.out.println("SeqBtwAspect_1");
    }
}
