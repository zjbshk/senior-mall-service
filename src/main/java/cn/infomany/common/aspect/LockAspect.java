package cn.infomany.common.aspect;

import cn.infomany.common.anno.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


        RLock lock = redissonClient.getLock(value);
        try {
            // 获取锁
            log.debug("获取锁");
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
            log.debug("释放锁");
            if (distributedLock.isAsync()) {
                lock.unlockAsync();
            } else {
                lock.unlock();
            }
        }
        return null;
    }
}