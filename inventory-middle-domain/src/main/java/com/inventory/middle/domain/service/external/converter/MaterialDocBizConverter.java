package com.inventory.middle.domain.service.external.converter;

import com.inventory.middle.domain.model.bo.material.*;
import com.inventory.middle.client.dto.material.MaterialDocumentDTO;
import com.inventory.middle.client.dto.material.MaterialDocumentResDTO;
import com.inventory.middle.domain.service.external.dto.SkuBatchRequest;
import com.inventory.middle.domain.service.external.dto.SkuBatchResponse;
import org.mapstruct.Mapper;
import java.util.List;

/** 物料凭证 MapStruct 转换器 */
@Mapper(componentModel = "spring")
public interface MaterialDocBizConverter {

    MaterialDocumentBO copyMaterialDocumentIn(MaterialDocumentBO materialDocument);

    List<MaterialDocumentItemBO> copyMaterialDocumentItems(List<MaterialDocumentItemBO> items);

    MaterialDocumentItemBO copyMaterialDocumentItem(MaterialDocumentItemBO d);

    MaterialExtDataBO copyMaterialExtData(MaterialExtDataBO materialExtData);

    FinancialDataBO copyFinanceData(FinancialDataBO financeData);

    QuantityAndAmountDataBO copyQuantityData(QuantityAndAmountDataBO quantityData);

    WarehouseDataBO copyWarehouseData(WarehouseDataBO warehouseData);

    MaterialDataBO copyMaterialData(MaterialDataBO materialData);

    List<SkuBatchRequest> conversionSkuBatchRequest(List<SkuBatchResponse> responseList);

    MaterialDocumentDTO copyMaterialDocumentResDTO(MaterialDocumentResDTO resDTO);
}

