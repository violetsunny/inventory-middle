package com.inventory.middle.domain.model.bo.storageLocation;

import lombok.Data;

import java.util.List;

/**
 * @author kll
 */
@Data
public class ListStorageLocationByIdRequestBO {
    private String tenantId;

    private List<Long> idList;

    private List<String> noList;
}
