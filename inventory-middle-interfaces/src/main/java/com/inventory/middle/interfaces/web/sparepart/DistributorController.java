package com.inventory.middle.interfaces.web.sparepart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;


/**
 * 备件经销商 Controller（从 inventory-center-bff DistributorController 迁移）
 * 依赖外部系统 CrmDistributor（Dubbo），inventory-middle 中尚无对应服务实现。
 * TODO: 待接入 CrmDistributor 外部服务（可通过 OpenFeign 调用）
 */
@Tag(name = "备件经销商管理")
@CatchAndLog
@RestController
@RequestMapping("/distributor")
@Slf4j
public class DistributorController {

    @Operation(summary = "模糊查询经销商（厂商端）")
    @GetMapping("/fuzzy-query")
    public SingleResponse<Object> fuzzyQueryByManufacturer(@RequestParam String distributorName,
                                                           @RequestParam String appKey) {
        // TODO: 待接入 CrmDistributorRemoteService（外部 CRM 服务）
        log.warn("distributor/fuzzy-query: 待接入 CrmDistributorRemoteService");
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "模糊查询经销商（经销商端）")
    @GetMapping("/fuzzy-query-by-distributor")
    public SingleResponse<Object> fuzzyQueryByDistributor(@RequestParam(required = false) String distributorName) {
        // TODO: 待接入 CrmDistributorRemoteService（外部 CRM 服务）
        log.warn("distributor/fuzzy-query-by-distributor: 待接入 CrmDistributorRemoteService");
        return SingleResponse.buildSuccess(null);
    }
}