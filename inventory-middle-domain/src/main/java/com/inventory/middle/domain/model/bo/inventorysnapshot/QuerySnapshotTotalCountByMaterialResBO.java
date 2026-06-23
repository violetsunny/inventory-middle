package com.inventory.middle.domain.model.bo.inventorysnapshot;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author hjs
 * @date 2022/4/11
 */
@Data
public class QuerySnapshotTotalCountByMaterialResBO implements Serializable {

    private String materialCode;

    private BigDecimal count;
}
