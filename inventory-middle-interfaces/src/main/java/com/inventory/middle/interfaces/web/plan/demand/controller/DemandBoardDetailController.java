package com.inventory.middle.interfaces.web.plan.demand.controller;

import com.alibaba.fastjson.JSON;
import com.inventory.middle.client.plan.demand.dto.DemandBoardDetailReqDTO;
import com.inventory.middle.client.plan.demand.dto.DemandBoardDetailResDTO;
import com.inventory.middle.client.plan.demand.dto.MaterialReqDTO;
import com.inventory.middle.client.plan.demand.dto.MaterialResDTO;
import com.inventory.middle.client.plan.demand.dto.SingleDemandResultDTO;
import com.inventory.middle.client.plan.demand.service.DemandBoardDetailRpcService;
import com.inventory.middle.interfaces.support.UserContextHolder;
import com.inventory.middle.interfaces.web.plan.demand.dto.DemandBoardDetailRequestDTO;
import com.inventory.middle.interfaces.web.plan.demand.dto.MaterialRequestDTO;
import com.inventory.middle.interfaces.web.plan.demand.vo.DemandBoardDetailResponseVO;
import com.inventory.middle.interfaces.web.plan.demand.vo.DemandBoardDetailResultVO;
import com.inventory.middle.interfaces.web.plan.demand.vo.MaterialResultVO;
import com.inventory.middle.interfaces.web.plan.demand.vo.MaterialsResVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.PagedSingleResponse;
import top.kdla.framework.dto.SingleResponse;
import top.kdla.framework.log.catchlog.CatchAndLog;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "独立需求看板API")
@CatchAndLog
@Slf4j
@RestController
@RequestMapping("/demandBoard")
public class DemandBoardDetailController {

    @Autowired
    private DemandBoardDetailRpcService demandBoardDetailRpcService;

    @Operation(summary = "独立需求看板详情")
    @PostMapping("/detail")
    public PagedSingleResponse<DemandBoardDetailResponseVO> selectDemandBoardDetailByPage(
            @RequestBody DemandBoardDetailRequestDTO requestDTO) {

        DemandBoardDetailReqDTO reqDTO = new DemandBoardDetailReqDTO();
        reqDTO.setLogicalPlantNo(requestDTO.getLogicalPlantNo());
        reqDTO.setMaterialCodeList(requestDTO.getMaterialCodeList());
        if (CollectionUtils.isNotEmpty(requestDTO.getExternalMaterialCodeList())) {
            reqDTO.setMaterialCodeList(requestDTO.getExternalMaterialCodeList());
        }
        reqDTO.setBeginTime(requestDTO.getBeginTime());
        reqDTO.setEndTime(requestDTO.getEndTime());
        reqDTO.setTenantId(UserContextHolder.getTenantId());

        PageResponse<DemandBoardDetailResDTO> result = demandBoardDetailRpcService.selectDemandBoardDetailByPage(
                reqDTO, requestDTO.getPageNum(), requestDTO.getPageSize());

        List<DemandBoardDetailResDTO> resDTOS = new ArrayList<>(result.getData());
        List<DemandBoardDetailResponseVO> vos = new ArrayList<>();

        if (CollectionUtils.isEmpty(resDTOS)) {
            return PagedSingleResponse.success(requestDTO.getPageNum(), requestDTO.getPageSize(), 0L, vos);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (DemandBoardDetailResDTO dto : resDTOS) {
            DemandBoardDetailResponseVO vo = new DemandBoardDetailResponseVO();
            vo.setLogicalPlantNo(dto.getLogicalPlantNo());
            vo.setMaterialCode(dto.getMaterialCode());
            vo.setExternalMaterialCode(dto.getExternalMaterialCode());
            vo.setMaterialDesc(dto.getMaterialDesc());
            vo.setMaterialUnit(dto.getMaterialUnit());
            
            Map<String, DemandBoardDetailResultVO> map = new HashMap<>();
            if (dto.getDemandList() != null) {
                for (SingleDemandResultDTO resultDTO : dto.getDemandList()) {
                    String key = simpleDateFormat.format(resultDTO.getPlanDate()) + "-" + resultDTO.getType();
                    DemandBoardDetailResultVO resultVO = new DemandBoardDetailResultVO();
                    resultVO.setAmount(BigDecimal.valueOf(resultDTO.getAmount()));
                    if (resultDTO.getExtInfo() != null) {
                        resultVO.setExtInfo(JSON.parseObject(resultDTO.getExtInfo()));
                    }
                    map.put(key, resultVO);
                }
            }
            vo.setPeriodDemandList(map);
            vos.add(vo);
        }

        return PagedSingleResponse.success(result.getPageNum(), result.getPageSize(), result.getTotalCount(), vos);
    }

    @Operation(summary = "模糊查询查询物料")
    @PostMapping("/selectMaterialsByName")
    public SingleResponse<MaterialsResVO> selectMaterialsByName(
            @RequestBody MaterialRequestDTO requestDTO) {
            
        MaterialReqDTO reqDTO = new MaterialReqDTO();
        reqDTO.setTenantId(UserContextHolder.getTenantId());
        reqDTO.setMaterialDesc(requestDTO.getMaterialDesc());
        reqDTO.setLogicalPlantNo(requestDTO.getLogicalPlantNo());

        SingleResponse<MaterialResDTO> result = demandBoardDetailRpcService.selectMaterialsByName(reqDTO);
        
        MaterialsResVO resVO = new MaterialsResVO();
        if (result.getData() != null && result.getData().getMaterialList() != null) {
            List<MaterialResultVO> list = result.getData().getMaterialList().stream().map(dto -> {
                MaterialResultVO vo = new MaterialResultVO();
                vo.setMaterialCode(dto.getMaterialCode());
                vo.setMaterialDesc(dto.getMaterialDesc());
                return vo;
            }).collect(Collectors.toList());
            resVO.setMaterialList(list);
        }
        
        return SingleResponse.buildSuccess(resVO);
    }
}
