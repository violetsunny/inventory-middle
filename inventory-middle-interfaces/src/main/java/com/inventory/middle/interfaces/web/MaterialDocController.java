package com.inventory.middle.interfaces.web;

import com.inventory.middle.application.service.FileImportApplicationService;
import com.inventory.middle.application.service.MaterialDocMainApplicationService;
import com.inventory.middle.application.service.MaterialDocMainQueryService;
import com.inventory.middle.application.service.MaterialDocQueryService;
import com.inventory.middle.client.dto.MaterialDocMainDto;
import com.inventory.middle.client.dto.material.MaterialMappingDTO;
import com.inventory.middle.client.dto.material.QueryMaterialBatchNoResDTO;
import com.inventory.middle.client.dto.query.MaterialBatchNoQuery;
import com.inventory.middle.client.dto.query.MaterialDocMainPageQuery;
import com.inventory.middle.client.dto.query.MaterialMappingQuery;
import com.inventory.middle.client.enums.MaterialDocGroupEnum;
import com.inventory.middle.client.enums.MaterialDocTypeEnum;
import com.inventory.middle.client.file.dto.request.PageQueryFileImportRecordRequest;
import com.inventory.middle.client.file.dto.response.FileImportRecord;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.service.material.model.MaterialDocInvRes;
import com.inventory.middle.interfaces.support.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import top.kdla.framework.log.catchlog.CatchAndLog;


/**
 * 物料凭证 Controller（从 inventory-center-bff 迁移）
 * 路径与 BFF 保持一致：/materialDoc
 */
@Tag(name = "物料凭证管理")
@CatchAndLog
@RestController
@RequestMapping("/materialDoc")
@Slf4j
public class MaterialDocController {

    @Resource
    private MaterialDocMainApplicationService materialDocMainApplicationService;
    @Resource
    private MaterialDocMainQueryService materialDocMainQueryService;
    @Resource
    private MaterialDocQueryService materialDocQueryService;
    @Resource
    private FileImportApplicationService fileImportApplicationService;

    // ==================== 查询类 ====================

    @Operation(summary = "查询物料凭证操作映射关系")
    @GetMapping("/queryMaterialTypeMapping")
    public SingleResponse<MaterialMappingDTO> queryMaterialTypeMapping() {
        return SingleResponse.buildSuccess(materialDocQueryService.queryMaterialTypeMapping(new MaterialMappingQuery()));
    }

    @Operation(summary = "查询物料凭证请求Id")
    @GetMapping("/queryMaterialDocId")
    public SingleResponse<String> queryMaterialDocId() {
        return SingleResponse.buildSuccess(materialDocMainApplicationService.getMaterialDocId());
    }

    @Operation(summary = "查询物料凭证（按原始单号）")
    @GetMapping("/queryMaterialDoc")
    public SingleResponse<MaterialDocMainDto> queryMaterialDoc(@RequestParam String originalNo) {
        return SingleResponse.buildSuccess(materialDocMainQueryService.getByOriginalNo(originalNo));
    }

    @Operation(summary = "查询物料批次")
    @PostMapping("/queryMaterialBatchNo")
    public SingleResponse<QueryMaterialBatchNoResDTO> queryMaterialBatchNo(@RequestBody MaterialBatchNoQuery query) {
        return SingleResponse.buildSuccess(materialDocQueryService.queryMaterialBatchNo(query));
    }

    @Operation(summary = "分页查询物料凭证")
    @PostMapping("/page/query")
    public PageResponse<MaterialDocMainDto> queryMaterialDocPage(@RequestBody MaterialDocMainPageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return materialDocMainQueryService.queryPage(pageQuery);
    }

    @Operation(summary = "物料凭证类型枚举")
    @GetMapping("/queryMaterialDocType")
    public SingleResponse<List<EnumVO>> queryMaterialDocType() {
        List<EnumVO> list = Arrays.stream(MaterialDocTypeEnum.values())
                .map(e -> new EnumVO(String.valueOf(e.getCode()), e.getDesc()))
                .collect(Collectors.toList());
        return SingleResponse.buildSuccess(list);
    }

    @Operation(summary = "物料凭证分组枚举")
    @GetMapping("/queryMaterialDocGroup")
    public SingleResponse<List<EnumVO>> queryMaterialDocGroup() {
        List<EnumVO> list = Arrays.stream(MaterialDocGroupEnum.values())
                .map(e -> new EnumVO(e.getCode(), e.getDesc()))
                .collect(Collectors.toList());
        return SingleResponse.buildSuccess(list);
    }

    @Operation(summary = "查询组装物料信息")
    @GetMapping("/queryBuildMaterialInfo")
    public SingleResponse<Object> queryBuildMaterialInfo(@RequestParam String skuCode) {
        // TODO: 待接入 ProductExternalService.queryBuildMaterialInfo(skuCode)
        log.warn("queryBuildMaterialInfo: 待接入 ProductExternalService, skuCode={}", skuCode);
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "通过名称模糊查询物料信息")
    @PostMapping("/queryMaterialInfoByName")
    public SingleResponse<Object> queryMaterialInfoByName(@RequestBody MaterialFuzzyQueryReq req) {
        // TODO: 待接入 ProductExternalService.fuzzyQueryByName(req)
        log.warn("queryMaterialInfoByName: 待接入 ProductExternalService, skuName={}", req.getSkuName());
        return SingleResponse.buildSuccess(null);
    }

    // ==================== 写操作类 ====================

    @Operation(summary = "生成物料凭证（通用）")
    @PostMapping("/createMaterialDoc")
    public SingleResponse<MaterialDocInvRes> createMaterialDoc(@RequestBody MaterialDocumentBO bo) {
        bo.setTenantId(UserContextHolder.getTenantId());
        bo.setOperator(UserContextHolder.getUserId());
        // 取消场景路由到 reverseMaterialDoc
        if (bo.getMaterialDocCategory() != null && bo.getMaterialDocCategory() == 3) {
            return materialDocMainApplicationService.reverseMaterialDoc(bo);
        }
        if ("IN".equals(bo.getIo())) {
            return materialDocMainApplicationService.createMaterialDocIn(bo);
        }
        return materialDocMainApplicationService.createMaterialDocOut(bo);
    }

    @Operation(summary = "生成入库物料凭证")
    @PostMapping("/inboundMaterialDoc")
    public SingleResponse<MaterialDocInvRes> inboundMaterialDoc(@RequestBody MaterialDocumentBO bo) {
        bo.setTenantId(UserContextHolder.getTenantId());
        bo.setOperator(UserContextHolder.getUserId());
        return materialDocMainApplicationService.createMaterialDocIn(bo);
    }

    @Operation(summary = "生成出库物料凭证")
    @PostMapping("/outboundMaterialDoc")
    public SingleResponse<MaterialDocInvRes> outboundMaterialDoc(@RequestBody MaterialDocumentBO bo) {
        bo.setTenantId(UserContextHolder.getTenantId());
        bo.setOperator(UserContextHolder.getUserId());
        return materialDocMainApplicationService.createMaterialDocOut(bo);
    }

    @Operation(summary = "取消物料凭证")
    @PostMapping("/cancelMaterialDoc")
    public SingleResponse<MaterialDocInvRes> cancelMaterialDoc(@RequestBody MaterialDocumentBO bo) {
        bo.setTenantId(UserContextHolder.getTenantId());
        bo.setOperator(UserContextHolder.getUserId());
        return materialDocMainApplicationService.reverseMaterialDoc(bo);
    }

    @Operation(summary = "检查物料凭证参数")
    @PostMapping("/checkParam")
    public SingleResponse<Boolean> checkParam(@RequestBody MaterialDocumentBO bo) {
        bo.setTenantId(UserContextHolder.getTenantId());
        return SingleResponse.buildSuccess(materialDocMainApplicationService.checkMaterialDoc(bo));
    }

    @Operation(summary = "导出物料凭证")
    @PostMapping("/export")
    public void export(@RequestBody MaterialDocMainPageQuery pageQuery, HttpServletResponse response) throws java.io.IOException {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        java.util.List<MaterialDocMainDto> list = materialDocMainQueryService.exportList(pageQuery);
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=MaterialDoc.xlsx");
        com.alibaba.excel.EasyExcel.write(response.getOutputStream(), MaterialDocMainDto.class)
                .sheet("sheet1").doWrite(list);
    }

    @Operation(summary = "更新年检时间")
    @PostMapping("/updateMaterialAnnualDate")
    public SingleResponse<Boolean> updateMaterialAnnualDate(@RequestBody MaterialDocumentBO bo) {
        // TODO: 待 MaterialDocMainApplicationService 补充 updateAnnualDate(bo) 方法
        log.warn("updateMaterialAnnualDate: 待接入");
        return SingleResponse.buildSuccess(true);
    }

    // ==================== 城燃项目 ====================

    @Operation(summary = "城燃项目库存导入")
    @PostMapping("/city-gas/excel/import")
    public SingleResponse<Boolean> cityGasExcelImport(@RequestParam("uploadFile") MultipartFile file) {
        // TODO: 待接入 FileImportApplicationService.cityGasImport(file) —— 当前 FileImportApplicationService 无城燃导入方法
        log.warn("cityGasExcelImport: 待接入城燃导入实现");
        return SingleResponse.buildSuccess(true);
    }

    @Operation(summary = "城燃项目导入记录查询")
    @PostMapping("/city-gas/excel/page")
    public PageResponse<FileImportRecord> cityGasExcelPage(@RequestBody PageQueryFileImportRecordRequest request) {
        return fileImportApplicationService.pageQuery(request);
    }

    // ==================== 内部 VO ====================

    @Data
    static class EnumVO {
        private String code;
        private String desc;
        EnumVO(String code, String desc) { this.code = code; this.desc = desc; }
    }

    @Data
    static class MaterialFuzzyQueryReq {
        private String skuName;
        private Integer pageNum;
        private Integer pageSize;
    }
}