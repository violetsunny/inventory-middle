package com.inventory.middle.interfaces.web;

import com.inventory.middle.application.service.StorageLocationApplicationService;
import com.inventory.middle.application.service.StorageLocationQueryService;
import com.inventory.middle.client.dto.StorageLocationDto;
import com.inventory.middle.client.dto.command.StorageLocationCommand;
import com.inventory.middle.client.dto.query.StorageLocationPageQuery;
import com.inventory.middle.interfaces.support.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import top.kdla.framework.log.catchlog.CatchAndLog;
import com.inventory.middle.domain.model.enums.StorageLocationTypeEnum;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



/**
 * 存储地点 Controller（从 inventory-center-bff 迁移）
 * 路径与 BFF 保持一致：/storageLocation
 */
@Tag(name = "存储地点管理")
@CatchAndLog
@RestController
@RequestMapping("/storageLocation")
@Slf4j
public class StorageLocationController {

    @Resource
    private StorageLocationApplicationService storageLocationApplicationService;
    @Resource
    private StorageLocationQueryService storageLocationQueryService;

    @Operation(summary = "库位列表查询")
    @PostMapping("/list")
    public PageResponse<StorageLocationDto> list(@RequestBody StorageLocationPageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return storageLocationQueryService.queryPage(pageQuery);
    }

    @Operation(summary = "按调整类型查询库位")
    @PostMapping("/listByAdjust")
    public MultiResponse<StorageLocationDto> listByAdjust(@RequestBody StorageLocationPageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return MultiResponse.buildSuccess(storageLocationQueryService.listByQuery(pageQuery));
    }

    @Operation(summary = "创建库位")
    @PostMapping("/create")
    public SingleResponse<Boolean> create(@RequestBody StorageLocationCommand command) {
        command.setTenantId(UserContextHolder.getTenantId());
        command.setCreatorId(UserContextHolder.getUserId());
        return SingleResponse.buildSuccess(storageLocationApplicationService.add(command));
    }

    @Operation(summary = "更新库位")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@RequestBody StorageLocationCommand command) {
        command.setUpdatorId(UserContextHolder.getUserId());
        return SingleResponse.buildSuccess(storageLocationApplicationService.update(command));
    }

    @Operation(summary = "库位详情")
    @PostMapping("/detail")
    public SingleResponse<StorageLocationDto> detail(@RequestBody StorageLocationPageQuery query) {
        if (query.getId() != null) {
            return SingleResponse.buildSuccess(storageLocationQueryService.findById(query.getId()));
        }
        if (query.getStorageLocationNo() != null && !query.getStorageLocationNo().isEmpty()) {
            return SingleResponse.buildSuccess(storageLocationQueryService.findByNo(query.getStorageLocationNo()));
        }
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "库位类型枚举")
    @GetMapping("/queryLocationType")
    public SingleResponse<List<Map<String, Object>>> queryLocationType() {
        List<Map<String, Object>> list = Arrays.stream(StorageLocationTypeEnum.values())
                .map(e -> {
                    Map<String, Object> m = new java.util.LinkedHashMap<>();
                    m.put("code", e.getCode());
                    m.put("mark", e.getMark());
                    m.put("desc", e.getDesc());
                    return m;
                }).collect(Collectors.toList());
        return SingleResponse.buildSuccess(list);
    }
}