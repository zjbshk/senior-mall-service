package cn.infomany.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁注解类
 *
 * @author zjb
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    /**
     * 锁的名称
     * <p>
     * 可使用的 内置参数
     * #methodName | 方法名称
     * #className |  全类名
     * #simpleClassName |  类名
     * #paramName |  参数名
     * <p>
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