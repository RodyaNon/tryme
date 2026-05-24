package com.rodya.spring.aspect;

import com.rodya.spring.aspect.selfInvocation.service.SelfInvService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SelfInvTest {
    @Autowired
    SelfInvService selfInvService;

    @Test
    void testSelfInv(){
        selfInvService.test();
        System.out.println("问题描述:当方法被同类下方法自调用时,注解Aop不会生效。常见@Transactional,递归");
        System.out.println("方式1,自注入bean调用方法替代this调用。需加上@lazy解决自循环的问题");
        System.out.println("方式2,拆分为两个服务");
        System.out.println("=================================");
        selfInvService.test2();
        System.out.println("方式3,暴露代理@EnableAspectJAutoProxy(exposeProxy = true) + (XxService) AopContext.currentProxy();");
    }

}
