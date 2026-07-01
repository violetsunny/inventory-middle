package com.inventory.middle.interfaces.web.sparepart;

import com.inventory.middle.application.service.SparePartApplicationService;
import com.inventory.middle.client.dto.material.CreateMaterialLogicalPlantRefReqDTO;
import com.inventory.middle.client.dto.sparepart.PageSparePartReqDTO;
import com.inventory.middle.client.dto.sparepart.PageSparePartResDTO;
import com.inventory.middle.interfaces.support.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import javax.annotation.Resource;
import java.util.List;

@Tag(name = "备品备件")
@CatchAndLog
@Slf4j
@RestController
@RequestMapping("/spare_part")
public class SparePartController {

    @Resource
    private SparePartApplicationService sparePartApplicationService;

    @Operation(summary = "分页查询备品备件入库信息")
    @PostMapping("/page/query")
    public PageResponse<PageSparePartResDTO> queryInventorySnapshotPage(@RequestBody PageSparePartReqDTO reqDTO) {
        reqDTO.setTenantId(UserContextHolder.getTenantId());
        return sparePartApplicationService.pageList(reqDTO);
    }

    @Operation(summary = "备品备件关联逻辑仓库")
    @PostMapping("/sku/save")
    public SingleResponse<Boolean> sparePartSave(@RequestBody List<CreateMaterialLogicalPlantRefReqDTO> reqDTOList) {
        return SingleResponse.of(sparePartApplicationService.sparePartSave(
                reqDTOList, UserContextHolder.getTenantId(), UserContextHolder.getUserId()));
    }
}
