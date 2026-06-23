package com.inventory.middle.application.plan.demand.convertor;

import com.inventory.middle.client.plan.demand.dto.*;
import com.inventory.middle.application.plan.demand.bo.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FacadeDemandBoardDetailConverter {

    FacadeDemandBoardDetailConverter INSTANCE = Mappers.getMapper(FacadeDemandBoardDetailConverter.class);

    DemandBoardDetailReqBO toReqBO(DemandBoardDetailReqDTO reqDTO);

    List<DemandBoardDetailResDTO> toResDTOList(List<DemandBoardDetailResBO> resBOList);

    List<SingleDemandResultDTO> toSingleDTOList(List<SingleDemandResultBO> singleBOList);

    MaterialReqBO toMaterialReqBO(MaterialReqDTO reqDTO);

    List<MaterialResultDTO> toMaterialDTOList(List<MaterialResultBO> resultBOList);

    MaterialResultDTO toMaterialDTO(MaterialResultBO resultBO);
}
