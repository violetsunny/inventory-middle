package com.inventory.middle.application.service;

import com.inventory.middle.client.dto.material.CreateMaterialLogicalPlantRefReqDTO;
import com.inventory.middle.client.dto.sparepart.PageSparePartReqDTO;
import com.inventory.middle.client.dto.sparepart.PageSparePartResDTO;
import top.kdla.framework.dto.PageResponse;

import java.util.List;

public interface SparePartApplicationService {

    PageResponse<PageSparePartResDTO> pageList(PageSparePartReqDTO reqDTO);

    boolean sparePartSave(List<CreateMaterialLogicalPlantRefReqDTO> reqDTOList, String tenantId, String userId);
}
