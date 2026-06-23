package com.inventory.middle.client.plan.bom.dto;

import lombok.Data;

/**
 * @author zhouxinzhong
 * @date 2021/12/17 16:49
 */
@Data
public class BomCaseChildrenQueryReqDTO extends BomCaseDetailReqDTO {

    private static final long serialVersionUID = 3237316162414433353L;

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;
}
