package com.inventory.middle.application.service.impl;

import com.inventory.middle.application.service.DistributorApplicationService;
import com.inventory.middle.domain.service.external.CrmDistributorRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;

/**
 * 备件经销商 ApplicationService 实现（M15：下沉 DistributorController 的直连外部服务依赖）
 */
@Service
@Slf4j
public class DistributorApplicationServiceImpl implements DistributorApplicationService {

    @Resource
    private CrmDistributorRemoteService crmDistributorRemoteService;

    @Override
    public SingleResponse<Object> fuzzyQueryByManufacturer(String distributorName, String appKey) {
        return crmDistributorRemoteService.fuzzyQueryByManufacturer(distributorName, appKey);
    }

    @Override
    public SingleResponse<Object> fuzzyQueryByDistributor(String distributorName) {
        return crmDistributorRemoteService.fuzzyQueryByDistributor(distributorName);
    }
}
