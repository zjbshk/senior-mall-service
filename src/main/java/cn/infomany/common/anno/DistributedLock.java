package cn.infomany.common.anno;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zjb
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    /**
     * 锁的名称
     * <p>
     * 可使用的 内置参数
     * ${methodName} | 方法名称
     * ${className} |  全类名
     * ${simpleClassName} |  类名
     * ${`paramName`} |  参数名
     * #{method()} | 不带参方法（静态方法调用）
     * #{method(${paramName})} | 带参方法（静态方法调用）
     * <p>
     * 方法调用可以是内置方法，或是全类名的静态方法调用
     * 内置方法用:
     * random() | 随机 0 - 1 浮点数
     * toUpper(str) | 将字符串str转换成大写
     * toLower(str) | 将字符串str转换成小写
     */
    String value();

    /**
     * 租期时间（就是这个锁过多久自动释放,默认是不自动释放的）
     */
    int leaseTime() default -1;

    /**
     * 租期时间单位（默认是秒）
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 是否是异步锁（默认是同步的）
     */
    boolean isAsync() default false;
}