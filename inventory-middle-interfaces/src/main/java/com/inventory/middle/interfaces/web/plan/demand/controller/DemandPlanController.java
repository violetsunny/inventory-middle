package com.inventory.middle.interfaces.web.plan.demand.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.google.common.collect.Lists;
import com.inventory.middle.application.plan.demand.excel.DemandPlanMaterialImportExcelListener;
import com.inventory.middle.application.plan.demand.service.DemandPlanApplicationService;
import com.inventory.middle.client.plan.demand.dto.*;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.interfaces.support.UserContextHolder;
import com.inventory.middle.interfaces.web.plan.demand.dto.DemandPlanSelectRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 需求计划 Controller（从 scm-plan-bff 迁移）
 * 路径与 BFF 保持一致：/demandPlan
 */
@Tag(name = "需求计划API")
@CatchAndLog
@Slf4j
@RestController
@RequestMapping("/demandPlan")
public class DemandPlanController {

    private static final long KB_SIZE = 1024;

    @Resource
    private DemandPlanApplicationService demandPlanApplicationService;

    @Value("${demand.plan.import.uploadFile.maxSize:256}")
    private long importMaxSizeInKb;

    @Value("${demand.plan.import.uploadFile.batchSize:300}")
    private int importBatchSize;

    @Operation(summary = "创建需求计划")
    @PostMapping("/createDemandPlan")
    public SingleResponse createDemandPlan(@RequestBody DemandPlanCreateReqDTO reqDTO) {
        reqDTO.setUserId(UserContextHolder.getUserId());
        reqDTO.setUserName(UserContextHolder.getUsername());
        reqDTO.setTenantId(UserContextHolder.getTenantId());
        reqDTO.setDemandType(1);
        reqDTO.setDemandSourceType(1);
        return demandPlanApplicationService.createDemandPlan(reqDTO);
    }

    @Operation(summary = "修改需求计划展望期")
    @PostMapping("/updateDemandPlan")
    public SingleResponse updateDemandPlan(@RequestBody DemandPlanUpdateReqDTO reqDTO) {
        reqDTO.setUserId(UserContextHolder.getUserId());
        reqDTO.setUserName(UserContextHolder.getUsername());
        reqDTO.setTenantId(UserContextHolder.getTenantId());
        return demandPlanApplicationService.updateDemandPlan(reqDTO);
    }

    @Operation(summary = "下载需求计划物料导入模板")
    @GetMapping("/downloadDemandPlanMaterialTemplate")
    public void downloadDemandPlanMaterialTemplate(
            @RequestParam long demandPlanId,
            HttpServletResponse response) throws Exception {
        SingleResponse<ArrayList<String>> result = demandPlanApplicationService
                .exportTemplate(demandPlanId, UserContextHolder.getTenantId());
        if (Objects.isNull(result) || !result.isSuccess() || CollectionUtils.isEmpty(result.getData())) {
            return;
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("需求计划物料导入模板", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(out).build();
        WriteTable table = new WriteTable();
        table.setTableNo(1);
        WriteSheet sheet = EasyExcel.writerSheet(0, "物料计划").build();
        List<List<String>> headList = new ArrayList<>();
        for (String headTitle : result.getData()) {
            headList.add(Lists.newArrayList(headTitle));
        }
        table.setHead(headList);
        excelWriter.write(Lists.newArrayList(), sheet, table);
        excelWriter.finish();
    }

    @Operation(summary = "导入需求计划物料")
    @PostMapping("/importDemandPlanMaterial")
    public SingleResponse importDemandPlanMaterial(
            @RequestParam("file") MultipartFile file,
            @RequestParam("demandPlanId") Long demandPlanId) {
        if (demandPlanId == null || file == null || file.isEmpty()) {
            throw new BusinessException("请求参数错误");
        }
        String filename = file.getOriginalFilename();
        if (filename == null || (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx"))) {
            throw new BusinessException("请上传 .xls 或 .xlsx 格式文件");
        }
        long fileSizeInKb = file.getSize() / KB_SIZE;
        if (importMaxSizeInKb <= fileSizeInKb) {
            throw new BusinessException(String.format("文件超过最大限制，最大不能超过 %s kb", importMaxSizeInKb));
        }
        DemandPlanMaterialImportExcelListener listener = new DemandPlanMaterialImportExcelListener(importBatchSize);
        try {
            InputStream inputStream = new BufferedInputStream(file.getInputStream());
            EasyExcel.read(inputStream, listener).sheet().doRead();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("解析 Excel 文件失败", e);
            throw new BusinessException("解析 Excel 文件失败");
        }
        List<DemandPlanMaterialPeriodDTO> periodList = listener.getPeriodList();
        if (CollectionUtils.isEmpty(periodList)) {
            throw new BusinessException("未导入数据");
        }
        DemandPlanMaterialBatchCreateReqDTO reqDTO = new DemandPlanMaterialBatchCreateReqDTO();
        reqDTO.setDemandPlanId(demandPlanId);
        reqDTO.setTenantId(UserContextHolder.getTenantId());
        reqDTO.setUserId(UserContextHolder.getUserId());
        reqDTO.setUserName(UserContextHolder.getUsername());
        reqDTO.setPeriodList(periodList);
        return demandPlanApplicationService.createDemandPlanMaterialPeriod(reqDTO);
    }

    @Operation(summary = "修改需求计划状态")
    @PostMapping("/updateDemandPlanStatus")
    public SingleResponse updateDemandPlanStatus(@RequestBody DemandPlanUpdateStatusDTO statusDTO) {
        statusDTO.setUserId(UserContextHolder.getUserId());
        statusDTO.setUserName(UserContextHolder.getUsername());
        statusDTO.setTenantId(UserContextHolder.getTenantId());
        return demandPlanApplicationService.updateDemandPlanStatus(statusDTO);
    }

    @Operation(summary = "剔除物料")
    @PostMapping("/cancelDemandPlanMaterial")
    public SingleResponse cancelDemandPlanMaterial(@RequestBody CancelDemandPlanMaterialReqDTO reqDTO) {
        reqDTO.setUserId(UserContextHolder.getUserId());
        reqDTO.setUserName(UserContextHolder.getUsername());
        reqDTO.setTenantId(UserContextHolder.getTenantId());
        return demandPlanApplicationService.cancelDemandPlanMaterial(reqDTO);
    }

    @Operation(summary = "修改物料数量")
    @PostMapping("/modifyDemandPlanMaterialAmount")
    public SingleResponse modifyDemandPlanMaterialAmount(@RequestBody ModifyDemandPlanMaterialAmountReqDTO reqDTO) {
        reqDTO.setUserId(UserContextHolder.getUserId());
        reqDTO.setUserName(UserContextHolder.getUsername());
        reqDTO.setTenantId(UserContextHolder.getTenantId());
        return demandPlanApplicationService.modifyDemandPlanMaterialAmount(reqDTO);
    }

    @Operation(summary = "分页查询需求计划")
    @PostMapping("/selectDemandPlanByPage")
    public PageResponse<DemandPlanSelectResDTO> selectDemandPlanByPage(
            @RequestBody DemandPlanSelectRequestDTO requestDTO) {
        requestDTO.setTenantId(UserContextHolder.getTenantId());
        return demandPlanApplicationService.selectDemandPlanByPage(
                requestDTO, requestDTO.getPageNum(), requestDTO.getPageSize());
    }

    @Operation(summary = "根据逻辑仓编号列表查需求计划文件")
    @PostMapping("/selectDemandPlanByLogicalPlantNos")
    public SingleResponse<DemandPlanVersionSelectResDTO> selectDemandPlanByLogicalPlantNos(
            @RequestBody DemandPlanVersionSelectReqDTO reqDTO) {
        reqDTO.setTenantId(UserContextHolder.getTenantId());
        return demandPlanApplicationService.selectByLogicalPlantNos(reqDTO);
    }

    @Operation(summary = "需求计划详情")
    @PostMapping("/demandPlanDetail")
    public SingleResponse<DemandPlanDetailSelectResDTO> selectDemandPlanDetail(
            @RequestBody DemandPlanDetailSelectReqDTO reqDTO) {
        reqDTO.setTenantId(UserContextHolder.getTenantId());
        return demandPlanApplicationService.selectDemandPlanDetail(reqDTO);
    }
}
