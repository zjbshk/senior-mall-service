package cn.infomany.common.anno;


import cn.infomany.common.constant.UniquelyIdentifiesEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 频率限制注解类
 *
 * @author zjb
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FrequencyLimit {

    /**
     * 设置名称
     */
    int value();

    /**
     * 指定一段时间
     * 与字段timeUnit联合
     */
    int epoch();


    /**
     * epoch时间的单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 在这段时间中最大的访问次数
     * 超过就进入限制状态
     */
    int maxTimes();

    /**
     * 当访问到达最大次数时，限制多长时间
     */
    int limitedTime();

    /**
     * 限制时间的单位
     */
    TimeUnit limitedTimeUnit() default TimeUnit.SECONDS;

    /**
     * 在这段时间中能访问多少次，超过要等epoch花费完，才能进入下一个
     */
    int times();


    /**
     * 标记通过何种方式标记唯一用户
     */
    UniquelyIdentifiesEnum uniquely() default UniquelyIdentifiesEnum.IP_USER;

}
