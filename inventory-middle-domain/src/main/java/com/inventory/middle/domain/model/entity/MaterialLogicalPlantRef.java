package com.inventory.middle.domain.model.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kdla.framework.domain.shared.Entity;

/**
 * 物料逻辑仓关联领域对象
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class MaterialLogicalPlantRef implements Entity<MaterialLogicalPlantRef> {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 物料编码
     */
    private String skuCode;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

    /**
     * 创建时间
     */
    private Date createTime;

    @Override
    public boolean sameIdentityAs(MaterialLogicalPlantRef other) {
        return other != null && this.id != null && this.id.equals(other.id);
    }
}
