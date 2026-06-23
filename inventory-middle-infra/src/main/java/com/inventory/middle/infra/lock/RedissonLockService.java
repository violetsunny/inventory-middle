package com.inventory.middle.infra.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 分布式锁服务，平替 RDFA DisLockService
 */
@Slf4j
@Service
public class RedissonLockService {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 带锁执行业务逻辑
     *
     * @param waitSeconds 等待锁超时秒数
     * @param unit        时间单位
     * @param lockKey     锁键
     * @param supplier    业务逻辑
     */
    public <T> T biz(long waitSeconds, TimeUnit unit, String lockKey, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(lockKey);
        boolean locked = false;
        try {
            locked = lock.tryLock(waitSeconds, unit);
            if (!locked) {
                throw new RuntimeException("获取分布式锁失败: " + lockKey);
            }
            return supplier.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("获取分布式锁被中断: " + lockKey, e);
        } finally {
            if (locked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
