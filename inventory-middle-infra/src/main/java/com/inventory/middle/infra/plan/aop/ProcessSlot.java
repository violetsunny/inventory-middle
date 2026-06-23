package com.inventory.middle.infra.plan.aop;

/**
 * 处理节点
 * <p>
 * Created on 2021/11/24.
 *
 * @author Danny.Lee
 */
public interface ProcessSlot {

    void entry(Invocation invocation);

    void fireEntry(Invocation invocation);

    void exit(Invocation invocation);

    void fireExit(Invocation invocation);
}
