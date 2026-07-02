package com.inventory.middle.domain.service.impl;

import com.inventory.middle.domain.model.bo.logicalPlant.ListLogicalPlantByIdListRequestBO;
import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;
import com.inventory.middle.domain.model.entity.LogicalPlant;
import com.inventory.middle.domain.model.types.LogicalPlantId;
import com.inventory.middle.domain.repository.LogicalPlantRepository;
import com.inventory.middle.domain.service.LogicalPlantDomainService;
import com.inventory.middle.domain.service.convertor.LogicalPlantConvertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 逻辑仓库服务实现
 * 迁移自: com.enn.inventory.center.biz.service.impl.LogicalPlantBizServiceImpl
 */
@Slf4j
@Service
public class LogicalPlantCoreService implements LogicalPlantDomainService {

    @Resource
    private LogicalPlantRepository logicalPlantRepository;

    @Override
    public LogicalPlantBO getByLogicalPlantNo(String logicalPlantNo) {
        LogicalPlant entity = logicalPlantRepository.findByLogicalPlantNo(logicalPlantNo);
        return toBO(entity);
    }

    @Override
    public List<LogicalPlantBO> listByPlantIdList(ListLogicalPlantByIdListRequestBO requestBO) {
        if (requestBO == null) {
            return Collections.emptyList();
        }
        List<LogicalPlant> list = logicalPlantRepository.listByIdsOrNos(
                requestBO.getTenantId(), requestBO.getIdList(), requestBO.getNoList());
        return list.stream().map(this::toBO).collect(Collectors.toList());
    }

    public List<LogicalPlantBO> listByWarehouseNo(String warehouseNo) {
        List<LogicalPlant> list = logicalPlantRepository.listByWarehouseNo(warehouseNo);
        return list.stream().map(this::toBO).collect(Collectors.toList());
    }

    private LogicalPlantBO toBO(LogicalPlant entity) {
        if (entity == null) {
            return null;
        }
        LogicalPlantBO bo = LogicalPlantConvertor.INSTANCE.toBO(entity);
        if (entity.getId() != null) {
            bo.setId(entity.getId().get());
        }
        return bo;
    }
}
