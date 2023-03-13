package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.MdocSubExtQueryService;
import com.inventory.middle.application.service.MdocSubExtApplicationService;
import com.inventory.middle.client.dto.MdocSubExtDto;
import com.inventory.middle.client.dto.command.MdocSubExtCommand;
import com.inventory.middle.client.dto.query.MdocSubExtPageQuery;
import com.inventory.middle.application.convertor.MdocSubExtDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 物料凭证-标签-扩展Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:26
 */
@Tag(name = " 物料凭证-标签-扩展管理")
@RestController
@RequestMapping("/mdocsubext")
@Slf4j
@CatchAndLog
public class MdocSubExtController {

    @Resource
    private MdocSubExtApplicationService mdocsubextApplicationService;
    @Resource
    private  MdocSubExtQueryService mdocsubextQueryService;
    @Resource
    private MdocSubExtDtoConvertor  mdocsubextDtoConvertor;


    /**
     * 物料凭证-标签-扩展分页查询
     */
    @Operation(summary="物料凭证-标签-扩展分页查询")
    @PostMapping("/page")
    public PageResponse<MdocSubExtDto> page(@RequestBody MdocSubExtPageQuery mdocsubextPageQuery) {
        return mdocsubextQueryService.queryPage(mdocsubextPageQuery);
    }

    /**
     * 物料凭证-标签-扩展list查询
     */
    @Operation(summary="物料凭证-标签-扩展list查询")
    @PostMapping("/list")
            public MultiResponse<MdocSubExtDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 物料凭证-标签-扩展信息
     */
    @Operation(summary="物料凭证-标签-扩展信息")
    @GetMapping("/find/{id}")
    public SingleResponse<MdocSubExtDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(mdocsubextQueryService.findById(id));
    }

    /**
     * 保存物料凭证-标签-扩展
     */
    @Operation(summary="保存物料凭证-标签-扩展")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody MdocSubExtCommand mdocsubextCommand) {
        return SingleResponse.buildSuccess(mdocsubextApplicationService.add(mdocsubextCommand));

    }

    /**
     * 修改物料凭证-标签-扩展
     */
    @Operation(summary="修改物料凭证-标签-扩展")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody MdocSubExtCommand mdocsubextCommand) {
        return SingleResponse.buildSuccess(mdocsubextApplicationService.update(mdocsubextCommand));
    }

    /**
     * 删除物料凭证-标签-扩展
     */
    @Operation(summary="删除物料凭证-标签-扩展")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(mdocsubextApplicationService.deleteBatch(ids));
    }

}