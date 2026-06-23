package com.inventory.middle.domain.plan.bom.bo;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 树形结构工具类
 */
public class TreeUtils {

    public static <T extends TreeNode<?>> List<T> generateTrees(List<T> nodes) {
        List<T> roots = new ArrayList<>();
        for (Iterator<T> ite = nodes.iterator(); ite.hasNext(); ) {
            T node = ite.next();
            if (node.root()) {
                roots.add(node);
                ite.remove();
            }
        }
        roots.forEach(r -> setChildren(r, nodes));
        return roots;
    }

    @SuppressWarnings("all")
    public static <T extends TreeNode> void setChildren(T parent, List<T> nodes) {
        List<T> children = new ArrayList<>();
        Object parentId = parent.id();
        for (Iterator<T> ite = nodes.iterator(); ite.hasNext(); ) {
            T node = ite.next();
            if (Objects.equals(node.parentId(), parentId)) {
                children.add(node);
                ite.remove();
            }
        }
        if (children.isEmpty()) {
            return;
        }
        parent.setChildren(children);
        children.forEach(m -> setChildren(m, nodes));
    }

    @SuppressWarnings("all")
    public static <T extends TreeNode> void fillLeaf(T parent, List<T> leafs) {
        List<T> children = parent.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            leafs.add(parent);
            return;
        }
        for (T child : children) {
            fillLeaf(child, leafs);
        }
    }
}
