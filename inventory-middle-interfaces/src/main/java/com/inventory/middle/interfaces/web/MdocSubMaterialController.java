package com.inventory.middle.interfaces.web;


import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.catchlog.CatchAndLog;
import top.kdla.framework.validator.group.AddGroup;
import top.kdla.framework.validator.group.UpdateGroup;
import com.inventory.middle.application.service.MdocSubMaterialQueryService;
import com.inventory.middle.application.service.MdocSubMaterialApplicationService;
import com.inventory.middle.client.dto.MdocSubMaterialDto;
import com.inventory.middle.client.dto.command.MdocSubMaterialCommand;
import com.inventory.middle.client.dto.query.MdocSubMaterialPageQuery;
import com.inventory.middle.application.convertor.MdocSubMaterialDtoConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import javax.annotation.Resource;


/**
 * 物料凭证子表-物料信息Controller
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:56:25
 */
@Tag(name = " 物料凭证子表-物料信息管理")
@RestController
@RequestMapping("/mdocsubmaterial")
@Slf4j
@CatchAndLog
public class MdocSubMaterialController {

    @Resource
    private MdocSubMaterialApplicationService mdocsubmaterialApplicationService;
    @Resource
    private  MdocSubMaterialQueryService mdocsubmaterialQueryService;
    @Resource
    private MdocSubMaterialDtoConvertor  mdocsubmaterialDtoConvertor;


    /**
     * 物料凭证子表-物料信息分页查询
     */
    @Operation(summary="物料凭证子表-物料信息分页查询")
    @PostMapping("/page")
    public PageResponse<MdocSubMaterialDto> page(@RequestBody MdocSubMaterialPageQuery mdocsubmaterialPageQuery) {
        return mdocsubmaterialQueryService.queryPage(mdocsubmaterialPageQuery);
    }

    /**
     * 物料凭证子表-物料信息list查询
     */
    @Operation(summary="物料凭证子表-物料信息list查询")
    @PostMapping("/list")
            public MultiResponse<MdocSubMaterialDto> list() {
        //TODO list query
        return MultiResponse.buildSuccess(null);
    }

    /**
     * 物料凭证子表-物料信息信息
     */
    @Operation(summary="物料凭证子表-物料信息信息")
    @GetMapping("/find/{id}")
    public SingleResponse<MdocSubMaterialDto> findById(@PathVariable("id") Long id) {
        return SingleResponse.buildSuccess(mdocsubmaterialQueryService.findById(id));
    }

    /**
     * 保存物料凭证子表-物料信息
     */
    @Operation(summary="保存物料凭证子表-物料信息")
    @PostMapping("/save")
    public SingleResponse<Boolean> save(@Validated(AddGroup.class) @RequestBody MdocSubMaterialCommand mdocsubmaterialCommand) {
        return SingleResponse.buildSuccess(mdocsubmaterialApplicationService.add(mdocsubmaterialCommand));

    }

    /**
     * 修改物料凭证子表-物料信息
     */
    @Operation(summary="修改物料凭证子表-物料信息")
    @PostMapping("/update")
    public SingleResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody MdocSubMaterialCommand mdocsubmaterialCommand) {
        return SingleResponse.buildSuccess(mdocsubmaterialApplicationService.update(mdocsubmaterialCommand));
    }

    /**
     * 删除物料凭证子表-物料信息
     */
    @Operation(summary="删除物料凭证子表-物料信息")
    @PostMapping("/delete")
    public SingleResponse<Boolean> delete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(mdocsubmaterialApplicationService.deleteBatch(ids));
    }

}