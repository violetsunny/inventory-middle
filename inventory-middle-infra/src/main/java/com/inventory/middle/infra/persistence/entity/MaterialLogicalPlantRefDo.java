package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-10-13 14:16:44
 */
@Data
public class MaterialLogicalPlantRefDo implements Serializable {

    private static final long serialVersionUID = 6831901365582636118L;

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
     * 逻辑仓id
     */
    private Long logicalPlantId;

    /**
     * 逻辑仓编码
     */
    private String logicalPlantNo;

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
     * 是否删除 0-否；1-是
     */
    private Integer deleted;
}
