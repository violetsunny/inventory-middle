package com.inventory.middle.infra.plan.aop.slot;

import com.inventory.middle.infra.plan.aop.Invocation;
import com.inventory.middle.infra.plan.aop.ProcessSlot;

import java.util.Optional;

/**
 * 处理节点基类
 * <p>
 * Created on 2022/1/26.
 *
 * @author Danny.Lee
 */
public abstract class AbstractProcessSlot implements ProcessSlot {

    private ProcessSlot next;
    private ProcessSlot previous;

    @Override
    public void entry(Invocation invocation) {
        this.fireEntry(invocation);
    }

    @Override
    public void exit(Invocation invocation) {
        this.fireExit(invocation);
    }

    @Override
    public void fireEntry(Invocation invocation) {
        Optional.ofNullable(next).ifPresent(node -> node.entry(invocation));
    }

    @Override
    public void fireExit(Invocation invocation) {
        Optional.ofNullable(previous).ifPresent(node -> node.exit(invocation));
    }

    public void next(ProcessSlot next) {
        this.next = next;
    }

    public void previous(ProcessSlot previous) {
        this.previous = previous;
    }
}
