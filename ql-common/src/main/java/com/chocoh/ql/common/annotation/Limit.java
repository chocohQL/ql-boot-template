package com.chocoh.ql.common.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author chocoh
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Limit {
    String key() default "";

    int permits();

    long timeout() default 1;

    TimeUnit timeunit() default TimeUnit.SECONDS;

    String message() default "系统繁忙";

    boolean onIp() default true;
}
