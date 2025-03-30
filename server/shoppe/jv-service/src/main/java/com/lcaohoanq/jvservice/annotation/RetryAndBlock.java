package com.lcaohoanq.jvservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RetryAndBlock {
    int maxAttempts() default 3;
    long blockDurationSeconds() default 3600; // 1 hour by default
    int maxDailyAttempts() default 6;
}