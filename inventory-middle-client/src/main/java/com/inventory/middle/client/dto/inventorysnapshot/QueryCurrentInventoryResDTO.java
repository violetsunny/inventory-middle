package com.inventory.middle.client.dto.inventorysnapshot;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 查询当前库存响应DTO
 * @date 2021/8/31 19:38
 */
@Data
public class QueryCurrentInventoryResDTO implements Serializable {

    private String batchNo;

    private String logicalPlantNo;

    private String logicalPlantName;

    private String materialCode;

    private BigDecimal inInventoryNum;

    private BigDecimal currentInventoryNum;

    private Date inInventoryTime;
}