package com.inventory.middle.domain.model.bo.sparepart;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PageSparePartResBO implements Serializable {

    private String batchNo;

    private String originalNo;

    private String batchTime;

    private String batchProduceDate;

    private BigDecimal batchPrice;

    private String logicalPlantName;

}
