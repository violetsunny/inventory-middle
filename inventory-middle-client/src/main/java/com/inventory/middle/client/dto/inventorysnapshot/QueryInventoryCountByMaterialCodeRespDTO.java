package com.inventory.middle.client.dto.inventorysnapshot;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author hjs
 * @date 2022/4/11
 */
@Data
public class QueryInventoryCountByMaterialCodeRespDTO implements Serializable {

    private String materialCode;

    private BigDecimal count;

}
