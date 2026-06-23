package com.inventory.middle.client.plan.bom.dto;

import com.inventory.middle.client.plan.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: BomCase创建DTO
 * @date 2021/12/6 14:33
 */
@Data
public class BomCaseConfigurationDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = -2987025626727942765L;

    /**
     * bom信息
     */
    private BomCaseDTO bomCase;

    /**
     * 母件
     */
    private BomNodeDTO parent;

    /**
     * 子件集合
     */
    private List<BomNodeDTO> children;
}
