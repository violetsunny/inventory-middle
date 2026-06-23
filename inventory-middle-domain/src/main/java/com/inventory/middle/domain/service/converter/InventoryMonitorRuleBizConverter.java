package com.inventory.middle.domain.service.converter;

import com.inventory.middle.client.dto.monitory.FailedCreateMonitorRuleLineDTO;
import com.inventory.middle.client.dto.monitory.InventoryMonitorRuleLineInfoDTO;
import com.inventory.middle.client.dto.monitory.InventoryMonitorRuleLineResponse;
import com.inventory.middle.client.enums.BaseStatusEnum;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleBO;
import com.inventory.middle.domain.model.bo.monitor.InventoryMonitorRuleLineBO;
import com.inventory.middle.domain.model.bo.monitor.UpdateMonitorRuleLineReq;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InventoryMonitorRuleBizConverter {

    public static InventoryMonitorRuleLineBO convertToDeleteRuleBo(UpdateMonitorRuleLineReq request) {
        if (null == request || CollectionUtils.isEmpty(request.getDeleteList())) { return null; }
        InventoryMonitorRuleLineBO bo = new InventoryMonitorRuleLineBO();
        bo.setDeleted(BaseStatusEnum.YES.getCode());
        bo.setUpdateTime(new Date());
        bo.setUpdatorId(request.getOperatorId());
        return bo;
    }

    public static InventoryMonitorRuleLineBO convertToDeleteRuleLineBo(InventoryMonitorRuleBO monitorRuleBO) {
        InventoryMonitorRuleLineBO lineBO = new InventoryMonitorRuleLineBO();
        lineBO.setUpdateTime(new Date());
        lineBO.setUpdatorId(monitorRuleBO.getUpdatorId());
        lineBO.setDeleted(BaseStatusEnum.YES.getCode());
        lineBO.setMonitorRuleId(monitorRuleBO.getId());
        return lineBO;
    }

    public static ArrayList<InventoryMonitorRuleLineResponse> convertRuleLineBoToResponses(List<InventoryMonitorRuleLineBO> lineBOList) {
        ArrayList<InventoryMonitorRuleLineResponse> list = Lists.newArrayList();
        if (CollectionUtils.isEmpty(lineBOList)) { return list; }
        lineBOList.stream().filter(Objects::nonNull).forEach(e -> list.add(convertRuleLineBoToResponse(e)));
        return list;
    }

    public static InventoryMonitorRuleLineResponse convertRuleLineBoToResponse(InventoryMonitorRuleLineBO lineBO) {
        if (lineBO == null) { return null; }
        InventoryMonitorRuleLineResponse response = new InventoryMonitorRuleLineResponse();
        response.setId(lineBO.getId());
        response.setMonitorRuleId(lineBO.getMonitorRuleId());
        response.setMonitorObject(lineBO.getMonitorObject());
        response.setMonitorDimension(lineBO.getMonitorDimension());
        response.setMonitorCeil(lineBO.getMonitorCeil());
        response.setMonitorFloor(lineBO.getMonitorFloor());
        response.setUpdateTime(lineBO.getUpdateTime());
        response.setUpdatorId(lineBO.getUpdatorId());
        response.setCreateTime(lineBO.getCreateTime());
        response.setCreatorId(lineBO.getCreatorId());
        response.setDeleted(lineBO.getDeleted());
        return response;
    }

    public static InventoryMonitorRuleLineInfoDTO convertBoToInfoDTO(InventoryMonitorRuleLineBO bo) {
        if (bo == null) { return null; }
        InventoryMonitorRuleLineInfoDTO infoDTO = new InventoryMonitorRuleLineInfoDTO();
        infoDTO.setMonitorRuleId(bo.getMonitorRuleId());
        infoDTO.setMonitorDimension(bo.getMonitorDimension());
        infoDTO.setMonitorObject(bo.getMonitorObject());
        infoDTO.setMonitorCeil(bo.getMonitorCeil());
        infoDTO.setMonitorFloor(bo.getMonitorFloor());
        infoDTO.setOperatorId(bo.getUpdatorId());
        return infoDTO;
    }

    public static List<InventoryMonitorRuleLineInfoDTO> convertBoToInfoDtos(List<InventoryMonitorRuleLineBO> boList) {
        if (CollectionUtils.isEmpty(boList)) { return Lists.newArrayList(); }
        return boList.stream().filter(Objects::nonNull).map(InventoryMonitorRuleBizConverter::convertBoToInfoDTO).collect(Collectors.toList());
    }

    public static List<FailedCreateMonitorRuleLineDTO> buildFailedDtos(List<InventoryMonitorRuleLineInfoDTO> list, String failedReason) {
        List<FailedCreateMonitorRuleLineDTO> failedList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(list)) { return failedList; }
        for (InventoryMonitorRuleLineInfoDTO infoDTO : list) {
            FailedCreateMonitorRuleLineDTO failedDto = buildFailedDto(infoDTO);
            failedDto.setFailedReason(failedReason);
            failedList.add(failedDto);
        }
        return failedList;
    }

    public static <P extends InventoryMonitorRuleLineInfoDTO> FailedCreateMonitorRuleLineDTO buildFailedDto(P ruleLineInfoDTO) {
        FailedCreateMonitorRuleLineDTO failedDto = new FailedCreateMonitorRuleLineDTO();
        failedDto.setMonitorRuleId(ruleLineInfoDTO.getMonitorRuleId());
        failedDto.setMonitorCeil(ruleLineInfoDTO.getMonitorCeil());
        failedDto.setMonitorFloor(ruleLineInfoDTO.getMonitorFloor());
        failedDto.setMonitorObject(ruleLineInfoDTO.getMonitorObject());
        failedDto.setMonitorDimension(ruleLineInfoDTO.getMonitorDimension());
        failedDto.setOperatorId(ruleLineInfoDTO.getOperatorId());
        return failedDto;
    }
}
