/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package com.inventory.middle.application.service.impl;

import com.google.common.collect.Lists;
import com.inventory.middle.application.service.MaterialDocQueryService;
import com.inventory.middle.client.dto.material.BusinessTypeMappingDTO;
import com.inventory.middle.client.dto.material.MaterialBatchNoDTO;
import com.inventory.middle.client.dto.material.QueryMaterialBatchNoResDTO;
import com.inventory.middle.client.dto.material.MaterialDocCategoryMappingDTO;
import com.inventory.middle.client.dto.material.MaterialMappingDTO;
import com.inventory.middle.client.dto.query.MaterialBatchNoQuery;
import com.inventory.middle.client.dto.query.MaterialMappingQuery;
import com.inventory.middle.domain.model.entity.InventorySnapshot;
import com.inventory.middle.domain.model.enums.*;
import com.inventory.middle.domain.repository.InventorySnapshotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.inventory.middle.client.dto.material.EnumMappingDTO;
import top.kdla.framework.validator.CommonValidator;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kanglele
 * @version $Id: MaterialDocQueryServiceImpl, v 0.1 2023/3/13 16:55 kanglele Exp $
 */
@Service
@Slf4j
public class MaterialDocQueryServiceImpl implements MaterialDocQueryService {

    @Resource
    private CommonValidator commonValidator;

    @Resource
    private InventorySnapshotRepository inventorySnapshotRepository;

    @Override
    public MaterialMappingDTO queryMaterialTypeMapping(MaterialMappingQuery req) {
        Map<MaterialDocCategoryEnum, List<BusinessTypeEnum>> materialTypeEnumListMap = getMaterialTypeMapping(req);
        MaterialMappingDTO materialMapping = new MaterialMappingDTO();
        List<MaterialDocCategoryMappingDTO> materialDocCategoryMappings = Lists.newArrayList();
        for (Map.Entry<MaterialDocCategoryEnum, List<BusinessTypeEnum>> entry : materialTypeEnumListMap.entrySet()) {
            MaterialDocCategoryMappingDTO materialDocCategoryMapping = new MaterialDocCategoryMappingDTO();

            EnumMappingDTO materialType = EnumMappingDTO.builder().key(String.valueOf(entry.getKey().getCode()))
                    .value(entry.getKey().getDesc()).build();
            List<BusinessTypeMappingDTO> businessTypes = Lists.newArrayList();

            if (!CollectionUtils.isEmpty(entry.getValue())) {
                for (BusinessTypeEnum businessTypeEnum : entry.getValue()) {
                    BusinessTypeMappingDTO businessTypeMapping = new BusinessTypeMappingDTO();

                    EnumMappingDTO businessType =
                            EnumMappingDTO.builder().key(businessTypeEnum.getCode()).value(businessTypeEnum.getDesc()).build();
                    businessTypeMapping.setBusinessType(businessType);

                    EnumMappingDTO adjustType = EnumMappingDTO.builder().key(businessTypeEnum.getAdjustTypeEnum().getCode())
                            .value(businessTypeEnum.getAdjustTypeEnum().getDesc()).build();
                    businessTypeMapping.setAdjustType(adjustType);

                    businessTypeMapping.setIo(businessTypeEnum.getIo());
                    if (MaterialDocCategoryEnum.CANCEL.getCode().equals(entry.getKey().getCode())) {
                        businessTypeMapping.setUpdateData(Boolean.FALSE);
                    }

                    fillMaterialRefInfo(businessTypeEnum.getAdjustTypeEnum(), businessTypeMapping);

                    businessTypes.add(businessTypeMapping);
                }
            }

            materialDocCategoryMapping.setMaterialDocCategory(materialType);
            materialDocCategoryMapping.setBusinessTypes(businessTypes);

            materialDocCategoryMappings.add(materialDocCategoryMapping);
        }
        materialMapping.setMappings(materialDocCategoryMappings);
        return materialMapping;
    }

    private void fillMaterialRefInfo(MaterialAdjustTypeEnum adjustTypeEnum, BusinessTypeMappingDTO businessTypeMapping) {
        if (null == adjustTypeEnum) {
            return;
        }
        MaterialDocRefOrderTypeEnum refOrderTypeEnum = adjustTypeEnum.getMaterialDocRefOrderTypeEnum();
        if (null != refOrderTypeEnum) {
            businessTypeMapping.setOriginalNoType(refOrderTypeEnum.getCode());
        }
        MaterialDocRefTypeEnum refTypeEnum = adjustTypeEnum.getMaterialDocRefTypeEnum();
        if (null == refTypeEnum) {
            return;
        }
        if (MaterialDocRefTypeEnum.ALL.getCode().equals(refTypeEnum.getCode())) {
            businessTypeMapping.setRefTypeList(Arrays.stream(MaterialDocRefTypeEnum.values())
                    .filter(e -> !MaterialDocRefTypeEnum.ALL.getCode().equals(e.getCode()))
                    .map(e -> EnumMappingDTO.builder().key(e.getCode().toString()).value(e.getDesc()).build())
                    .collect(Collectors.toList()));
        } else {
            businessTypeMapping.getRefTypeList()
                    .add(EnumMappingDTO.builder().key(refTypeEnum.getCode().toString()).value(refTypeEnum.getDesc()).build());
        }
    }

    private Map<MaterialDocCategoryEnum, List<BusinessTypeEnum>> getMaterialTypeMapping(MaterialMappingQuery getMaterialMapping) {
        commonValidator.defaultValidate(getMaterialMapping);
        MaterialDocSourceEnum materialDocSourceEnum = MaterialDocSourceEnum.enumByCode(getMaterialMapping.getMaterialDocSource());
        Map<MaterialDocCategoryEnum, List<BusinessTypeEnum>> materialDocCategoryMapping = BusinessTypeEnum.getMaterialTypeMapping(materialDocSourceEnum);
        materialDocCategoryMapping.put(MaterialDocCategoryEnum.CANCEL, Lists.newArrayList());
        return materialDocCategoryMapping;
    }

    @Override
    public QueryMaterialBatchNoResDTO queryMaterialBatchNo(MaterialBatchNoQuery query) {
        List<InventorySnapshot> snapshots = inventorySnapshotRepository.queryBatchNoList(
                query.getMaterialCode(), query.getLogicalPlantNo(), query.getStorageLocationNo(), query.getTenantId());
        QueryMaterialBatchNoResDTO resDTO = new QueryMaterialBatchNoResDTO();
        resDTO.setMaterialCode(query.getMaterialCode());
        resDTO.setLogicalPlantNo(query.getLogicalPlantNo());
        resDTO.setStorageLocationNo(query.getStorageLocationNo());
        List<MaterialBatchNoDTO> batchNos = snapshots.stream()
                .filter(s -> s.getBatchNo() != null)
                .map(s -> {
                    MaterialBatchNoDTO dto = new MaterialBatchNoDTO();
                    dto.setMaterialCode(s.getMaterialCode());
                    dto.setBatchNo(s.getBatchNo());
                    dto.setBatchPrice(s.getBatchPrice());
                    return dto;
                })
                .collect(Collectors.toList());
        resDTO.setMaterialBatchNos(batchNos);
        return resDTO;
    }
}
