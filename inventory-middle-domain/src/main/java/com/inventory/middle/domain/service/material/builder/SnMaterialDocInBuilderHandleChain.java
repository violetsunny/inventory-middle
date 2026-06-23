package com.inventory.middle.domain.service.material.builder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.inventory.middle.domain.service.external.dto.SkuResponse;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandleChain;
import com.inventory.middle.domain.service.LogicalPlantDomainService;
import com.inventory.middle.client.dto.material.*;
import com.inventory.middle.client.enums.CurrencyEnum;
import com.inventory.middle.domain.common.constants.CommonConstant;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.model.enums.StockTypeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.model.bo.mq.sub.MaterialDocInMessage;
import com.inventory.middle.domain.model.bo.mq.sub.SnMaterialDocInBO;
import com.inventory.middle.domain.model.bo.logicalPlant.LogicalPlantBO;
import com.inventory.middle.domain.service.external.RemoteProductCenterRestService;
import com.google.common.collect.Lists;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.generator.SnowflakeGenerator;

/**
 * @author dongguo.tao
 * @description
 * @date 2021-08-30 14:49:39
 */

@Service("snMaterialDocInBuilderHandleChain")
public class SnMaterialDocInBuilderHandleChain implements IHandleChain<MaterialDocInMessage, MaterialDocumentDTO> {

    @Resource
    private RemoteProductCenterRestService productCenterRestService;
    @Resource
    private LogicalPlantDomainService logicalPlantDomainService;
    @Resource
    private SnowflakeGenerator snowflakeGenerator;


    @Override
    public boolean doProcess(HandleMessage<MaterialDocInMessage, MaterialDocumentDTO> msg) {

        MaterialDocInMessage message = msg.getT();

        Long materialDocId = snowflakeGenerator.next();

        SnMaterialDocInBO docInBO = JSONObject.parseObject(message.getContent(), SnMaterialDocInBO.class);

        LogicalPlantBO plantDo = logicalPlantDomainService.getByLogicalPlantNo(docInBO.getStoreCode());
        if (null == plantDo) {
            throw new BusinessException(ResponseCodeEnum.PARAM_VALID_ERROR.getCode(), "入库仓库不存在");
        }

        List<String> skuCodes = Lists.newArrayList(docInBO.getGoodCode());
        List<SkuResponse> responseList = productCenterRestService.skuListByRequest(skuCodes, message.getToken(),message.getTenantId());
        if (CollectionUtils.isEmpty(responseList)) {
            throw new BusinessException(ResponseCodeEnum.PARAM_VALID_ERROR.getCode(), "物料不存在");
        }
        SkuResponse suk = responseList.get(CommonConstant.NUM_ZERO);

        MaterialDocumentItemDTO itemDTO = new MaterialDocumentItemDTO();
        itemDTO.setMaterialCode(docInBO.getGoodCode());
        itemDTO.setMaterialData(getMaterialDto(docInBO, suk));
        itemDTO.setWarehouseData(getWarehouseDto(plantDo));
        itemDTO.setQuantityData(getQuantityDto(docInBO, suk));
        itemDTO.setMaterialExtData(getExtDataDTO(docInBO));


        MaterialDocumentDTO documentDTO = getDocumentDto(message);
        documentDTO.setMaterialDocumentItems(Lists.newArrayList(itemDTO));
        documentDTO.setSupplyLogicalPlantNo(plantDo.getLogicalPlantNo());
        documentDTO.setUniqueNo(materialDocId.toString());

        msg.setE(documentDTO);

        return true;
    }

    public static MaterialDocumentDTO getDocumentDto(MaterialDocInMessage message) {
        if (message == null) {
            return null;
        }
        MaterialDocumentDTO documentDTO = new MaterialDocumentDTO();
        documentDTO.setMaterialDocGroup(message.getMaterialDocGroup());
        documentDTO.setMaterialDocType(message.getMaterialDocType());
        documentDTO.setMaterialDocCategory(message.getMaterialDocCategory());
        documentDTO.setBusinessType(message.getBusinessType());
        documentDTO.setAdjustType(message.getAdjustType());
        documentDTO.setToken(message.getToken());
        documentDTO.setOperator(message.getOperator());
        documentDTO.setTenantId(message.getTenantId());
        documentDTO.setPublishDate(DateUtil.formatDate(new Date()));
        documentDTO.setPostingDate(DateUtil.formatDate(new Date()));
        documentDTO.setAppKey(message.getAppKey());
        return documentDTO;
    }

    private MaterialDataDTO getMaterialDto(SnMaterialDocInBO docInBO, SkuResponse sku) {
        MaterialDataDTO materialDataDTO = new MaterialDataDTO();
        materialDataDTO.setMaterialCode(docInBO.getGoodCode());
        materialDataDTO.setMaterialName(sku.getName());
        materialDataDTO.setMaterialCategoryCode(sku.getMaterialCategoryCode());
        materialDataDTO.setMaterialVolume(BigDecimal.ZERO);
        materialDataDTO.setMaterialWeight(BigDecimal.ZERO);
        return materialDataDTO;
    }

    private WarehouseDataDTO getWarehouseDto(LogicalPlantBO plantDo) {
        WarehouseDataDTO warehouseDataDTO = new WarehouseDataDTO();
        warehouseDataDTO.setSupplyBatchNo(plantDo.getLogicalPlantNo());
        warehouseDataDTO.setSupplyLogicalPlantName(plantDo.getLogicalPlantName());
        warehouseDataDTO.setSupplyStockType(StockTypeEnum.UNRESTRICTED.getCode());
        warehouseDataDTO.setSupplyStockTypeDesc(StockTypeEnum.UNRESTRICTED.getDesc());
        return warehouseDataDTO;
    }

    private QuantityAndAmountDataDTO getQuantityDto(SnMaterialDocInBO docInBO, SkuResponse sku) {
        QuantityAndAmountDataDTO quantityDTO = new QuantityAndAmountDataDTO();
        quantityDTO.setAdjustQuantity(docInBO.getQuantity());
        quantityDTO.setUom(Objects.nonNull(sku.getUnitId()) ? sku.getUnitId() : 0);
        //todo 数能默认1
        quantityDTO.setPrice(BigDecimal.ONE);
        quantityDTO.setCurrency(CurrencyEnum.CNY.getCode());
        return quantityDTO;
    }

    private MaterialExtDataDTO getExtDataDTO(SnMaterialDocInBO docInBO) {
        MaterialExtDataDTO extDataDTO = new MaterialExtDataDTO();
        extDataDTO.setBatchProduceDate(Objects.isNull(docInBO.getProductionTime())?null:DateUtil.formatDateTime(docInBO.getProductionTime()));
        if (null != docInBO.getAnnualTime() && null != docInBO.getAnnualCycleDays()){
            Date annualDate = docInBO.getAnnualTime();
            //当 当前时间已经大于年检时间的时候， 会自动加一次年检周期。
            if (DateUtil.between(DateUtil.parseDate(DateUtil.formatDate(annualDate)), DateUtil.parseDate(DateUtil.formatDate(new Date())), DateUnit.SECOND, false) >= 0) {
                extDataDTO.setAnnualDate((DateUtil.offsetDay(annualDate, docInBO.getAnnualCycleDays())));
            } else {
                extDataDTO.setAnnualDate(annualDate);
            }
            extDataDTO.setAnnualCycleDays(docInBO.getAnnualCycleDays());
        }
        return extDataDTO;
    }
}
