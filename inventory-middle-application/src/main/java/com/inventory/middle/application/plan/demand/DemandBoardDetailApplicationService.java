package com.inventory.middle.application.plan.demand;

import com.inventory.middle.application.service.InventoryMaterialApplicationService;
import com.inventory.middle.client.material.dto.InventoryMaterialDTO;
import com.inventory.middle.client.material.dto.request.ListMaterialCodeRequest;
import com.inventory.middle.client.plan.demand.dto.DemandBoardDetailReqDTO;
import com.inventory.middle.client.plan.demand.dto.DemandBoardDetailResDTO;
import com.inventory.middle.client.plan.demand.dto.MaterialReqDTO;
import com.inventory.middle.client.plan.demand.dto.MaterialResDTO;
import com.inventory.middle.client.plan.demand.service.DemandBoardDetailRpcService;
import com.inventory.middle.application.plan.demand.bo.DemandBoardDetailReqBO;
import com.inventory.middle.application.plan.demand.bo.DemandBoardDetailResBO;
import com.inventory.middle.application.plan.demand.bo.MaterialReqBO;
import com.inventory.middle.application.plan.demand.bo.MaterialResultBO;
import com.inventory.middle.application.plan.demand.service.DemandBoardDetailService;
import com.inventory.middle.application.plan.demand.convertor.DemandBoardDetailConverter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.dto.MultiResponse;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DemandBoardDetailApplicationService implements DemandBoardDetailRpcService {

    @Autowired
    private DemandBoardDetailService demandBoardDetailService;

    @Autowired
    private InventoryMaterialApplicationService inventoryMaterialApplicationService;

    @Override
    public PageResponse<DemandBoardDetailResDTO> selectDemandBoardDetailByPage(DemandBoardDetailReqDTO reqDTO, int pageNum, int pageSize) {
        DemandBoardDetailReqBO reqBO = DemandBoardDetailConverter.INSTANCE.toReqBO(reqDTO);
        PageResponse<DemandBoardDetailResBO> boPagedSingleResponse = demandBoardDetailService.
                selectDemandBoardDetailByPage(reqBO, pageNum, pageSize);
        List<DemandBoardDetailResBO> boList = (List<DemandBoardDetailResBO>) boPagedSingleResponse.getData();
        List<DemandBoardDetailResDTO> dtoList = DemandBoardDetailConverter.INSTANCE.toResDTOList(boList);

        if (CollectionUtils.isNotEmpty(dtoList)) {
            List<String> materialCodeList = dtoList.stream()
                    .map(DemandBoardDetailResDTO::getMaterialCode)
                    .distinct()
                    .collect(Collectors.toList());

            ListMaterialCodeRequest request = new ListMaterialCodeRequest();
            request.setTenantId(reqDTO.getTenantId());
            request.setMaterialCodeList(materialCodeList);
            MultiResponse<InventoryMaterialDTO> materialResponse = inventoryMaterialApplicationService.listByMaterialCodeList(request);
            if (materialResponse != null && materialResponse.isSuccess() && CollectionUtils.isNotEmpty(materialResponse.getData())) {
                Map<String, InventoryMaterialDTO> materialMap = materialResponse.getData().stream()
                        .collect(Collectors.toMap(InventoryMaterialDTO::getMaterialCode, Function.identity(), (v1, v2) -> v1));
                
                for (DemandBoardDetailResDTO dto : dtoList) {
                    InventoryMaterialDTO materialDTO = materialMap.get(dto.getMaterialCode());
                    if (materialDTO != null) {
                        dto.setExternalMaterialCode(materialDTO.getOutMaterialCode());
                        dto.setMaterialDesc(materialDTO.getMaterialName());
                        if (materialDTO.getUnit() != null) {
                            dto.setMaterialUnit(materialDTO.getUnit().getUnitName());
                        }
                    }
                }
            }
        }

        return PageResponse.of(dtoList, boPagedSingleResponse.getTotalCount(), boPagedSingleResponse.getPageSize(), boPagedSingleResponse.getPageNum());
    }

    @Override
    public SingleResponse<MaterialResDTO> selectMaterialsByName(MaterialReqDTO reqDTO) {
        MaterialReqBO reqBO = DemandBoardDetailConverter.INSTANCE.toMaterialReqBO(reqDTO);
        MaterialResDTO materialResDTO = new MaterialResDTO();
        List<MaterialResultBO> boList = demandBoardDetailService.selectMaterialsByName(reqBO);
        materialResDTO.setMaterialList(DemandBoardDetailConverter.INSTANCE.toMaterialDTOList(boList));
        return SingleResponse.buildSuccess(materialResDTO);
    }
}
