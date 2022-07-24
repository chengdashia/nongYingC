package com.bigdatastudio.nongyingc.common.aspect;

import java.lang.annotation.*;

/**
 * @author 成大事
 * @since 2022/7/24 17:33
 * 定义操作日志注解
 */
@Target(ElementType.METHOD) //注解放置的目标位置即方法级别
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface SysLog {
    /**
     * 操作模块
     */
    String module() default "";

    /**
     * 操作类型
     */
    String type() default "";

    /**
     * 操作说明
     */
    String desc() default "";

}