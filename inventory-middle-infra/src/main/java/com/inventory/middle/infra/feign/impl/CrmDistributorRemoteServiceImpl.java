package com.inventory.middle.infra.feign.impl;

import com.inventory.middle.domain.service.external.CrmDistributorRemoteService;
import com.inventory.middle.infra.feign.CrmDistributorFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.SingleResponse;

/**
 * CRM 经销商远程服务实现（委托 Feign Client，URL 未配置时降级返回错误响应）
 */
@Service
@Slf4j
public class CrmDistributorRemoteServiceImpl implements CrmDistributorRemoteService {

    @Autowired(required = false)
    private CrmDistributorFeignClient feignClient;

    @Override
    public SingleResponse<Object> fuzzyQueryByManufacturer(String distributorName, String appKey) {
        if (feignClient == null) return SingleResponse.buildFailure("NOT_CONFIGURED", "CRM 服务未配置");
        return feignClient.fuzzyQueryByManufacturer(distributorName, appKey);
    }

    @Override
    public SingleResponse<Object> fuzzyQueryByDistributor(String distributorName) {
        if (feignClient == null) return SingleResponse.buildFailure("NOT_CONFIGURED", "CRM 服务未配置");
        return feignClient.fuzzyQueryByDistributor(distributorName);
    }
}
