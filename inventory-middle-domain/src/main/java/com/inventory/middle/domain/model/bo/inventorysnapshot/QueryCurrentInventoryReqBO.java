package com.inventory.middle.domain.model.bo.inventorysnapshot;

import lombok.Data;

import java.io.Serializable;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 查询当前库存请求BO
 * @date 2021/8/31 19:38
 */
@Data
public class QueryCurrentInventoryReqBO implements Serializable {

    private String batchNo;

    private String materialCode;

    private String tenantId;

}