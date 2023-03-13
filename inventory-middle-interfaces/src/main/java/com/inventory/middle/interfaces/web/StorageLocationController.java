package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.StorageLocationQueryService;
import com.inventory.middle.application.service.StorageLocationApplicationService;
import com.inventory.middle.client.dto.StorageLocationDto;
import com.inventory.middle.client.dto.command.StorageLocationCommand;
import com.inventory.middle.client.dto.query.StorageLocationPageQuery;
import com.inventory.middle.application.convertor.StorageLocationDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 存储地点表Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:27
 */
@Tag(name = " 存储地点表管理")
@RestController
@RequestMapping("/storagelocation")
@Slf4j
@CatchAndLog
public class StorageLocationController {

    @Resource
    private StorageLocationApplicationService storagelocationApplicationService;
    @Resource
    private  StorageLocationQueryService storagelocationQueryService;
    @Resource
    private StorageLocationDtoConvertor  storagelocationDtoConvertor;


    /**
     * 存储地点表分页查询
     */
    @Operation(summary="存储地点表分页查询")
    @PostMapping("/page")
    public PageResponse<StorageLocationDto> page(@RequestBody StorageLocationPageQuery storagelocationPageQuery) {
        return storagelocationQueryService.queryPage(storagelocationPageQuery);
    }

    /**
     * 存储地点表list查询
     */
    @Operation(summary="存储地点表list查询")
    @PostMapping("/list")
            public MultiResponse<StorageLocationDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 存储地点表信息
     */
    @Operation(summary="存储地点表信息")
    @GetMapping("/find/{id}")
    public SingleResponse<StorageLocationDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(storagelocationQueryService.findById(id));
    }

    /**
     * 保存存储地点表
     */
    @Operation(summary="保存存储地点表")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody StorageLocationCommand storagelocationCommand) {
        return SingleResponse.buildSuccess(storagelocationApplicationService.add(storagelocationCommand));

    }

    /**
     * 修改存储地点表
     */
    @Operation(summary="修改存储地点表")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody StorageLocationCommand storagelocationCommand) {
        return SingleResponse.buildSuccess(storagelocationApplicationService.update(storagelocationCommand));
    }

    /**
     * 删除存储地点表
     */
    @Operation(summary="删除存储地点表")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(storagelocationApplicationService.deleteBatch(ids));
    }

}