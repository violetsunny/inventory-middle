package com.inventory.middle.client.dto.inventorysnapshot;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 库存
 * @author peisheng.wang
 * @date 2021-06-16
 */
@Data
public class DetailInventorySnapshotResDTO implements Serializable {


    private String title;

    private String desc;

    private Long locationId;

    private Long logicalPlantId;

    private BigDecimal unrestricted;

    private BigDecimal damaged;

    private BigDecimal inspection;

    private BigDecimal batchPrice;

    private String currency;

    private BigDecimal batchTotalPrice;

    private Date createTime;

    private Date eta;

    private Date dueDate;

    private Integer type;

}
