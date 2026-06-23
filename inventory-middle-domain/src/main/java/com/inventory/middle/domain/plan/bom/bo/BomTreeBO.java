package com.inventory.middle.domain.plan.bom.bo;

import lombok.Data;

import java.util.function.BiConsumer;

/**
 * BOM树BO
 */
@Data
public class BomTreeBO {

    private HierarchicalBomNodeBO root;

    public void visit(BiConsumer<HierarchicalBomNodeBO, HierarchicalBomNodeBO> consumer) {
        root.visit(null, consumer);
    }
}
