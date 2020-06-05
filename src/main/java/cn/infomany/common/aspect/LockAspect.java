package cn.infomany.common.aspect;

import cn.infomany.common.anno.DistributedLock;
import cn.infomany.util.SpelUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 分布式锁截面
 *
 * @author zjb
 */
@Aspect
@Component
@Slf4j
public class LockAspect {

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut(value = "@annotation(cn.infomany.common.anno.DistributedLock)")
    public void annotationPointCut() {

    }

    @Around("annotationPointCut() && @annotation(distributedLock)")
    public Object lockAround(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
        String value = distributedLock.value();
        // 得到被代理的方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 得到被切面修饰的方法的参数列表
        Object[] args = joinPoint.getArgs();

        // 获取表达式结果值
        String name = SpelUtil.parseKey(value, method, args);
        log.info("解析分布式锁name：{}", name);

        RLock lock = redissonClient.getLock(name);
        try {
            // 获取锁
            log.debug("获取锁[{}]", name);
            if (distributedLock.isAsync()) {
                lock.lockAsync(distributedLock.leaseTime(), distributedLock.timeUnit());
            } else {
                lock.lock(distributedLock.leaseTime(), distributedLock.timeUnit());
            }

            // 执行相应的方法
            return joinPoint.proceed();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            // 释放锁
            log.debug("释放锁[{}]", name);
            if (distributedLock.isAsync()) {
                lock.unlockAsync();
            } else {
                lock.unlock();
            }
        }
        return null;
    }
}