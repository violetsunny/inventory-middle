package com.inventory.middle.domain.service.lock;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 分布式锁服务接口（Domain层只定义接口，具体实现在 Infra 层）
 */
public interface DistributedLockService {
    <T> T executeWithLock(long waitTime, TimeUnit unit, String lockKey, Supplier<T> supplier);
}
