package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.MaterialDocMainQueryService;
import com.inventory.middle.application.service.MaterialDocMainApplicationService;
import com.inventory.middle.client.dto.MaterialDocMainDto;
import com.inventory.middle.client.dto.command.MaterialDocMainCommand;
import com.inventory.middle.client.dto.query.MaterialDocMainPageQuery;
import com.inventory.middle.application.convertor.MaterialDocMainDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 物料凭证主表Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:26
 */
@Tag(name = " 物料凭证主表管理")
@RestController
@RequestMapping("/materialdocmain")
@Slf4j
@CatchAndLog
public class MaterialDocMainController {

    @Resource
    private MaterialDocMainApplicationService materialdocmainApplicationService;
    @Resource
    private  MaterialDocMainQueryService materialdocmainQueryService;
    @Resource
    private MaterialDocMainDtoConvertor  materialdocmainDtoConvertor;


    /**
     * 物料凭证主表分页查询
     */
    @Operation(summary="物料凭证主表分页查询")
    @PostMapping("/page")
    public PageResponse<MaterialDocMainDto> page(@RequestBody MaterialDocMainPageQuery materialdocmainPageQuery) {
        return materialdocmainQueryService.queryPage(materialdocmainPageQuery);
    }

    /**
     * 物料凭证主表list查询
     */
    @Operation(summary="物料凭证主表list查询")
    @PostMapping("/list")
            public MultiResponse<MaterialDocMainDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 物料凭证主表信息
     */
    @Operation(summary="物料凭证主表信息")
    @GetMapping("/find/{id}")
    public SingleResponse<MaterialDocMainDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(materialdocmainQueryService.findById(id));
    }

    /**
     * 保存物料凭证主表
     */
    @Operation(summary="保存物料凭证主表")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody MaterialDocMainCommand materialdocmainCommand) {
        return SingleResponse.buildSuccess(materialdocmainApplicationService.add(materialdocmainCommand));

    }

    /**
     * 修改物料凭证主表
     */
    @Operation(summary="修改物料凭证主表")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody MaterialDocMainCommand materialdocmainCommand) {
        return SingleResponse.buildSuccess(materialdocmainApplicationService.update(materialdocmainCommand));
    }

    /**
     * 删除物料凭证主表
     */
    @Operation(summary="删除物料凭证主表")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(materialdocmainApplicationService.deleteBatch(ids));
    }

}