package com.inventory.middle.domain.model.bo.inventorysnapshot;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hjs
 * @date 2022/4/11
 */
@Data
public class QuerySnapshotTotalCountByMaterialReqBO {

    private String tenantId;

    private List<String> materialCodeList;

}
