package com.inventory.middle.interfaces.web;

import com.inventory.middle.application.service.LogicalPlantApplicationService;
import com.inventory.middle.application.service.LogicalPlantQueryService;
import com.inventory.middle.client.dto.LogicalPlantDto;
import com.inventory.middle.client.dto.command.LogicalPlantCommand;
import com.inventory.middle.client.dto.query.LogicalPlantPageQuery;
import com.inventory.middle.interfaces.support.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.util.List;
import top.kdla.framework.log.catchlog.CatchAndLog;
import com.inventory.middle.domain.model.enums.LogicalPlantTypeEnum;
import com.inventory.middle.client.dto.logicalPlant.ListLogicalPlantByIdListRequest;
import lombok.Data;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;



/**
 * 逻辑仓库 Controller（从 inventory-center-bff 迁移）
 * 路径与 BFF 保持一致：/logicalPlant
 */
@Tag(name = "逻辑仓库管理")
@CatchAndLog
@RestController
@RequestMapping("/logicalPlant")
@Slf4j
public class LogicalPlantController {

    @Resource
    private LogicalPlantApplicationService logicalPlantApplicationService;
    @Resource
    private LogicalPlantQueryService logicalPlantQueryService;

    @Operation(summary = "逻辑仓库类型枚举")
    @GetMapping("/types")
    public SingleResponse<List<Map<String, Object>>> types() {
        List<Map<String, Object>> list = Arrays.stream(LogicalPlantTypeEnum.values())
                .map(e -> {
                    Map<String, Object> m = new java.util.LinkedHashMap<>();
                    m.put("code", e.getCode());
                    m.put("mark", e.getMark());
                    m.put("desc", e.getDesc());
                    return m;
                }).collect(Collectors.toList());
        return SingleResponse.buildSuccess(list);
    }

    @Operation(summary = "逻辑仓库分页查询")
    @PostMapping("/page")
    public PageResponse<LogicalPlantDto> page(@RequestBody LogicalPlantPageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return logicalPlantQueryService.queryPage(pageQuery);
    }

    @Operation(summary = "逻辑仓库列表查询")
    @PostMapping("/list")
    public MultiResponse<LogicalPlantDto> list(@RequestBody LogicalPlantPageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return MultiResponse.buildSuccess(logicalPlantQueryService.list(pageQuery));
    }

    @Operation(summary = "逻辑仓库列表查询（body）")
    @PostMapping("/list/query")
    public MultiResponse<LogicalPlantDto> queryList(@RequestBody LogicalPlantPageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return MultiResponse.buildSuccess(logicalPlantQueryService.list(pageQuery));
    }

    @Operation(summary = "创建逻辑仓库")
    @PostMapping("/create")
    public SingleResponse<Boolean> create(@RequestBody LogicalPlantCommand command) {
        command.setTenantId(UserContextHolder.getTenantId());
        command.setCreatorId(UserContextHolder.getUserId());
        return SingleResponse.buildSuccess(logicalPlantApplicationService.add(command));
    }

    @Operation(summary = "更新逻辑仓库")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@RequestBody LogicalPlantCommand command) {
        command.setUpdatorId(UserContextHolder.getUserId());
        return SingleResponse.buildSuccess(logicalPlantApplicationService.update(command));
    }

    @Operation(summary = "逻辑仓库详情")
    @PostMapping("/detail")
    public SingleResponse<LogicalPlantDto> detail(@RequestBody LogicalPlantPageQuery query) {
        if (query.getId() != null) {
            return SingleResponse.buildSuccess(logicalPlantQueryService.findById(query.getId()));
        }
        if (query.getLogicalPlantNo() != null && !query.getLogicalPlantNo().isEmpty()) {
            return SingleResponse.buildSuccess(logicalPlantQueryService.findByNo(query.getLogicalPlantNo()));
        }
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "按ID列表或编码列表批量查询逻辑仓")
    @PostMapping("/listByIdList")
    public MultiResponse<LogicalPlantDto> listByIdList(@RequestBody ListLogicalPlantByIdListRequest request) {
        request.setTenantId(UserContextHolder.getTenantId());
        return MultiResponse.buildSuccess(logicalPlantQueryService.listByIdList(request));
    }
}