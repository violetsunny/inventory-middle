package com.inventory.middle.infra.persistence.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dongguo.tao
 * @description
 * @date 2022-05-05 17:26:17
 */
@Data
public class ListMaterialCodeParamPO implements Serializable {

    private static final long serialVersionUID = 7440017388582350557L;

    private String tenantId;

    private List<String> materialCodeList;

    private List<String> outMaterialCodeList;

    private Integer deleted = 0;
}
