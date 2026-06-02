package com.rodya.mysql.dbrouter.util;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.core.env.Environment;

public class PropertyUtil {
    /**
     * Boot3/Boot4 专用配置绑定，替代原来多版本反射
     * @param environment 环境对象
     * @param prefix 配置前缀
     * @param targetClass 目标对象类
     */
    public static <T> T handle(final Environment environment, final String prefix, final Class<T> targetClass) {
        Binder binder = new Binder(ConfigurationPropertySources.get(environment));
        BindResult<T> bindResult = binder.bind(prefix, targetClass);
        return bindResult.get();
    }
}
