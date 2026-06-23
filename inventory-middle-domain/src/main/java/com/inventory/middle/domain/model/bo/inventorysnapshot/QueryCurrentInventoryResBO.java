package com.inventory.middle.domain.model.bo.inventorysnapshot;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description:
 * @date 2021/8/31 19:38
 */
@Data
public class QueryCurrentInventoryResBO implements Serializable {

    private String batchNo;

    private String logicalPlantNo;

    private String logicalPlantName;

    private String materialCode;

    private BigDecimal inInventoryNum;

    private BigDecimal currentInventoryNum;

    private Date inInventoryTime;
}