package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.MdocSubMapQueryService;
import com.inventory.middle.application.service.MdocSubMapApplicationService;
import com.inventory.middle.client.dto.MdocSubMapDto;
import com.inventory.middle.client.dto.command.MdocSubMapCommand;
import com.inventory.middle.client.dto.query.MdocSubMapPageQuery;
import com.inventory.middle.application.convertor.MdocSubMapDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 物料凭证-标签-移动平均价Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:25
 */
@Tag(name = " 物料凭证-标签-移动平均价管理")
@RestController
@RequestMapping("/mdocsubmap")
@Slf4j
@CatchAndLog
public class MdocSubMapController {

    @Resource
    private MdocSubMapApplicationService mdocsubmapApplicationService;
    @Resource
    private  MdocSubMapQueryService mdocsubmapQueryService;
    @Resource
    private MdocSubMapDtoConvertor  mdocsubmapDtoConvertor;


    /**
     * 物料凭证-标签-移动平均价分页查询
     */
    @Operation(summary="物料凭证-标签-移动平均价分页查询")
    @PostMapping("/page")
    public PageResponse<MdocSubMapDto> page(@RequestBody MdocSubMapPageQuery mdocsubmapPageQuery) {
        return mdocsubmapQueryService.queryPage(mdocsubmapPageQuery);
    }

    /**
     * 物料凭证-标签-移动平均价list查询
     */
    @Operation(summary="物料凭证-标签-移动平均价list查询")
    @PostMapping("/list")
            public MultiResponse<MdocSubMapDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 物料凭证-标签-移动平均价信息
     */
    @Operation(summary="物料凭证-标签-移动平均价信息")
    @GetMapping("/find/{id}")
    public SingleResponse<MdocSubMapDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(mdocsubmapQueryService.findById(id));
    }

    /**
     * 保存物料凭证-标签-移动平均价
     */
    @Operation(summary="保存物料凭证-标签-移动平均价")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody MdocSubMapCommand mdocsubmapCommand) {
        return SingleResponse.buildSuccess(mdocsubmapApplicationService.add(mdocsubmapCommand));

    }

    /**
     * 修改物料凭证-标签-移动平均价
     */
    @Operation(summary="修改物料凭证-标签-移动平均价")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody MdocSubMapCommand mdocsubmapCommand) {
        return SingleResponse.buildSuccess(mdocsubmapApplicationService.update(mdocsubmapCommand));
    }

    /**
     * 删除物料凭证-标签-移动平均价
     */
    @Operation(summary="删除物料凭证-标签-移动平均价")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(mdocsubmapApplicationService.deleteBatch(ids));
    }

}