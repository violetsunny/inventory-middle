package com.inventory.middle.client.plan.bom.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/12/6 15:17
 */
@Data
public class BomTreeRenderReqDTO implements Serializable {

    private static final long serialVersionUID = -5427163578047978713L;
    private String materialCode;

    private String logicalPlantNo;

    private String tenantId;
    /*
     * 节点是否作为根节点展示
     */
    private boolean nodeAsRoot;


    /**
     * 是否返回非bom的叶子节点信息
     */
    private boolean showLeaf;

    /**
     * 只返回启用状态的bom
     */
    private boolean showEnable;
}
