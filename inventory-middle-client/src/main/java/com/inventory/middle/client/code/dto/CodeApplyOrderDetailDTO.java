package com.inventory.middle.client.code.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-12-15 15:59:24
 */
@Data
public class CodeApplyOrderDetailDTO implements Serializable {

    private static final long serialVersionUID = -7410032549780553668L;
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

}
