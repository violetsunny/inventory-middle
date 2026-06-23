package com.inventory.middle.client.plan.bom.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author caosheng
 * @title: BomChangeStatusResDTO
 * @projectName scm-plan-management
 * @description: BomChangeStatusResDTO
 * @date 2021/12/9 9:40
 */
@Data
public class BomChangeStatusResDTO implements Serializable {

    private static final long serialVersionUID = 6551842896066082762L;
    
    List<String> materialCodes;
}
