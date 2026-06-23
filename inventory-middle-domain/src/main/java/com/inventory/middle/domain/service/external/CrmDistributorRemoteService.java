package com.inventory.middle.domain.service.external;

import top.kdla.framework.dto.SingleResponse;

/** CRM 经销商远程服务（domain 层接口，infra OpenFeign 实现） */
public interface CrmDistributorRemoteService {

    /** 厂商端模糊查询经销商 */
    SingleResponse<Object> fuzzyQueryByManufacturer(String distributorName, String appKey);

    /** 经销商端模糊查询 */
    SingleResponse<Object> fuzzyQueryByDistributor(String distributorName);
}
