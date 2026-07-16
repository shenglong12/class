package com.kuafu.common.sensitive;


import java.lang.annotation.*;

@Target({ElementType.METHOD}) // 作用在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时可用
@Documented
public @interface SensitiveFilter {
}
