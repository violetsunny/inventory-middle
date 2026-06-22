package com.inventory.middle.interfaces.web;

import com.inventory.middle.application.service.InventoryMonitorRuleApplicationService;
import com.inventory.middle.application.service.InventoryMonitorRuleLineApplicationService;
import com.inventory.middle.application.service.InventoryMonitorRuleLineQueryService;
import com.inventory.middle.application.service.InventoryMonitorRuleQueryService;
import com.inventory.middle.client.dto.InventoryMonitorRuleDto;
import com.inventory.middle.client.dto.InventoryMonitorRuleLineDto;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleCommand;
import com.inventory.middle.client.dto.command.InventoryMonitorRuleLineCommand;
import com.inventory.middle.client.dto.query.InventoryMonitorRuleLinePageQuery;
import com.inventory.middle.client.dto.query.InventoryMonitorRulePageQuery;
import com.inventory.middle.interfaces.support.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import top.kdla.framework.log.catchlog.CatchAndLog;
import com.inventory.middle.client.enums.monitor.MonitorRuleTypeEnum;
import com.inventory.middle.client.enums.monitor.MonitorRuleSendModeEnum;
import com.inventory.middle.client.enums.monitor.MonitorRuleDimensionEnum;
import com.inventory.middle.domain.model.enums.MonitorRuleEnableStatusEnum;
import java.util.LinkedHashMap;
import java.util.Map;



/**
 * 库存预警规则 Controller（从 inventory-center-bff 迁移）
 * 路径与 BFF 保持一致：/monitorRule
 */
@Tag(name = "库存预警规则管理")
@CatchAndLog
@RestController
@RequestMapping("/monitorRule")
@Slf4j
public class InventoryMonitorRuleController {

    @Resource
    private InventoryMonitorRuleApplicationService monitorRuleApplicationService;
    @Resource
    private InventoryMonitorRuleQueryService monitorRuleQueryService;
    @Resource
    private InventoryMonitorRuleLineApplicationService monitorRuleLineApplicationService;
    @Resource
    private InventoryMonitorRuleLineQueryService monitorRuleLineQueryService;

    // ==================== 规则 CRUD ====================

    @Operation(summary = "分页查询库存预警信息")
    @PostMapping("/page/query")
    public PageResponse<InventoryMonitorRuleDto> pageQueryMonitorRule(@RequestBody InventoryMonitorRulePageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return monitorRuleQueryService.queryPage(pageQuery);
    }

    @Operation(summary = "库存预警规则创建")
    @PostMapping("/create")
    public SingleResponse<Boolean> monitorRuleCreate(@RequestBody InventoryMonitorRuleCommand command) {
        command.setTenantId(UserContextHolder.getTenantId());
        command.setCreatorId(UserContextHolder.getUserId());
        return SingleResponse.buildSuccess(monitorRuleApplicationService.add(command));
    }

    @Operation(summary = "库存预警规则更新")
    @PostMapping("/update")
    public SingleResponse<Boolean> monitorRuleUpdate(@RequestBody InventoryMonitorRuleCommand command) {
        command.setUpdatorId(UserContextHolder.getUserId());
        return SingleResponse.buildSuccess(monitorRuleApplicationService.update(command));
    }

    @Operation(summary = "库存预警规则删除")
    @PostMapping("/delete")
    public SingleResponse<Boolean> monitorRuleDelete(@RequestBody List<Long> ids) {
        return SingleResponse.buildSuccess(monitorRuleApplicationService.deleteBatch(ids));
    }

    // ==================== 枚举查询 ====================

    @Operation(summary = "预警规则类型枚举")
    @GetMapping("/type/query")
    public SingleResponse<List<EnumVO>> queryMonitorRuleType() {
        List<EnumVO> list = Arrays.stream(MonitorRuleTypeEnum.values())
                .map(e -> new EnumVO(e.name(), e.getDesc()))
                .collect(Collectors.toList());
        return SingleResponse.buildSuccess(list);
    }

    @Operation(summary = "预警发送方式枚举")
    @GetMapping("/sendMode/query")
    public SingleResponse<List<EnumVO>> queryMonitorRuleSendMode() {
        List<EnumVO> list = Arrays.stream(MonitorRuleSendModeEnum.values())
                .map(e -> new EnumVO(e.name(), e.getDesc()))
                .collect(Collectors.toList());
        return SingleResponse.buildSuccess(list);
    }

    @Operation(summary = "预警维度枚举")
    @GetMapping("/dimension/query")
    public SingleResponse<List<EnumVO>> queryMonitorRuleDimension() {
        List<EnumVO> list = Arrays.stream(MonitorRuleDimensionEnum.values())
                .map(e -> new EnumVO(e.name(), e.getDesc()))
                .collect(Collectors.toList());
        return SingleResponse.buildSuccess(list);
    }

    @Operation(summary = "预警启用状态枚举")
    @GetMapping("/enableStatus/query")
    public SingleResponse<List<EnumVO>> queryMonitorEnableStatus() {
        List<EnumVO> list = Arrays.stream(MonitorRuleEnableStatusEnum.values())
                .map(e -> new EnumVO(e.name(), e.getDesc()))
                .collect(Collectors.toList());
        return SingleResponse.buildSuccess(list);
    }

    // ==================== 规则明细 ====================

    @Operation(summary = "库存预警规则和明细更新")
    @PostMapping("/ruleAndLine/update")
    public SingleResponse<Boolean> monitorRuleAndLineUpdate(@RequestBody RuleAndLineUpdateReq req) {
        if (req.getRule() != null) {
            req.getRule().setUpdatorId(UserContextHolder.getUserId());
        }
        java.util.List<com.inventory.middle.client.dto.command.InventoryMonitorRuleLineCommand> lines =
                req.getLines() != null ? req.getLines() : java.util.Collections.emptyList();
        return SingleResponse.buildSuccess(monitorRuleApplicationService.updateWithLines(req.getRule(), lines));
    }

    @Operation(summary = "库存预警规则明细保存更新")
    @PostMapping("/ruleLine/save")
    public SingleResponse<Boolean> monitorRuleLineSave(@RequestBody InventoryMonitorRuleLineCommand command) {
        String userId = UserContextHolder.getUserId();
        command.setUpdatorId(userId != null && !userId.isEmpty() ? Long.valueOf(userId) : null);
        return SingleResponse.buildSuccess(monitorRuleLineApplicationService.update(command));
    }

    @Operation(summary = "库存预警规则明细分页查询")
    @PostMapping("/ruleLine/page")
    public PageResponse<InventoryMonitorRuleLineDto> queryMonitorRuleLine(@RequestBody InventoryMonitorRuleLinePageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return monitorRuleLineQueryService.queryPage(pageQuery);
    }

    // ==================== Excel 导入导出 ====================

    @Operation(summary = "上传导入预警规则明细（物料）")
    @PostMapping("/ruleLine/import")
    public SingleResponse<Object> excelImport(
            @RequestParam("monitorRuleId") Long monitorRuleId,
            @RequestParam("monitorType") String monitorType,
            @RequestParam("uploadFile") MultipartFile file) {
        // TODO: 待 InventoryMonitorRuleLineApplicationService 补充 importByExcel 方法（阶段三 R11）
        log.warn("monitorRule/ruleLine/import: 待接入 Excel 导入");
        return SingleResponse.buildFailure("NOT_IMPLEMENTED", "Excel 导入功能暂未开放");
    }

    @Operation(summary = "下载导入结果详情 Excel")
    @GetMapping("/ruleLine/import/result")
    public SingleResponse<Object> importResult(@RequestParam("detailInfoKey") String detailInfoKey) {
        // TODO: 待 InventoryMonitorRuleLineQueryService 补充 getImportResult(detailInfoKey) 方法（阶段三 R11）
        log.warn("monitorRule/ruleLine/import/result: 待接入");
        return SingleResponse.buildFailure("NOT_IMPLEMENTED", "导入结果查询功能暂未开放");
    }

    @Operation(summary = "获取导入模板 Excel")
    @GetMapping("/ruleLine/template")
    public SingleResponse<Object> templateExport() {
        // TODO: 待实现模板导出（EasyExcel 写入 ImportMonitorRuleLineExcelBO 模板）（阶段三 R11）
        log.warn("monitorRule/ruleLine/template: 待接入模板导出");
        return SingleResponse.buildFailure("NOT_IMPLEMENTED", "模板下载功能暂未开放");
    }

    // ==================== 内部 VO ====================

    @Data
    static class RuleAndLineUpdateReq {
        private com.inventory.middle.client.dto.command.InventoryMonitorRuleCommand rule;
        private java.util.List<com.inventory.middle.client.dto.command.InventoryMonitorRuleLineCommand> lines;
    }

        @Data
    static class EnumVO {
        private String code;
        private String desc;
        EnumVO(String code, String desc) { this.code = code; this.desc = desc; }
    }
}