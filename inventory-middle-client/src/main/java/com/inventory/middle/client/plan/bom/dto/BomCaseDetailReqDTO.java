package com.inventory.middle.client.plan.bom.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouxinzhong
 * @date 2021/12/11 22:40
 */
@Data
public class BomCaseDetailReqDTO implements Serializable {

    private static final long serialVersionUID = -2660068339505500602L;

    /**
     * BOM单id
     */
    private Long bomCaseId;

    /**
     * 租户id
     */
    private String tenantId;
}
