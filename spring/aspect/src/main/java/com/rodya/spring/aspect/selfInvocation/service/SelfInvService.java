package com.rodya.spring.aspect.selfInvocation.service;

import com.rodya.spring.aspect.selfInvocation.aspect.annotation.SelfInvLog;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class SelfInvService {

    @Autowired
    @Lazy
    SelfInvService selfInvService;
    @SelfInvLog
    public void inner(){
        System.out.println("inner方法");
    }


    public void test(){
        System.out.println("test");
       selfInvService.inner();
    }

    public void test2(){
        System.out.println("test2");
        inner();
        SelfInvService proxy = (SelfInvService) AopContext.currentProxy();
        proxy.inner();
    }
}
