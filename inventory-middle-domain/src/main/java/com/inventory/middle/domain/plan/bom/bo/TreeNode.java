package com.inventory.middle.domain.plan.bom.bo;

import java.util.List;

/**
 * 树节点接口
 */
public interface TreeNode<T> {

    T id();

    T parentId();

    boolean root();

    void setChildren(List children);

    List getChildren();
}
