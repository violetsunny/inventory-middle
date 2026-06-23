package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dongguo.tao
 * @description
 * @date 2022-05-05 16:54:35
 */
@Data
public class InventoryMaterialDo implements Serializable {

    private static final long serialVersionUID = -7868776859947648333L;

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
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 外部物料编码
     */
    private String outMaterialCode;

    /**
     * 单位id
     */
    private Long unitId;

    /**
     * 创建人id
     */
    private String creatorId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private String updatorId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除：0-否；1-是
     */
    private Integer deleted;
}
