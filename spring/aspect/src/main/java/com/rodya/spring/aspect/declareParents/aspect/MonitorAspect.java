package com.rodya.spring.aspect.declareParents.aspect;

import com.rodya.spring.aspect.declareParents.monitorable.Monitorable;
import com.rodya.spring.aspect.declareParents.monitorable.Impl.MonitorableImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MonitorAspect {

    @DeclareParents(value = "com.rodya.spring.aspect.declareParents.service.DpService+",
            defaultImpl = MonitorableImpl.class)
    private Monitorable monitorable;

    @Around("execution(* com.rodya.spring.aspect.declareParents.service.DeclareParent*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Monitorable monitor = (Monitorable) point.getThis();
        if(monitor.getValue()){
            System.out.println("【AOP监控开启】方法执行前记录日志");
        }

        Object result = point.proceed();

        if(monitor.getValue()){
            System.out.println("【AOP监控开启】方法执行后统计耗时");
        }
        return result;
    }
}


