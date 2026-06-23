package com.inventory.middle.application.plan.plan.calculate.bo;

import com.inventory.middle.domain.plan.common.bo.MaterialBO;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 计划实例物料清单关系<br/>
 * <p>
 * 用于记录计划实例运行过程中，物料对应的母件/子件物料信息。
 * 由于Bom树存在多层结构，当作为上层的节点都作为独立需求分别运行时，会存在多个根级物料
 * </p>
 *
 * @author Danny.Lee
 */
@ToString
public class PlanInstanceBomRelationBO{

    private final Map<MaterialBO, Set<MaterialBO>> rootMaterialCodes = Maps.newConcurrentMap();
    private final Map<MaterialBO, Set<MaterialBO>> subordinateMaterialCodes = Maps.newConcurrentMap();

    public PlanInstanceBomRelationBO addRoot(MaterialBO material, MaterialBO rootMaterialCode) {
        Set<MaterialBO> rootMaterialCodeSet;
        if (!rootMaterialCodes.containsKey(material)) {
            rootMaterialCodes.putIfAbsent(material, Sets.newConcurrentHashSet());
        }
        rootMaterialCodeSet = rootMaterialCodes.get(material);
        rootMaterialCodeSet.add(rootMaterialCode);
        return this;
    }

    public PlanInstanceBomRelationBO addSubordinate(MaterialBO material, MaterialBO subordinateMaterial) {
        Set<MaterialBO> subordinateMaterialCodeSet;
        if (!subordinateMaterialCodes.containsKey(material)) {
            subordinateMaterialCodes.putIfAbsent(material, Sets.newConcurrentHashSet());
        }
        subordinateMaterialCodeSet = subordinateMaterialCodes.get(material);
        subordinateMaterialCodeSet.add(subordinateMaterial);
        return this;
    }

    public Set<MaterialBO> rootMaterialCodes(MaterialBO material) {
        if (CollectionUtils.isEmpty(rootMaterialCodes.get(material))) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(rootMaterialCodes.get(material));
    }

    public Set<MaterialBO> subordinateMaterialCodes(MaterialBO material) {
        if (CollectionUtils.isEmpty(subordinateMaterialCodes.get(material))) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(subordinateMaterialCodes.get(material));
    }
}
