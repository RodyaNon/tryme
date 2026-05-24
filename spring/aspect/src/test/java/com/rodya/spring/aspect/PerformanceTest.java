package com.rodya.spring.aspect;

import com.rodya.spring.aspect.performance.service.AspectTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PerformanceTest {

    private static final int LOOP_COUNT = 100000;
    private static final int WARMUP_COUNT = 5000;

    @Autowired
    AspectTestService aspectTestService;

    @Test
    void testAspect(){

        warmup();

        long start = 0;
        start = System.nanoTime();
        for (int i = 0; i < LOOP_COUNT; i++) {
            aspectTestService.beforeMethod();

        }
        long endBefore = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < LOOP_COUNT; i++) {
            aspectTestService.afterMethod();
        }
        long endAfter = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < LOOP_COUNT; i++) {
            aspectTestService.aroundMethod();
        }
        long endAround = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < LOOP_COUNT; i++) {
            aspectTestService.beforeAndAfterMethod();
        }
        long beforeAndAfter = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < LOOP_COUNT; i++) {
            aspectTestService.commonMethod();
        }
        long common = System.nanoTime() - start;


        System.out.println("===== AOP通知性能耗时(纳秒) 执行" + LOOP_COUNT + "次 =====");
        System.out.println("Before 通知耗时：" + endBefore);
        System.out.println("After  通知耗时：" + endAfter);
        System.out.println("Around 通知耗时：" + endAround);
        System.out.println("BAfter 通知耗时：" + beforeAndAfter);
        System.out.println("common 通知耗时：" + common);
    }

    private void warmup(){
        for (int i = 0; i < WARMUP_COUNT; i++) {
            aspectTestService.beforeMethod();
            aspectTestService.afterMethod();
            aspectTestService.aroundMethod();
            aspectTestService.beforeAndAfterMethod();
        }
        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
