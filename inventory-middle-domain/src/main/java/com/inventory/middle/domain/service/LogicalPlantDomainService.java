package com.inventory.middle.domain.service;

import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;
import com.inventory.middle.domain.model.bo.logicalPlant.ListLogicalPlantByIdListRequestBO;
import java.util.List;

/** 逻辑仓库业务服务接口（待 G1 任务完整实现） */
public interface LogicalPlantDomainService {

    LogicalPlantBO getByLogicalPlantNo(String logicalPlantNo);
    List<LogicalPlantBO> listByPlantIdList(ListLogicalPlantByIdListRequestBO requestBO);
}
