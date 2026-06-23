package com.inventory.middle.client.dto.sparepart;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PageSparePartResDTO implements Serializable {

    private String batchNo;

    private String originalNo;

    private String batchTime;

    private String batchProduceDate;

    private BigDecimal batchPrice;

    private String logicalPlantName;


}
