package com.lylblog.framework.Aspectj.annotation;

import java.lang.annotation.*;

/**
 * @Author: lyl
 * @Date: 2021/6/13 12:41
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    public String moduleName() default "";

    public String functionName() default "";
}
