package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.MaterialDocItemQueryService;
import com.inventory.middle.application.service.MaterialDocItemApplicationService;
import com.inventory.middle.client.dto.MaterialDocItemDto;
import com.inventory.middle.client.dto.command.MaterialDocItemCommand;
import com.inventory.middle.client.dto.query.MaterialDocItemPageQuery;
import com.inventory.middle.application.convertor.MaterialDocItemDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 物料凭证-itemController
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:26
 */
@Tag(name = " 物料凭证-item管理")
@RestController
@RequestMapping("/materialdocitem")
@Slf4j
@CatchAndLog
public class MaterialDocItemController {

    @Resource
    private MaterialDocItemApplicationService materialdocitemApplicationService;
    @Resource
    private  MaterialDocItemQueryService materialdocitemQueryService;
    @Resource
    private MaterialDocItemDtoConvertor  materialdocitemDtoConvertor;


    /**
     * 物料凭证-item分页查询
     */
    @Operation(summary="物料凭证-item分页查询")
    @PostMapping("/page")
    public PageResponse<MaterialDocItemDto> page(@RequestBody MaterialDocItemPageQuery materialdocitemPageQuery) {
        return materialdocitemQueryService.queryPage(materialdocitemPageQuery);
    }

    /**
     * 物料凭证-itemlist查询
     */
    @Operation(summary="物料凭证-itemlist查询")
    @PostMapping("/list")
            public MultiResponse<MaterialDocItemDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 物料凭证-item信息
     */
    @Operation(summary="物料凭证-item信息")
    @GetMapping("/find/{id}")
    public SingleResponse<MaterialDocItemDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(materialdocitemQueryService.findById(id));
    }

    /**
     * 保存物料凭证-item
     */
    @Operation(summary="保存物料凭证-item")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody MaterialDocItemCommand materialdocitemCommand) {
        return SingleResponse.buildSuccess(materialdocitemApplicationService.add(materialdocitemCommand));

    }

    /**
     * 修改物料凭证-item
     */
    @Operation(summary="修改物料凭证-item")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody MaterialDocItemCommand materialdocitemCommand) {
        return SingleResponse.buildSuccess(materialdocitemApplicationService.update(materialdocitemCommand));
    }

    /**
     * 删除物料凭证-item
     */
    @Operation(summary="删除物料凭证-item")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(materialdocitemApplicationService.deleteBatch(ids));
    }

}