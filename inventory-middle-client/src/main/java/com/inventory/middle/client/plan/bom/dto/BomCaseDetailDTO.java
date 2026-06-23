package com.inventory.middle.client.plan.bom.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/12/11 22:41
 * @description bom单及母件信息
 */
@Data
public class BomCaseDetailDTO implements Serializable {

    private static final long serialVersionUID = -7711896894583564770L;

    /**
     * bom单
     */
    private BomCaseDTO bomCase;

    /**
     * 母件
     */
    private BomNodeDTO parent;
}
