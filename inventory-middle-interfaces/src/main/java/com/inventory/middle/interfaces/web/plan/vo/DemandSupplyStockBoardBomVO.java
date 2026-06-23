package com.inventory.middle.interfaces.web.plan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author zhouxinzhong
 * @date 2021/12/15 15:24
 */
@Data
public class DemandSupplyStockBoardBomVO implements Serializable {

    private static final long serialVersionUID = 441733831057627551L;

    @Schema(description = "物料编码")
    private String materialCode;

    @Schema(description = "物料名称")
    private String materialDesc;

    @Schema(description = "子件列表")
    private ArrayList<DemandSupplyStockBoardBomVO> children;
}
