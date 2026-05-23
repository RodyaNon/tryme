package com.rodya.performance.service;

import com.rodya.performance.aspect.annotation.TestAfter;
import com.rodya.performance.aspect.annotation.TestAround;
import com.rodya.performance.aspect.annotation.TestBefore;
import org.springframework.stereotype.Service;

@Service
public class AspectTestService {
    @TestBefore
    public void beforeMethod() {
        test();
    }

    @TestAfter
    public void afterMethod() {
        test();
    }

    @TestAround
    public void aroundMethod() {
        test();
    }

    @TestBefore
    @TestAfter
    public void beforeAndAfterMethod() {
        test();
    }

    public void commonMethod() {
        test();
    }

    private void test(){
        int num = 0;
        for (int i = 0; i < 1000000; i++) {
            num += i;
        }
    }
}
