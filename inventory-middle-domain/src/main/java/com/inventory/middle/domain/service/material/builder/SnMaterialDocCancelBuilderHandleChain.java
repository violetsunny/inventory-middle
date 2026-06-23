package com.inventory.middle.domain.service.material.builder;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import top.kdla.framework.dto.SingleResponse;
import com.inventory.middle.domain.service.external.converter.MaterialDocBizConverter;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandleChain;
import com.inventory.middle.client.dto.material.GetMaterialDocumentReqDTO;
import com.inventory.middle.client.dto.material.MaterialDocumentDTO;
import com.inventory.middle.client.dto.material.MaterialDocumentResDTO;
import com.inventory.middle.client.service.MaterialDocService;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.model.bo.mq.sub.MaterialDocCancelMessage;
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
    private MaterialDocService materialDocService;
    @Resource
    private SnowflakeGenerator snowflakeGenerator;
    @Resource
    private MaterialDocBizConverter materialDocBizConverter;

    @Override
    public boolean doProcess(HandleMessage<MaterialDocCancelMessage, MaterialDocumentDTO> msg) {

        MaterialDocCancelMessage message = msg.getT();

        GetMaterialDocumentReqDTO reqDTO  = new GetMaterialDocumentReqDTO();
        reqDTO.setTenantId(message.getTenantId());
        reqDTO.setOriginalNo(message.getMaterialDocNo());
        SingleResponse<MaterialDocumentResDTO> documentResult = materialDocService.getMaterialDocument(reqDTO);
        if (null == documentResult){
            throw new BusinessException(ResponseCodeEnum.FAILED.getCode(), ResponseCodeEnum.FAILED.getDesc());
        }
        if (!documentResult.isSuccess()){
            throw new BusinessException(documentResult.getCode(), documentResult.getMessage());
        }
        if (null == documentResult.getData()){
            throw new BusinessException(ResponseCodeEnum.MDOC_NOT_EXIST, message.getMaterialDocNo());
        }
        Long materialDocId = snowflakeGenerator.next();
        MaterialDocumentDTO documentDTO = materialDocBizConverter.copyMaterialDocumentResDTO(documentResult.getData());
        documentDTO.setUniqueNo(materialDocId.toString());
        documentDTO.setMaterialDocCategory(message.getMaterialDocCategory());
        documentDTO.setOriginalNo(message.getMaterialDocNo());
        documentDTO.setToken(message.getToken());
        documentDTO.setOperator(message.getOperator());

        documentDTO.getMaterialDocumentItems().forEach(e -> {
            e.setBatchNo(null);
            e.setMapJournalData(null);
            e.getMaterialData().setMaterialDocItemId(null);
            e.getWarehouseData().setAdjustType(null);
        });

        msg.setE(documentDTO);
        return true;
    }


}
