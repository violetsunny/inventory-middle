package com.inventory.middle.domain.service.material.builder;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandleChain;
import com.inventory.middle.client.dto.material.MaterialDataDTO;
import com.inventory.middle.client.dto.material.MaterialDocumentDTO;
import com.inventory.middle.client.dto.material.MaterialDocumentItemDTO;
import com.inventory.middle.client.dto.material.WarehouseDataDTO;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.model.bo.mq.sub.MaterialDocCancelMessage;
import com.inventory.middle.domain.service.MaterialDocCoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-08-30 14:49:39
 */

@Service("snMaterialDocCancelBuilderHandleChain")
public class SnMaterialDocCancelBuilderHandleChain implements IHandleChain<MaterialDocCancelMessage, MaterialDocumentDTO> {

    @Resource
    private MaterialDocCoreService materialDocCoreService;
    @Resource
    private SnowflakeGenerator snowflakeGenerator;

    @Override
    public boolean doProcess(HandleMessage<MaterialDocCancelMessage, MaterialDocumentDTO> msg) {

        MaterialDocCancelMessage message = msg.getT();

        MaterialDocumentBO documentBO = materialDocCoreService.getMaterialDocByOriginalNo(message.getMaterialDocNo());
        if (documentBO == null) {
            throw new BusinessException(ResponseCodeEnum.MDOC_NOT_EXIST, message.getMaterialDocNo());
        }
        Long materialDocId = snowflakeGenerator.next();
        MaterialDocumentDTO documentDTO = new MaterialDocumentDTO();
        // Exclude materialDocumentItems to avoid ClassCastException from generics erasure
        // (List<MaterialDocumentItemBO> would be blindly assigned to List<MaterialDocumentItemDTO>)
        BeanUtils.copyProperties(documentBO, documentDTO, "materialDocumentItems");
        documentDTO.setUniqueNo(materialDocId.toString());
        documentDTO.setMaterialDocCategory(message.getMaterialDocCategory());
        documentDTO.setOriginalNo(message.getMaterialDocNo());
        documentDTO.setToken(message.getToken());
        documentDTO.setOperator(message.getOperator());

        // Manually convert item list: BO → DTO with field-by-field copy of nested objects
        if (!CollectionUtils.isEmpty(documentBO.getMaterialDocumentItems())) {
            List<MaterialDocumentItemDTO> itemDTOs = new ArrayList<>();
            for (MaterialDocumentItemBO itemBO : documentBO.getMaterialDocumentItems()) {
                MaterialDocumentItemDTO itemDTO = new MaterialDocumentItemDTO();
                itemDTO.setMaterialCode(itemBO.getMaterialCode());
                // batchNo cleared for cancel reversal
                itemDTO.setBatchNo(null);
                // mapJournalData cleared for cancel reversal
                itemDTO.setMapJournalData(null);

                if (itemBO.getMaterialData() != null) {
                    MaterialDataDTO materialDataDTO = new MaterialDataDTO();
                    BeanUtils.copyProperties(itemBO.getMaterialData(), materialDataDTO);
                    // materialDocItemId cleared for cancel reversal (new line item)
                    materialDataDTO.setMaterialDocItemId(null);
                    itemDTO.setMaterialData(materialDataDTO);
                }

                if (itemBO.getWarehouseData() != null) {
                    WarehouseDataDTO warehouseDataDTO = new WarehouseDataDTO();
                    BeanUtils.copyProperties(itemBO.getWarehouseData(), warehouseDataDTO);
                    // adjustType cleared for cancel reversal
                    warehouseDataDTO.setAdjustType(null);
                    itemDTO.setWarehouseData(warehouseDataDTO);
                }

                if (itemBO.getQuantityData() != null) {
                    com.inventory.middle.client.dto.material.QuantityAndAmountDataDTO quantityDTO =
                            new com.inventory.middle.client.dto.material.QuantityAndAmountDataDTO();
                    BeanUtils.copyProperties(itemBO.getQuantityData(), quantityDTO);
                    itemDTO.setQuantityData(quantityDTO);
                }

                if (itemBO.getFinanceData() != null) {
                    com.inventory.middle.client.dto.material.FinancialDataDTO financeDTO =
                            new com.inventory.middle.client.dto.material.FinancialDataDTO();
                    BeanUtils.copyProperties(itemBO.getFinanceData(), financeDTO);
                    itemDTO.setFinanceData(financeDTO);
                }

                if (itemBO.getMaterialExtData() != null) {
                    com.inventory.middle.client.dto.material.MaterialExtDataDTO extDTO =
                            new com.inventory.middle.client.dto.material.MaterialExtDataDTO();
                    BeanUtils.copyProperties(itemBO.getMaterialExtData(), extDTO);
                    itemDTO.setMaterialExtData(extDTO);
                }

                itemDTOs.add(itemDTO);
            }
            documentDTO.setMaterialDocumentItems(itemDTOs);
        }

        msg.setE(documentDTO);
        return true;
    }


}
