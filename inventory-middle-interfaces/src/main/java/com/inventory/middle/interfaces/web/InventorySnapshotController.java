package com.inventory.middle.interfaces.web;

import com.inventory.middle.application.service.InventorySnapshotQueryService;
import com.inventory.middle.client.dto.InventorySnapshotDto;
import com.inventory.middle.client.dto.query.InventorySnapshotPageQuery;
import com.inventory.middle.interfaces.support.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;
import com.alibaba.excel.EasyExcel;
import java.util.List;



/**
 * 库存快照 Controller（从 inventory-center-bff 迁移）
 * 路径与 BFF 保持一致：/inventorySnapshot
 */
@Tag(name = "库存快照查询")
@CatchAndLog
@RestController
@RequestMapping("/inventorySnapshot")
@Slf4j
public class InventorySnapshotController {

    @Resource
    private InventorySnapshotQueryService inventorySnapshotQueryService;

    @Operation(summary = "分页查询库存信息")
    @PostMapping("/page/query")
    public PageResponse<InventorySnapshotDto> queryInventorySnapshotPage(@RequestBody InventorySnapshotPageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return inventorySnapshotQueryService.queryPage(pageQuery);
    }

    @Operation(summary = "分页查询库存信息-城燃")
    @PostMapping("/city-gas/page/query")
    public PageResponse<InventorySnapshotDto> queryCityGasPage(@RequestBody InventorySnapshotPageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return inventorySnapshotQueryService.pageListCityGas(pageQuery);
    }

    @Operation(summary = "库存详情查询")
    @PostMapping("/detail/query")
    public SingleResponse<InventorySnapshotDto> queryDetail(@RequestBody InventorySnapshotPageQuery pageQuery) {
        if (pageQuery.getId() != null) {
            return SingleResponse.buildSuccess(inventorySnapshotQueryService.findById(pageQuery.getId()));
        }
        return SingleResponse.buildSuccess(null);
    }

    @Operation(summary = "导出库存信息")
    @PostMapping("/export")
    public void export(@RequestBody InventorySnapshotPageQuery pageQuery, HttpServletResponse response) {
        try {
            pageQuery.setTenantId(UserContextHolder.getTenantId());
            List<InventorySnapshotDto> list = inventorySnapshotQueryService.exportList(pageQuery);
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=InventorySnapshot.xlsx");
            EasyExcel.write(response.getOutputStream(), InventorySnapshotDto.class)
                    .sheet("sheet1").doWrite(list);
        } catch (Exception e) {
            log.error("inventorySnapshot export failed", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try { response.getWriter().write("{\"code\":500,\"msg\":\"导出失败\"}"); } catch (java.io.IOException ignored) {}
        }
    }

    @Operation(summary = "根据批次号查询库存信息")
    @PostMapping("/queryByBatchNo")
    public PageResponse<InventorySnapshotDto> queryByBatchNo(@RequestBody InventorySnapshotPageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return inventorySnapshotQueryService.queryByBatchNo(pageQuery);
    }

    @Operation(summary = "查询实时库存")
    @PostMapping("/queryCurrentInventory")
    public PageResponse<InventorySnapshotDto> queryCurrentInventory(@RequestBody InventorySnapshotPageQuery pageQuery) {
        pageQuery.setTenantId(UserContextHolder.getTenantId());
        return inventorySnapshotQueryService.queryCurrentInventory(pageQuery);
    }
}