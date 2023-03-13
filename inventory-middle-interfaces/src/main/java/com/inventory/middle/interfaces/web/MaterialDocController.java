package com.inventory.middle.interfaces.web;


import com.inventory.middle.application.convertor.MaterialDocMainDtoConvertor;
import com.inventory.middle.application.service.MaterialDocMainApplicationService;
import com.inventory.middle.application.service.MaterialDocMainQueryService;
import com.inventory.middle.application.service.MaterialDocQueryService;
import com.inventory.middle.client.dto.MaterialDocMainDto;
import com.inventory.middle.client.dto.query.MaterialMappingQuery;
import com.inventory.middle.client.dto.material.MaterialBatchNoResDto;
import com.inventory.middle.client.dto.material.MaterialMappingDto;
import com.inventory.middle.client.dto.query.MaterialBatchNoQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;


/**
 * 物料凭证主表Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-10 15:43:33
 */
@Tag(name = " 物料凭证主表管理")
@RestController
@RequestMapping("/materialdocmain")
@Slf4j
@CatchAndLog
public class MaterialDocController {

    @Resource
    private MaterialDocMainApplicationService materialdocmainApplicationService;
    @Resource
    private MaterialDocMainQueryService materialdocmainQueryService;
    @Resource
    private MaterialDocMainDtoConvertor materialdocmainDtoConvertor;
    @Resource
    private MaterialDocQueryService materialDocQueryService;


    @Operation(summary = "获取移动类型映射关系")
    @PostMapping("/queryMaterialTypeMapping")
    public SingleResponse<MaterialMappingDto> queryMaterialTypeMapping(@RequestBody MaterialMappingQuery req) {
        return SingleResponse.buildSuccess(materialDocQueryService.queryMaterialTypeMapping(req));
    }


    @Operation(summary = "查询物料批次")
    @PostMapping("/queryMaterialBatchNo")
    public SingleResponse<MaterialBatchNoResDto> queryMaterialBatchNo(MaterialBatchNoQuery query) {
        return SingleResponse.buildSuccess(materialDocQueryService.queryMaterialBatchNo(query));
    }
}