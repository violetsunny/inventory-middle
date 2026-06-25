package com.inventory.middle.application.service.impl;

import com.inventory.middle.application.service.InventoryExternalApplicationService;
import com.inventory.middle.domain.service.external.RemoteProductCenterRestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;

/**
 * 外部产品中心调用 ApplicationService 实现（M15：下沉 interfaces 层的直连依赖）
 */
@Service
@Slf4j
public class InventoryExternalApplicationServiceImpl implements InventoryExternalApplicationService {

    @Resource
    private RemoteProductCenterRestService remoteProductCenterRestService;

    @Override
    public SingleResponse<Object> queryBuildMaterialInfo(String skuCode, String tenantId) {
        return remoteProductCenterRestService.queryBuildMaterialInfo(skuCode, tenantId);
    }

    @Override
    public SingleResponse<Object> fuzzyQueryByName(String skuName, int pageNum, int pageSize, String tenantId) {
        return remoteProductCenterRestService.fuzzyQueryByName(skuName, pageNum, pageSize, tenantId);
    }

    @Override
    public String getUnitNameByCode(String uomCode, String tenantId) {
        return remoteProductCenterRestService.getUnitNameByCode(uomCode, tenantId);
    }
}
