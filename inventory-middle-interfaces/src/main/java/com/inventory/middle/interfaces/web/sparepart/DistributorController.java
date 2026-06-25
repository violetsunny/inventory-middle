package com.inventory.middle.interfaces.web.sparepart;

import com.inventory.middle.application.service.DistributorApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;


/**
 * 备件经销商 Controller（从 inventory-center-bff DistributorController 迁移）
 * 依赖外部系统 CrmDistributorRemoteService（OpenFeign），URL 未配置时降级返回错误响应。
 */
@Tag(name = "备件经销商管理")
@CatchAndLog
@RestController
@RequestMapping("/distributor")
@Slf4j
public class DistributorController {

    @Resource
    private DistributorApplicationService distributorApplicationService;

    @Operation(summary = "模糊查询经销商（厂商端）")
    @GetMapping("/fuzzy-query")
    public SingleResponse<Object> fuzzyQueryByManufacturer(@RequestParam String distributorName,
                                                           @RequestParam String appKey) {
        return distributorApplicationService.fuzzyQueryByManufacturer(distributorName, appKey);
    }

    @Operation(summary = "模糊查询经销商（经销商端）")
    @GetMapping("/fuzzy-query-by-distributor")
    public SingleResponse<Object> fuzzyQueryByDistributor(@RequestParam(required = false) String distributorName) {
        return distributorApplicationService.fuzzyQueryByDistributor(distributorName);
    }
}
