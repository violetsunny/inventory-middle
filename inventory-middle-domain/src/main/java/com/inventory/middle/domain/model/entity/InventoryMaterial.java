package com.inventory.middle.domain.model.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.kdla.framework.domain.shared.Entity;

/**
 * 库存物料领域对象
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class InventoryMaterial implements Entity<InventoryMaterial> {

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
     * 物料名称
     */
    private String skuName;

    /**
     * 物料编码（标准字段名，对应DB material_code）
     */
    private String materialCode;

    /**
     * 物料名称（标准字段名，对应DB material_name）
     */
    private String materialName;

    /**
     * 外部物料编码
     */
    private String outMaterialCode;

    /**
     * 单位ID
     */
    private Long unitId;

    /**
     * 单位编码
     */
    private String unitCode;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 类目编码
     */
    private String categoryCode;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @Override
    public boolean sameIdentityAs(InventoryMaterial other) {
        return other != null && this.id != null && this.id.equals(other.id);
    }
}
