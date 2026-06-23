package com.inventory.middle.client.plan.bom.dto;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/12/6 14:33
 */
@Data
public class HierarchicalBomNodeDTO extends BomNodeDTO{

    private static final long serialVersionUID = 7359835789530064526L;
    /**
     * bom编码
     */
    private String code;

    /**
     * bom单名称
     */
    private String name;


    /**
     *
     */
    List<HierarchicalBomNodeDTO> children;

}
