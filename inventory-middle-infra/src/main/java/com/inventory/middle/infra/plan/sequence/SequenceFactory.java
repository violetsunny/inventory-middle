package com.inventory.middle.infra.plan.sequence;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 序列工厂 — 迁移自 com.enn.plan.management.dal.sequence.SequenceFactory
 * <p>
 * 简化实现：使用内存自增序列，无需数据库序列表
 * </p>
 *
 * @author migrated from scm-plan-management
 */
@Component
public class SequenceFactory {

    private static final ConcurrentMap<String, Sequence> SEQUENCES = Maps.newConcurrentMap();

    /**
     * 获取序列
     *
     * @param name 序列名称
     * @return 获取对应序列
     */
    public Sequence getSequence(String name) {
        return SEQUENCES.computeIfAbsent(name, n -> {
            AtomicLong counter = new AtomicLong(1L);
            return counter::getAndIncrement;
        });
    }

}
