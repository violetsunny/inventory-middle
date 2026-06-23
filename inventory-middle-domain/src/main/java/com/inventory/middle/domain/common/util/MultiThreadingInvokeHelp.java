package com.inventory.middle.domain.common.util;

import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/** 多线程执行工具类（迁移自 inventory-center MultiThreadingInvokeHelp） */
@Slf4j
public class MultiThreadingInvokeHelp {

    public static <T> List<CompletableFuture<T>> invoke(List<Supplier<T>> suppliers, Executor executor) {
        return execute(suppliers, executor);
    }

    public static <T> List<T> invokeGet(List<Supplier<T>> suppliers, Executor executor) throws Exception {
        List<T> results = new ArrayList<>();
        List<CompletableFuture<T>> completableFutures = execute(suppliers, executor);
        for (CompletableFuture<T> cf : completableFutures) {
            results.add(cf.get());
        }
        return results;
    }

    private static <T> List<CompletableFuture<T>> execute(List<Supplier<T>> suppliers, Executor executor) {
        List<CompletableFuture<T>> tasks = suppliers.stream()
                .map(s -> CompletableFuture.supplyAsync(s, executor))
                .collect(Collectors.toList());
        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
        return tasks;
    }

    public static <T> List<CompletableFuture<Void>> execute(List<Consumer<T>> consumers, T t, Executor executor) {
        return consumers.stream()
                .map(c -> CompletableFuture.runAsync(() -> c.accept(t), executor))
                .collect(Collectors.toList());
    }
}

