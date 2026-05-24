package com.rodya.spring.aspect;

import com.rodya.spring.aspect.declareParents.monitorable.Monitorable;
import com.rodya.spring.aspect.declareParents.service.DeclareParentService;
import com.rodya.spring.aspect.declareParents.service.DeclareParentService2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeclareParentTest {
    @Autowired
    DeclareParentService declareParentService;

    @Autowired
    DeclareParentService2 declareParentService2;

    @Test
    void testDeclareParent(){
        declareParentService.play();
        Monitorable monitorable = (Monitorable) declareParentService;
        monitorable.setValue(false);
        declareParentService.play();
        System.out.println("==========");
        System.out.println("spring aop非侵入式增强类,会在类加载时期增强方法,增强方法注意变量的并发问题");
    }

    @Test
    void testMonitorableStateSharing() {
        System.out.println("=== 测试 Monitorable 状态是否全局共享 ===");

        Monitorable monitorable1 = (Monitorable) declareParentService;

        System.out.println("初始状态 - Service1: " + monitorable1.getValue());

        monitorable1.setValue(false);
        System.out.println("设置 Service1 为 false 后: " + monitorable1.getValue());

        declareParentService.play();

        System.out.println("再次获取 Service1 状态: " + monitorable1.getValue());

        if (!monitorable1.getValue()) {
            System.out.println("结论: 状态是持久的（保存在代理对象中）");
        } else {
            System.out.println("结论: 状态未持久化（可能是原型模式或每次重新创建）");
        }
    }
    @Test
    void testMonitorableGlobalState() {
        System.out.println("=== 测试 Monitorable 状态是否全局共享 ===\n");

        Monitorable monitorable1 = (Monitorable) declareParentService;
        Monitorable monitorable2 = (Monitorable) declareParentService2;

        System.out.println("步骤1: 查看初始状态");
        System.out.println("  Service1 状态: " + monitorable1.getValue());
        System.out.println("  Service2 状态: " + monitorable2.getValue());

        System.out.println("\n步骤2: 只修改 Service1 的状态为 false");
        monitorable1.setValue(false);
        System.out.println("  Service1 状态: " + monitorable1.getValue());
        System.out.println("  Service2 状态: " + monitorable2.getValue());

        System.out.println("\n步骤3: 调用 Service2 的方法（触发 AOP）");
        declareParentService2.play();

        if (!monitorable2.getValue()) {
            System.out.println("\n❌ 结论: 状态是全局共享的！");
            System.out.println("   修改 Service1 影响了 Service2，说明它们共用同一个 MonitorableImpl 实例");
        } else {
            System.out.println("\n✓ 结论: 状态不是全局共享的");
            System.out.println("  每个 Service 有自己独立的 MonitorableImpl 实例");
        }
    }

}
