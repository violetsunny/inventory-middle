package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.MdocSubInOutQueryService;
import com.inventory.middle.application.service.MdocSubInOutApplicationService;
import com.inventory.middle.client.dto.MdocSubInOutDto;
import com.inventory.middle.client.dto.command.MdocSubInOutCommand;
import com.inventory.middle.client.dto.query.MdocSubInOutPageQuery;
import com.inventory.middle.application.convertor.MdocSubInOutDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 物料凭证子表-出入库信息Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:26
 */
@Tag(name = " 物料凭证子表-出入库信息管理")
@RestController
@RequestMapping("/mdocsubinout")
@Slf4j
@CatchAndLog
public class MdocSubInOutController {

    @Resource
    private MdocSubInOutApplicationService mdocsubinoutApplicationService;
    @Resource
    private  MdocSubInOutQueryService mdocsubinoutQueryService;
    @Resource
    private MdocSubInOutDtoConvertor  mdocsubinoutDtoConvertor;


    /**
     * 物料凭证子表-出入库信息分页查询
     */
    @Operation(summary="物料凭证子表-出入库信息分页查询")
    @PostMapping("/page")
    public PageResponse<MdocSubInOutDto> page(@RequestBody MdocSubInOutPageQuery mdocsubinoutPageQuery) {
        return mdocsubinoutQueryService.queryPage(mdocsubinoutPageQuery);
    }

    /**
     * 物料凭证子表-出入库信息list查询
     */
    @Operation(summary="物料凭证子表-出入库信息list查询")
    @PostMapping("/list")
            public MultiResponse<MdocSubInOutDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 物料凭证子表-出入库信息信息
     */
    @Operation(summary="物料凭证子表-出入库信息信息")
    @GetMapping("/find/{id}")
    public SingleResponse<MdocSubInOutDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(mdocsubinoutQueryService.findById(id));
    }

    /**
     * 保存物料凭证子表-出入库信息
     */
    @Operation(summary="保存物料凭证子表-出入库信息")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody MdocSubInOutCommand mdocsubinoutCommand) {
        return SingleResponse.buildSuccess(mdocsubinoutApplicationService.add(mdocsubinoutCommand));

    }

    /**
     * 修改物料凭证子表-出入库信息
     */
    @Operation(summary="修改物料凭证子表-出入库信息")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody MdocSubInOutCommand mdocsubinoutCommand) {
        return SingleResponse.buildSuccess(mdocsubinoutApplicationService.update(mdocsubinoutCommand));
    }

    /**
     * 删除物料凭证子表-出入库信息
     */
    @Operation(summary="删除物料凭证子表-出入库信息")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(mdocsubinoutApplicationService.deleteBatch(ids));
    }

}