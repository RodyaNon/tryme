package com.rodya.mysql.dbrouter;

import com.rodya.mysql.dbrouter.annotation.DBRouter;
import com.rodya.mysql.dbrouter.config.DBRouterConfig;
import com.rodya.mysql.dbrouter.strategy.IDBRouterStrategy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class DBRouterJoinPoint {

    private DBRouterConfig dbRouterConfig;

    private IDBRouterStrategy dbRouterStrategy;

    public DBRouterJoinPoint(DBRouterConfig dbRouterConfig,IDBRouterStrategy dbRouterStrategy) {
        this.dbRouterConfig = dbRouterConfig;
        this.dbRouterStrategy = dbRouterStrategy;
    }

    @Pointcut("@annotation(com.rodya.mysql.dbrouter.annotation.DBRouter)")
    public void dbRouterPoint() {
    }

    @Around("dbRouterPoint() && @annotation(dbRouter)")
    public Object doRouter(ProceedingJoinPoint jp, DBRouter dbRouter) throws Throwable {
        log.info("DBRouterJoinPoint.doRouter()");
        return jp.proceed();
    }
}
