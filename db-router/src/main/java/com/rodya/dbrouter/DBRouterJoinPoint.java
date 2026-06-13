package com.rodya.dbrouter;

import com.rodya.dbrouter.annotation.DBRouter;
import com.rodya.dbrouter.config.DBRouterConfig;
import com.rodya.dbrouter.strategy.IDBRouterStrategy;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import java.lang.reflect.Field;

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
        String dbKey = dbRouter.value();
        if(StringUtils.isBlank(dbKey) && StringUtils.isBlank(dbRouterConfig.getRouterKey())){
            throw new RuntimeException("@DBRouter注解的value属性值不能为空");
        }
        dbKey = StringUtils.isNotBlank(dbKey) ? dbKey : dbRouterConfig.getRouterKey();
        String dbKeyAttr = getAttrValue(dbKey, jp.getArgs());
        dbRouterStrategy.doRouter(dbKeyAttr);
        try {
            return jp.proceed();
        } finally {
            dbRouterStrategy.clear();
        }
    }
    /**
     * 入参为key,和原方法入参
     *
     */
    private String getAttrValue(String attr, Object[] args) {
        if(1 == args.length){
            Object arg = args[0];
            if(arg instanceof String){
                return arg.toString();
            }
        }
        String filedValue = null;
        for (Object arg : args) {
            try {
                if (StringUtils.isNotBlank(filedValue)) {
                    break;
                }
                filedValue = String.valueOf(this.getValueByName(arg, attr));
            } catch (Exception e) {
                log.error("获取路由属性异常", e);
            }
        }
        return filedValue;
    }

    private Object getValueByName(Object item, String name){
        try {
           Field field = getFieldByName(item, name);
           if(field == null){
               return  null;
           }
           field.setAccessible(true);
           Object o = field.get(item);
           field.setAccessible(false);
           return o;
        } catch (Exception e) {
            return null;
        }
    }
    private Field getFieldByName(Object item, String name){
        try{
            Field field;
            try{
                field = item.getClass().getDeclaredField(name);
            }catch (NoSuchFieldException e){
                field = item.getClass().getSuperclass().getDeclaredField(name);
            }
            return field;
        } catch (Exception e){
            return null;
        }
    }
}
