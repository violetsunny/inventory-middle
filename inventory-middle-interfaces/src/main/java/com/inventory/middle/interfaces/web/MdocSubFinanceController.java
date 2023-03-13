package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.MdocSubFinanceQueryService;
import com.inventory.middle.application.service.MdocSubFinanceApplicationService;
import com.inventory.middle.client.dto.MdocSubFinanceDto;
import com.inventory.middle.client.dto.command.MdocSubFinanceCommand;
import com.inventory.middle.client.dto.query.MdocSubFinancePageQuery;
import com.inventory.middle.application.convertor.MdocSubFinanceDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 物料凭证-标签-财务Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:26
 */
@Tag(name = " 物料凭证-标签-财务管理")
@RestController
@RequestMapping("/mdocsubfinance")
@Slf4j
@CatchAndLog
public class MdocSubFinanceController {

    @Resource
    private MdocSubFinanceApplicationService mdocsubfinanceApplicationService;
    @Resource
    private  MdocSubFinanceQueryService mdocsubfinanceQueryService;
    @Resource
    private MdocSubFinanceDtoConvertor  mdocsubfinanceDtoConvertor;


    /**
     * 物料凭证-标签-财务分页查询
     */
    @Operation(summary="物料凭证-标签-财务分页查询")
    @PostMapping("/page")
    public PageResponse<MdocSubFinanceDto> page(@RequestBody MdocSubFinancePageQuery mdocsubfinancePageQuery) {
        return mdocsubfinanceQueryService.queryPage(mdocsubfinancePageQuery);
    }

    /**
     * 物料凭证-标签-财务list查询
     */
    @Operation(summary="物料凭证-标签-财务list查询")
    @PostMapping("/list")
            public MultiResponse<MdocSubFinanceDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 物料凭证-标签-财务信息
     */
    @Operation(summary="物料凭证-标签-财务信息")
    @GetMapping("/find/{id}")
    public SingleResponse<MdocSubFinanceDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(mdocsubfinanceQueryService.findById(id));
    }

    /**
     * 保存物料凭证-标签-财务
     */
    @Operation(summary="保存物料凭证-标签-财务")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody MdocSubFinanceCommand mdocsubfinanceCommand) {
        return SingleResponse.buildSuccess(mdocsubfinanceApplicationService.add(mdocsubfinanceCommand));

    }

    /**
     * 修改物料凭证-标签-财务
     */
    @Operation(summary="修改物料凭证-标签-财务")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody MdocSubFinanceCommand mdocsubfinanceCommand) {
        return SingleResponse.buildSuccess(mdocsubfinanceApplicationService.update(mdocsubfinanceCommand));
    }

    /**
     * 删除物料凭证-标签-财务
     */
    @Operation(summary="删除物料凭证-标签-财务")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(mdocsubfinanceApplicationService.deleteBatch(ids));
    }

}