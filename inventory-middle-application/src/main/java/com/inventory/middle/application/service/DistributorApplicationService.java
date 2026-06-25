package com.inventory.middle.application.service;

import top.kdla.framework.dto.SingleResponse;

/**
 * 备件经销商 ApplicationService（M15：下沉 DistributorController 的直连外部服务依赖）
 */
public interface DistributorApplicationService {

    SingleResponse<Object> fuzzyQueryByManufacturer(String distributorName, String appKey);

    SingleResponse<Object> fuzzyQueryByDistributor(String distributorName);
}
