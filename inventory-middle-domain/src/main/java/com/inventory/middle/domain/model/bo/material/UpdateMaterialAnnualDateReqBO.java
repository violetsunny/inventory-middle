package com.inventory.middle.domain.model.bo.material;

import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description:
 * @date 2021/9/3 16:35
 */
@Data
public class UpdateMaterialAnnualDateReqBO implements Serializable {

    private String materialCode;

    private String batchNo;

    private String token;

    private String tenantId;

    private String materialDocNo;

}