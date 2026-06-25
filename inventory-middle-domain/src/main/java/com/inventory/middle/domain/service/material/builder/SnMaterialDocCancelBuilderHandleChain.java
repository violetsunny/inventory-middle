package com.inventory.middle.domain.service.material.builder;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.inventory.middle.domain.service.external.converter.MaterialDocBizConverter;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandleChain;
import com.inventory.middle.client.dto.material.MaterialDocumentDTO;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.mq.sub.MaterialDocCancelMessage;
import com.inventory.middle.domain.service.MaterialDocCoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
        BeanUtils.copyProperties(documentBO, documentDTO);
        documentDTO.setUniqueNo(materialDocId.toString());
        documentDTO.setMaterialDocCategory(message.getMaterialDocCategory());
        documentDTO.setOriginalNo(message.getMaterialDocNo());
        documentDTO.setToken(message.getToken());
        documentDTO.setOperator(message.getOperator());

        if (documentDTO.getMaterialDocumentItems() != null) {
            documentDTO.getMaterialDocumentItems().forEach(e -> {
                e.setBatchNo(null);
                e.setMapJournalData(null);
                e.getMaterialData().setMaterialDocItemId(null);
                e.getWarehouseData().setAdjustType(null);
            });
        }

        msg.setE(documentDTO);
        return true;
    }


}
