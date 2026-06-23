package com.inventory.middle.infra.plan.aop;

import com.inventory.middle.infra.plan.aop.slot.AbstractProcessSlot;

/**
 * 流程处理链
 * Created on 2021/11/24.
 *
 * @author Danny.Lee
 */
public class SlotProcessChain {

    private AbstractProcessSlot head;
    private AbstractProcessSlot tail;

    public void entry(Invocation invocation) {
        head.entry(invocation);
    }

    public void exit(Invocation invocation) {
        tail.exit(invocation);
    }

    public void addLast(AbstractProcessSlot last) {
        if (null != tail) {
            tail.next(last);
            last.previous(tail);
        }
        tail = last;
        if (null == head) {
            head = last;
        }
    }
}
