/**
 * llkang.com Inc.
 * Copyright (c) 2010-2023 All Rights Reserved.
 */
package com.inventory.middle.application.service.impl;

import com.google.common.collect.Lists;
import com.inventory.middle.application.service.MaterialDocQueryService;
import com.inventory.middle.client.dto.material.BusinessTypeMappingDto;
import com.inventory.middle.client.dto.material.MaterialBatchNoResDto;
import com.inventory.middle.client.dto.material.MaterialDocCategoryMappingDto;
import com.inventory.middle.client.dto.material.MaterialMappingDto;
import com.inventory.middle.client.dto.query.MaterialBatchNoQuery;
import com.inventory.middle.client.dto.query.MaterialMappingQuery;
import com.inventory.middle.domain.model.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.sequence.CommandVisitor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.kdla.framework.dto.EnumResponse;
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

    @Override
    public MaterialMappingDto queryMaterialTypeMapping(MaterialMappingQuery req) {
        Map<MaterialDocCategoryEnum, List<BusinessTypeEnum>> materialTypeEnumListMap = getMaterialTypeMapping(req);
        MaterialMappingDto materialMapping = new MaterialMappingDto();
        List<MaterialDocCategoryMappingDto> materialDocCategoryMappings = Lists.newArrayList();
        for (Map.Entry<MaterialDocCategoryEnum, List<BusinessTypeEnum>> entry : materialTypeEnumListMap.entrySet()) {
            MaterialDocCategoryMappingDto materialDocCategoryMapping = new MaterialDocCategoryMappingDto();

            EnumResponse materialType = EnumResponse.builder().code(String.valueOf(entry.getKey().getCode()))
                    .desc(entry.getKey().getDesc()).build();
            List<BusinessTypeMappingDto> businessTypes = Lists.newArrayList();

            if (!CollectionUtils.isEmpty(entry.getValue())) {
                for (BusinessTypeEnum businessTypeEnum : entry.getValue()) {
                    BusinessTypeMappingDto businessTypeMapping = new BusinessTypeMappingDto();

                    EnumResponse businessType =
                            EnumResponse.builder().code(businessTypeEnum.getCode()).desc(businessTypeEnum.getDesc()).build();
                    businessTypeMapping.setBusinessType(businessType);

                    EnumResponse adjustType = EnumResponse.builder().code(businessTypeEnum.getAdjustTypeEnum().getCode())
                            .desc(businessTypeEnum.getAdjustTypeEnum().getDesc()).build();
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

    private void fillMaterialRefInfo(MaterialAdjustTypeEnum adjustTypeEnum, BusinessTypeMappingDto businessTypeMapping) {
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
                    .map(e -> EnumResponse.builder().code(e.getCode().toString()).desc(e.getDesc()).build())
                    .collect(Collectors.toList()));
        } else {
            businessTypeMapping.getRefTypeList()
                    .add(EnumResponse.builder().code(refTypeEnum.getCode().toString()).desc(refTypeEnum.getDesc()).build());
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
    public MaterialBatchNoResDto queryMaterialBatchNo(MaterialBatchNoQuery query) {

        return null;
    }
}
