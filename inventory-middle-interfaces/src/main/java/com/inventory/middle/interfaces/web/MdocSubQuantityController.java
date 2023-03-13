package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.MdocSubQuantityQueryService;
import com.inventory.middle.application.service.MdocSubQuantityApplicationService;
import com.inventory.middle.client.dto.MdocSubQuantityDto;
import com.inventory.middle.client.dto.command.MdocSubQuantityCommand;
import com.inventory.middle.client.dto.query.MdocSubQuantityPageQuery;
import com.inventory.middle.application.convertor.MdocSubQuantityDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 物料凭证子表-数量Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:25
 */
@Tag(name = " 物料凭证子表-数量管理")
@RestController
@RequestMapping("/mdocsubquantity")
@Slf4j
@CatchAndLog
public class MdocSubQuantityController {

    @Resource
    private MdocSubQuantityApplicationService mdocsubquantityApplicationService;
    @Resource
    private  MdocSubQuantityQueryService mdocsubquantityQueryService;
    @Resource
    private MdocSubQuantityDtoConvertor  mdocsubquantityDtoConvertor;


    /**
     * 物料凭证子表-数量分页查询
     */
    @Operation(summary="物料凭证子表-数量分页查询")
    @PostMapping("/page")
    public PageResponse<MdocSubQuantityDto> page(@RequestBody MdocSubQuantityPageQuery mdocsubquantityPageQuery) {
        return mdocsubquantityQueryService.queryPage(mdocsubquantityPageQuery);
    }

    /**
     * 物料凭证子表-数量list查询
     */
    @Operation(summary="物料凭证子表-数量list查询")
    @PostMapping("/list")
            public MultiResponse<MdocSubQuantityDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 物料凭证子表-数量信息
     */
    @Operation(summary="物料凭证子表-数量信息")
    @GetMapping("/find/{id}")
    public SingleResponse<MdocSubQuantityDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(mdocsubquantityQueryService.findById(id));
    }

    /**
     * 保存物料凭证子表-数量
     */
    @Operation(summary="保存物料凭证子表-数量")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody MdocSubQuantityCommand mdocsubquantityCommand) {
        return SingleResponse.buildSuccess(mdocsubquantityApplicationService.add(mdocsubquantityCommand));

    }

    /**
     * 修改物料凭证子表-数量
     */
    @Operation(summary="修改物料凭证子表-数量")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody MdocSubQuantityCommand mdocsubquantityCommand) {
        return SingleResponse.buildSuccess(mdocsubquantityApplicationService.update(mdocsubquantityCommand));
    }

    /**
     * 删除物料凭证子表-数量
     */
    @Operation(summary="删除物料凭证子表-数量")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(mdocsubquantityApplicationService.deleteBatch(ids));
    }

}