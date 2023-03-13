package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.LogicalPlantQueryService;
import com.inventory.middle.application.service.LogicalPlantApplicationService;
import com.inventory.middle.client.dto.LogicalPlantDto;
import com.inventory.middle.client.dto.command.LogicalPlantCommand;
import com.inventory.middle.client.dto.query.LogicalPlantPageQuery;
import com.inventory.middle.application.convertor.LogicalPlantDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 逻辑仓库表Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Tag(name = " 逻辑仓库表管理")
@RestController
@RequestMapping("/logicalplant")
@Slf4j
@CatchAndLog
public class LogicalPlantController {

    @Resource
    private LogicalPlantApplicationService logicalplantApplicationService;
    @Resource
    private  LogicalPlantQueryService logicalplantQueryService;
    @Resource
    private LogicalPlantDtoConvertor  logicalplantDtoConvertor;


    /**
     * 逻辑仓库表分页查询
     */
    @Operation(summary="逻辑仓库表分页查询")
    @PostMapping("/page")
    public PageResponse<LogicalPlantDto> page(@RequestBody LogicalPlantPageQuery logicalplantPageQuery) {
        return logicalplantQueryService.queryPage(logicalplantPageQuery);
    }

    /**
     * 逻辑仓库表list查询
     */
    @Operation(summary="逻辑仓库表list查询")
    @PostMapping("/list")
            public MultiResponse<LogicalPlantDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 逻辑仓库表信息
     */
    @Operation(summary="逻辑仓库表信息")
    @GetMapping("/find/{id}")
    public SingleResponse<LogicalPlantDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(logicalplantQueryService.findById(id));
    }

    /**
     * 保存逻辑仓库表
     */
    @Operation(summary="保存逻辑仓库表")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody LogicalPlantCommand logicalplantCommand) {
        return SingleResponse.buildSuccess(logicalplantApplicationService.add(logicalplantCommand));

    }

    /**
     * 修改逻辑仓库表
     */
    @Operation(summary="修改逻辑仓库表")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody LogicalPlantCommand logicalplantCommand) {
        return SingleResponse.buildSuccess(logicalplantApplicationService.update(logicalplantCommand));
    }

    /**
     * 删除逻辑仓库表
     */
    @Operation(summary="删除逻辑仓库表")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(logicalplantApplicationService.deleteBatch(ids));
    }

}