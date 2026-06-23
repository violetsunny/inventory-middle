package com.inventory.middle.domain.service.external;

import com.inventory.middle.client.dto.map.InventoryMapDTO;
import com.inventory.middle.client.dto.map.QueryInventoryMapDTO;
import top.kdla.framework.dto.SingleResponse;

/** 库存 MAP 远程服务接口（domain 层定义，infra 层实现） */
public interface RemoteInventoryMapService {

    SingleResponse<InventoryMapDTO> queryInventoryMap(QueryInventoryMapDTO query);
}
