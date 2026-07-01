package com.inventory.middle.application.plan.calculate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 计划域线程池配置
 *
 * @author Danny.Lee
 * @date 2021/10/25
 */
@Configuration
public class PlanExecutorConfig {

    /**
     * 计划计算线程池
     */
    public static final String PLAN_CALCULATE_EXECUTOR = "planCalculateExecutor";

    @Bean(PLAN_CALCULATE_EXECUTOR)
    public ExecutorService planCalculateExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(10000);
        executor.setKeepAliveSeconds(10);
        executor.setThreadNamePrefix(PLAN_CALCULATE_EXECUTOR);
        executor.setDaemon(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor.getThreadPoolExecutor();
    }
}
