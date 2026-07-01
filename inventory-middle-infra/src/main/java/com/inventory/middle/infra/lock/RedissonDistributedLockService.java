package com.inventory.middle.infra.lock;

import com.inventory.middle.domain.service.lock.DistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Redisson 分布式锁实现
 */
@Service
@Slf4j
public class RedissonDistributedLockService implements DistributedLockService {

    @Resource
    private RedissonClient redissonClient;

    @Override
    public <T> T executeWithLock(long waitTime, TimeUnit unit, String lockKey, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(lockKey);
        boolean locked = false;
        try {
            locked = lock.tryLock(waitTime, unit);
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

