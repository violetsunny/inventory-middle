package com.inventory.middle.infra.plan.sequence;

/**
 * 序列定义 — 迁移自 com.enn.plan.management.dal.sequence.Sequence
 * <strong>禁止在事务中获取sequence</strong>
 *
 * @author migrated from scm-plan-management
 */
public interface Sequence {

    /**
     * 获取下一序列值
     *
     * @return 序列值
     */
    Long next();

}
