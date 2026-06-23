package com.inventory.middle.client.plan.demand.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/10/30 23:44
 */
@Data
public class MaterialResDTO implements Serializable {

    private static final long serialVersionUID = -2372841994990085974L;

    /**
     * 物料列表
     */
    List<MaterialResultDTO> materialList;
}
