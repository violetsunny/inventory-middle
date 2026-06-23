package com.inventory.middle.domain.model.bo.inventorysnapshot;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author dongguo
 */
@Data
public class ListQueryInventorySnapshotReqBO implements Serializable {

    private static final long serialVersionUID = 7794807279340532024L;

    private List<String> logicalPlantNoList;

    private List<String> materialCodeList;

    private String tenantId;
}
