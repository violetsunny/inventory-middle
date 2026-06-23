package com.inventory.middle.client.plan.demand.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouxinzhong
 * @date 2021/10/30 23:35
 */
@Data
public class MaterialResultDTO implements Serializable {

    private static final long serialVersionUID = -3335250760461970388L;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料描述
     */
    private String materialDesc;
}
