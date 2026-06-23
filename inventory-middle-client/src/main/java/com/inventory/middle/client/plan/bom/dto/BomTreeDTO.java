package com.inventory.middle.client.plan.bom.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/12/6 15:11
 */
@Data
public class BomTreeDTO implements Serializable {

    private static final long serialVersionUID = -3610049844915563387L;
    private HierarchicalBomNodeDTO root;
}
