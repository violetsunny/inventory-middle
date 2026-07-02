package com.inventory.middle.infra.persistence.convertor;

import com.inventory.middle.domain.service.external.dto.SkuBatchResponse;
import com.inventory.middle.domain.service.external.dto.SkuResponse;
import com.inventory.middle.infra.persistence.entity.SkuBatchDo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductSkuBatchConvertor {

    SkuBatchResponse toSkuBatchResponse(SkuBatchDo doObj);

    SkuResponse toSkuResponse(SkuBatchDo doObj);
}
