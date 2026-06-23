package com.inventory.middle.client.plan.config.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xinao
 */
@Data
public class MaterialPlanInstanceBomNodeDTO implements Serializable {
    private static final long serialVersionUID = 4500027252738380005L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料描述
     */
    private String materialDesc;

    /**
     * 状态信息
     */
    private Integer status;

    /**
     * 异常码
     */
    private String errCode;

    /**
     * 异常信息
     */
    private String errMessage;

    /**
     * 子件list集合
     */
    private List<MaterialPlanInstanceBomNodeDTO> list = new ArrayList<>();
}
