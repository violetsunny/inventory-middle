package com.inventory.middle.starter.config;

import java.util.concurrent.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.alibaba.ttl.threadpool.TtlExecutors;

/**
 * @author dongguo.tao
 * @date 2021-06-23
 * @description
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {

    private String customThreadNamePrefix;
    private int corePoolSize;
    private int maximumPoolSize;
    private int keepAliveTimeInSeconds;
    private int blockingQueueSize;
    private RejectedExecutionHandler handler;
    private boolean allowCoreThreadTimeOut;
    private TimeUnit unit;
    private ThreadFactory threadFactory;
    private BlockingQueue<Runnable> workQueue;

    @Bean(name = "inventoryCenterExecutor")
    public ExecutorService initExecutorService() {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(this.corePoolSize, this.maximumPoolSize,
            (long)this.keepAliveTimeInSeconds, this.unit, this.workQueue, this.threadFactory, this.handler);
        poolExecutor.allowCoreThreadTimeOut(this.allowCoreThreadTimeOut);
        return TtlExecutors.getTtlExecutorService(poolExecutor);
    }

    public String toString() {
        return "RDFAExecutor: " + this.customThreadNamePrefix + ", " + this.corePoolSize + ", " + this.maximumPoolSize
            + ", " + this.keepAliveTimeInSeconds + ", " + this.blockingQueueSize;
    }
}
