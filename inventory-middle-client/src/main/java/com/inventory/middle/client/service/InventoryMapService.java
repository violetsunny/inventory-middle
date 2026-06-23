package com.inventory.middle.client.service;

/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */

import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import com.inventory.middle.client.dto.map.InventoryMapDTO;
import com.inventory.middle.client.dto.map.InventoryMapPageDTO;
import com.inventory.middle.client.dto.map.QueryInventoryMapDTO;

/**
 * map
 *
 * @author kll
 * @version $Id: InventoryMapService, v 0.1 2021/6/29 15:02 Exp $
 */
public interface InventoryMapService {
    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    PageResponse<InventoryMapDTO> mapPageList(InventoryMapPageDTO page);

    /**
     * 获取最新MAP
     */
    SingleResponse<InventoryMapDTO> queryInventoryMap(QueryInventoryMapDTO query);
}
