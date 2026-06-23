package com.inventory.middle.starter.config;

import java.util.concurrent.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.alibaba.ttl.threadpool.TtlExecutors;

@EnableAsync
@Configuration
public class ThreadPoolConfig {

    @Value("${thread.pool.coreSize:10}")
    private int corePoolSize;

    @Value("${thread.pool.maxSize:50}")
    private int maximumPoolSize;

    @Value("${thread.pool.keepAliveSeconds:60}")
    private int keepAliveTimeInSeconds;

    @Value("${thread.pool.queueSize:1000}")
    private int blockingQueueSize;

    @Bean(name = "inventoryCenterExecutor")
    public ExecutorService initExecutorService() {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(blockingQueueSize);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize,
                keepAliveTimeInSeconds, TimeUnit.SECONDS,
                workQueue, threadFactory, handler);
        poolExecutor.allowCoreThreadTimeOut(true);
        return TtlExecutors.getTtlExecutorService(poolExecutor);
    }
}
