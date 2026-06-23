package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-15 15:59:24
 */
@Data
public class CodeApplyOrderDetailDo extends BasePO implements Serializable {


    private static final long serialVersionUID = 6933660640660464197L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 码申请单据id
     */
    private Long applyOrderId;

    /**
     * 流转码
     */
    private String code;

    /**
     * 内部码
     */
    private String innerCode;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

}
