package com.rodya.dbrouter.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface DBRouterStrategy {
    boolean value() default false;
}
