package com.inventory.middle.domain.plan.bom.bo;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * 层次化BOM节点BO（用于树形结构）
 */
@Data
@ToString(callSuper = true)
public class HierarchicalBomNodeBO extends BomNodeBO implements TreeNode<Long> {

    private static final long serialVersionUID = 8436838338271546944L;

    private String code;
    private String name;
    private Long pid;
    private Long treeAmount;
    List<HierarchicalBomNodeBO> children;

    public void visit(HierarchicalBomNodeBO parent, BiConsumer<HierarchicalBomNodeBO, HierarchicalBomNodeBO> consumer) {
        consumer.accept(parent, this);
        if (CollectionUtils.isNotEmpty(getChildren())) {
            getChildren().forEach(node -> node.visit(this, consumer));
        }
    }

    @Override
    public Long id() {
        return this.getId();
    }

    @Override
    public Long parentId() {
        return this.getPid();
    }

    @Override
    public boolean root() {
        return Objects.equals(this.pid, 0L) || Objects.isNull(this.pid);
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
    }
}
