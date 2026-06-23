package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.application.plan.demand.bo.DemandBoardDetailReqBO;
import com.inventory.middle.application.plan.demand.bo.DemandBoardDetailResBO;
import com.inventory.middle.application.plan.demand.bo.MaterialReqBO;
import com.inventory.middle.application.plan.demand.bo.MaterialResultBO;
import com.inventory.middle.application.plan.demand.bo.SingleDemandResultBO;
import com.inventory.middle.client.plan.demand.dto.DemandBoardDetailReqDTO;
import com.inventory.middle.client.plan.demand.dto.DemandBoardDetailResDTO;
import com.inventory.middle.client.plan.demand.dto.MaterialReqDTO;
import com.inventory.middle.client.plan.demand.dto.MaterialResultDTO;
import com.inventory.middle.infra.plan.persistence.condition.demand.MaterialReqCondition;
import com.inventory.middle.infra.plan.persistence.result.DemandDetailResult;
import com.inventory.middle.infra.plan.persistence.result.MaterialResult;
import com.inventory.middle.infra.plan.persistence.result.SingleDemandResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DemandBoardDetailConverter {

    DemandBoardDetailConverter INSTANCE = Mappers.getMapper(DemandBoardDetailConverter.class);

    DemandBoardDetailReqBO toReqBO(DemandBoardDetailReqDTO reqDTO);

    List<DemandBoardDetailResDTO> toResDTOList(List<DemandBoardDetailResBO> resBOList);

    MaterialReqCondition toMaterialReqCondition(MaterialReqBO reqBO);

    List<DemandBoardDetailResBO> toResBOList(List<DemandDetailResult> resultList);

    SingleDemandResultBO toSingleBO(SingleDemandResult result);

    List<MaterialResultBO> toMaterialBOList(List<MaterialResult> resultList);

    MaterialResultBO toMaterialBO(MaterialResult result);
    
    MaterialReqBO toMaterialReqBO(MaterialReqDTO reqDTO);
    
    List<MaterialResultDTO> toMaterialDTOList(List<MaterialResultBO> boList);

}
