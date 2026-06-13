package com.rodya;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static org.springframework.beans.BeanUtils.getPropertyDescriptors;

@Slf4j
public class PojoUtil extends BeanUtils {
//    public static <S,T> T copy(S source,Class<T> clazz){
//        return copy(source, clazz, new String[0]);
//    }
//
//    public static <S,T> T copy(S source,Class<T> clazz, String ignoreProperties){
//        return copy(source, clazz,null, ignoreProperties);
//    }
    public static <S,T> T copy(S source, Class<T> clazz, Consumer<T>  customer, String[] ignoreProperties){
        if (Objects.isNull( source)){
            return null;
        }
        Object targetObj = null;

        try{
            targetObj = clazz.newInstance();
        }catch (Exception classInstanceEx){
            log.error(clazz.getName() + " 创建对象异常 ", classInstanceEx);
        }
        if(Objects.isNull(targetObj)){
            return null;
        }

        List<String> ignoreList = ignoreProperties != null ? Arrays.asList((ignoreProperties)) : null;
        Class<?> actualClass = targetObj.getClass();
        PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(actualClass);

        PropertyDescriptor targetPd;
        Method writeMethod;
        for (PropertyDescriptor pd : propertyDescriptors){
            targetPd = pd;
            writeMethod = targetPd.getWriteMethod();
            if(writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))){
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if(sourcePd != null){
                    Method readMethod = sourcePd.getReadMethod();
                    if(readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())){
                        try{
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())){
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(targetObj, value);
                        } catch (Throwable throwable){
                            throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", throwable);
                        }
                    }
                }
            }
        }

        if ((customer != null)){
            customer.accept((T) targetObj);
        }

        return (T) targetObj;

    }
}

