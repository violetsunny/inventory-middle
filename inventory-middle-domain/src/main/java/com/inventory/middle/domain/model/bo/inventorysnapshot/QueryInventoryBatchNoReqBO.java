package com.inventory.middle.domain.model.bo.inventorysnapshot;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description:
 * @date 2021/8/30 9:04
 */
@Data
public class QueryInventoryBatchNoReqBO implements Serializable {

    private List<String> batchNoList;

    private Integer pageSize;

    private Integer pageNum;

    private String tenantId;
}