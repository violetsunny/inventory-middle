package com.inventory.middle.infra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.kdla.framework.dto.SingleResponse;

/**
 * CRM 经销商外部服务 Feign Client
 * URL 通过 application.yml 配置 remote.crm.url（空串时 Feign 不初始化）
 */
@FeignClient(name = "crmDistributorClient", url = "${remote.crm.url:}")
public interface CrmDistributorFeignClient {

    @GetMapping("/distributor/fuzzy-query")
    SingleResponse<Object> fuzzyQueryByManufacturer(
            @RequestParam String distributorName, @RequestParam String appKey);

    @GetMapping("/distributor/fuzzy-query-by-distributor")
    SingleResponse<Object> fuzzyQueryByDistributor(
            @RequestParam(required = false) String distributorName);
}
