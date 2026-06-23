package com.inventory.middle.interfaces.web.plan;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.inventory.middle.application.plan.config.bo.*;
import com.inventory.middle.application.plan.config.service.PlanConfigService;
import com.inventory.middle.domain.plan.common.constants.CommonConstants;
import com.inventory.middle.application.plan.calculate.service.PlanGenerateApplicationService;
import com.inventory.middle.interfaces.web.plan.PlanConfigWebConverter;
import com.inventory.middle.interfaces.web.plan.PlanReportConverter;
import com.inventory.middle.interfaces.web.plan.dto.*;
import com.inventory.middle.interfaces.web.plan.vo.*;
import com.inventory.middle.client.plan.config.dto.PlanGenerateRequest;
import com.inventory.middle.client.plan.config.dto.PlanInstanceDTO;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.inventory.middle.interfaces.support.UserContext;
import com.inventory.middle.interfaces.support.UserContextHolder;
import top.kdla.framework.dto.PagedSingleResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author peisheng.wang
 * @version 1.0
 * @description: 计划配置controller
 * @date 2021/9/26 11:14
 */
@Slf4j
@CatchAndLog
@RestController
@RequestMapping("/planConfig")
@Tag(name = "计划配置管理")
@CrossOrigin(origins = "*")
public class PlanConfigController  {

    @Resource
    private PlanConfigService planConfigService;

    @Resource
    private PlanReportConverter planReportConverter;

    @Resource
    private PlanGenerateApplicationService planGenerateApplicationService;

    /**
     * 下载物料计划参数导入模板
     *
     * @param response
     * @throws Exception
     */
    @Operation(summary = "下载物料计划参数导入模板")
    @GetMapping(value = "/downloadPlanMaterialParamTemplate")
        public void downloadPlanMaterialParamTemplate(HttpServletResponse response) throws Exception {
        UserContextHolder.get();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("计划参数导入模板", "UTF-8").replaceAll(CommonConstants.TRANSFERRED_PLUS, CommonConstants.TRANSFERRED_BLANK);
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + CommonConstants.DOT_XLSX);
        ServletOutputStream out = response.getOutputStream();
        EasyExcelFactory.write(out, PlanMaterialParamImportExcelBO.class).sheet("sheet1").doWrite(Lists.newArrayList());
    }

    /**
     * 批量导入物料计划参数
     *
     * @param reqDTO
     * @param token
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/importPlanMaterialParam")
    @ResponseBody
    @Operation(summary = "批量导入物料计划参数")
        public SingleResponse<PlanMaterialParamImportResVO> importPlanMaterialParam(PlanMaterialParamImportReqDTO reqDTO, @RequestHeader(value = "ennUnifiedAuthorization", required = false) String token) throws Exception {

        UserContext userInfo = UserContextHolder.get();

        PlanMaterialParamImportReqBO reqBO = PlanConfigWebConverter.convertPlanMaterialParamImportReqDTO2BO(reqDTO, token);

        return SingleResponse.buildSuccess();
    }

    /**
     * 更新物料计划参数
     *
     * @param reqDTO
     * @return
     */
    @PostMapping("/updatePlanMaterialParam")
    @ResponseBody
    @Operation(summary = "更新物料计划参数")
        public SingleResponse<Boolean> updatePlanMaterialParam(@RequestBody PlanMaterialParamUpdateReqDTO reqDTO) {

        PlanMaterialParameterBO bo = PlanConfigWebConverter.convertPlanMaterialParamUpdateDTO2BO(reqDTO);

        return SingleResponse.buildSuccess(planConfigService.updatePlanMaterialParam(bo));
    }

    /**
     * 查询物料计划参数
     *
     * @param reqDTO
     * @return
     */
    @PostMapping("/queryPlanMaterialParam")
    @ResponseBody
    @Operation(summary = "查询物料计划参数")
        public SingleResponse<PlanMaterialParamQueryResVO> queryPlanMaterialParam(@RequestBody PlanMaterialParamQueryReqDTO reqDTO) {

        PlanMaterialParamQueryReqBO reqBO = PlanConfigWebConverter.convertPlanMaterialParamQueryReqDTO2BO(reqDTO);

        PlanMaterialParameterBO resBO = planConfigService.queryByMaterialCodeAndLogicalPlantNo(reqBO);

        return SingleResponse.buildSuccess(PlanConfigWebConverter.convertPlanMaterialParamQueryResBO2VO(resBO));
    }

    /**
     * 分页查询物料计划参数
     *
     * @param reqDTO
     * @return
     */
    @PostMapping("/pageQueryPlanMaterialParam")
    @ResponseBody
    @Operation(summary = "分页查询物料计划参数")
        public PagedSingleResponse<PlanMaterialParamPageResVO> pageQueryPlanMaterialParam(@RequestBody PlanMaterialParamPageReqDTO reqDTO) {

        PlanMaterialParamPageReqBO bo = PlanConfigWebConverter.convertPlanMaterialParamPageReqDTO2BO(reqDTO);

        if (StringUtils.isNotEmpty(reqDTO.getExternalMaterialCode())) {
            bo.setMaterialCode(reqDTO.getExternalMaterialCode());
        }

        PagedSingleResponse<PlanMaterialParameterBO> result = planConfigService.pageQueryPlanMaterialParam(bo);

        List<PlanMaterialParamPageResVO> data = result.getData().stream()
                .map(d -> PlanConfigWebConverter.convertPlanMaterialParamPageResBO2VO(d))
                .collect(Collectors.toList());

        return PagedSingleResponse.success(reqDTO.getPageNum(), reqDTO.getPageSize(), result.getTotalCount(), data);
    }

    @PostMapping("/createPlan")
    @ResponseBody
    @Operation(summary = "创建计划方案")
        public SingleResponse<Boolean> createPlan(@RequestBody PlanCreateReqDTO reqDTO) throws ParseException {

        PlanBO bo = PlanConfigWebConverter.convertPlanCreateReqDTO2BO(reqDTO);

        return SingleResponse.buildSuccess(planConfigService.createPlan(bo));
    }

    @PostMapping("/queryPlan")
    @ResponseBody
    @Operation(summary = "查询计划方案")
        public SingleResponse<PlanQueryResVO> queryPlan(@RequestParam("planId") Long planId) {
        UserContext userInfo = UserContextHolder.get();
        return SingleResponse.buildSuccess(PlanConfigWebConverter.convertPlanQueryResBO2VO(planConfigService.queryPlanById(planId, userInfo.getTenantId())));
    }

    @PostMapping("/updatePlan")
    @ResponseBody
    @Operation(summary = "更新计划方案")
        public SingleResponse<Boolean> updatePlan(@RequestBody PlanUpdateReqDTO reqDTO) throws ParseException {

        PlanBO bo = PlanConfigWebConverter.convertPlanUpdateReqDTO2BO(reqDTO);

        return SingleResponse.buildSuccess(planConfigService.updatePlan(bo));
    }

    @PostMapping("/pageQueryPlan")
    @ResponseBody
    @Operation(summary = "分页查询计划方案")
        public PagedSingleResponse<PlanPageResVO> pageQueryPlan(@RequestBody PlanPageReqDTO reqDTO) {

        PlanPageReqBO bo = PlanConfigWebConverter.convertPlanPageReqDTO2BO(reqDTO);

        PagedSingleResponse<PlanBO> result = planConfigService.pageQueryPlan(bo);

        List<PlanPageResVO> data = result.getData().stream()
                .map(d -> PlanConfigWebConverter.convertPlanPageResBO2VO(d))
                .collect(Collectors.toList());

        return PagedSingleResponse.success(reqDTO.getPageNum(), reqDTO.getPageSize(), result.getTotalCount(), data);
    }

    @PostMapping("/changePlanStatus")
    @ResponseBody
    @Operation(summary = "切换计划方案有效状态")
        public SingleResponse<Boolean> changePlanStatus(@RequestBody PlanChangeStatusReqDTO reqDTO) throws ParseException {

        UserContext userInfo = UserContextHolder.get();

        ChangeStatusPlanBO bo = PlanConfigWebConverter.convertPlanChangeStatusReqDTO2BO(reqDTO, userInfo);

        return SingleResponse.buildSuccess(planConfigService.changeStatus(bo));
    }

    @Operation(summary = "下载计划物料清单导入模板")
    @GetMapping(value = "/downloadPlanMaterialTemplate")
        public void downloadPlanMaterialTemplate(HttpServletResponse response) throws Exception {
        UserContextHolder.get();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("计划物料清单导入模板", "UTF-8").replaceAll(CommonConstants.TRANSFERRED_PLUS, CommonConstants.TRANSFERRED_BLANK);
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + CommonConstants.DOT_XLSX);
        ServletOutputStream out = response.getOutputStream();
        EasyExcelFactory.write(out, PlanMaterialImportExcelBO.class).sheet("sheet1").doWrite(Lists.newArrayList());
    }

    /**
     * 批量导入计划物料清单
     *
     * @return
     * @throws Exception
     */
    @Operation(summary = "批量导入计划物料清单")
    @RequestMapping(value = "/importPlanMaterial", method = {RequestMethod.POST})
        public SingleResponse<PlanMaterialImportResVO> importPlanMaterial(PlanMaterialImportReqDTO reqDTO, @RequestHeader(value = "ennUnifiedAuthorization", required = false) String token) throws IOException {

        PlanMaterialImportReqBO reqBO = PlanConfigWebConverter.convertPlanMaterialImportReqDTO2BO(reqDTO, token);

        return SingleResponse.buildSuccess();
    }

    /**
     * 批量删除计划物料清单
     *
     * @return
     * @throws Exception
     */
    @Operation(summary = "批量删除计划物料清单")
    @RequestMapping(value = "/deletePlanMaterial", method = {RequestMethod.POST})
        public SingleResponse<PlanMaterialImportResVO> deletePlanMaterial(PlanMaterialImportReqDTO reqDTO, @RequestHeader(value = "ennUnifiedAuthorization", required = false) String token) throws IOException {

        PlanMaterialImportReqBO reqBO = PlanConfigWebConverter.convertPlanMaterialImportReqDTO2BO(reqDTO, token);

        return SingleResponse.buildSuccess();
    }

    @PostMapping("/queryPlanMaterial")
    @ResponseBody
    @Operation(summary = "查询计划物料清单")
        public void queryPlanMaterial(@RequestParam("planId") Long planId, HttpServletResponse response) throws IOException {
        UserContext userInfo = UserContextHolder.get();

        List<PlanMaterialBO> bos = planConfigService.queryPlanMaterialByPlanId(planId, userInfo.getTenantId());
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("计划物料清单", "UTF-8").replaceAll(CommonConstants.TRANSFERRED_PLUS, CommonConstants.TRANSFERRED_BLANK);
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + CommonConstants.DOT_XLSX);

        ExcelWriter writer = null;
        try {
            writer = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet sheet1 = EasyExcel.writerSheet("导入结果汇总").head(PlanMaterialImportExcelBO.class).build();
            writer.write(bos, sheet1);
        } finally {
            if (writer != null) {
                writer.finish();
            }
        }
    }


    /**
     * 执行计划
     *
     * @return 计划执行结果
     */
    @Operation(summary = "执行计划")
    @RequestMapping(value = "/generate", method = {RequestMethod.POST})
        public SingleResponse<PlanGenerateResultVO> generatePlan(@RequestParam("planId") Long planId,
                                                         @RequestHeader(value = "ennUnifiedAuthorization", required = false) String token) {
        UserContext userInfo = UserContextHolder.get();
        PlanGenerateRequest request = new PlanGenerateRequest();
        request.setPlanId(planId);
        request.setUserId(userInfo.getUserId());
        request.setUserName(userInfo.getUsername());
        request.setTenantId(userInfo.getTenantId());
        request.setOperator(userInfo.getUsername());
        SingleResponse<PlanInstanceDTO> result = planGenerateApplicationService.generate(request);
        if (!result.isSuccess()) {
            return SingleResponse.buildFailure(result.getCode(), result.getMessage());
        }
        return SingleResponse.buildSuccess(planReportConverter.convertPlanInstance(result.getData()));
    }

    /**
     * 获取计划参数列表
     *
     * @return hashMap
     */
    @PostMapping("/queryPlanParamList")
    @ResponseBody
    @Operation(summary = "获取计划参数枚举类列表")
        public SingleResponse<PlanParamEnumResVO> queryPlanParamList() {

        UserContextHolder.get();
        PlanParamEnumResBO resBO = planConfigService.getPlanParamEnumList();
        return SingleResponse.buildSuccess(PlanConfigWebConverter.convertPlanParamBO2VO(resBO));
    }

    /**
     * 获取计划物料参数列表
     *
     * @return hashMap
     */
    @PostMapping("/queryPlanMaterialParamList")
    @ResponseBody
    @Operation(summary = "获取计划物料参数枚举类列表")
        public SingleResponse<PlanMaterialParamEnumResVO> queryPlanMaterialParamList() {
        UserContextHolder.get();
        PlanMaterialParamEnumResBO resBO = planConfigService.getPlanMaterialParamEnumList();
        return SingleResponse.buildSuccess(PlanConfigWebConverter.convertPlanMaterialParamBO2VO(resBO));
    }

    /**
     * 根据租户id查询计划物料参数可调拨逻辑仓
     *
     * @return
     */
    @PostMapping("/queryPlanTransferLogicalPlants")
    @ResponseBody
    @Operation(summary = "根据租户id查询计划物料参数可调拨逻辑仓")
        public SingleResponse<PlanMaterialLogicalPlantResVO> queryPlanTransferLogicalPlants(
            @RequestParam(value = "materialCode",required = false) String materialCode,
            @RequestParam(value = "externalMaterialCode",required = false) String externalMaterialCode,
            @RequestParam("logicalPlantNo") String logicalPlantNo) {
        UserContext userInfo = UserContextHolder.get();
        String materialCodeToUse = Optional.ofNullable(materialCode).orElse(externalMaterialCode);
        QueryPlanTransferLogicalPlantsReqBO bo = PlanConfigWebConverter.convertQueryPlanTransferLogicalPlantNoBO(materialCodeToUse, logicalPlantNo, userInfo.getTenantId());
        PlanTransferLogicalPlantsResBO resBO = planConfigService.queryPlanTransferLogicalPlants(bo);
        return SingleResponse.buildSuccess(PlanConfigWebConverter.convertPlanTransferLogicalPlantsResBO2VO(resBO));
    }

}
