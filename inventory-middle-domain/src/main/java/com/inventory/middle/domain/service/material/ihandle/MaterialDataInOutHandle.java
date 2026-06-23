/**
 * kll Inc.
 * Copyright (c) 2021 All Rights Reserved.
 */
package com.inventory.middle.domain.service.material.ihandle;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandler;
import com.inventory.middle.domain.service.material.model.MaterialDocInvReq;
import com.inventory.middle.domain.model.enums.MaterialDocRefOrderTypeEnum;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
import com.inventory.middle.domain.model.bo.EnumMapping;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentBO;
import com.inventory.middle.domain.model.bo.material.MaterialDocumentItemBO;
import com.inventory.middle.domain.service.external.SequenceNoHelper;
import com.inventory.middle.domain.service.MaterialDocCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.inventory.middle.domain.common.constants.CommonConstant.DEC;

/**
 * 出入库数据组装
 *
 * @author kll
 * @version $Id: MaterialDocBizHandler, v 0.1 2021/6/20 20:06 Exp $
 */
@Service
@Slf4j
public class MaterialDataInOutHandle implements IHandler {

    @Autowired
    private SequenceNoHelper sequenceNoHelper;
    @Resource
    private MaterialDocCoreService materialDocCoreService;
    @Autowired
    private SnowflakeGenerator snowflakeGenerator;

    @Override
    @StopWatchWrapper(logHead = "createMaterialDoc", msg = "出入库补充数据组装")
    public <T, E> HandleMessage<T, E> operation(HandleMessage<T, E> message) {
        MaterialDocInvReq req = (MaterialDocInvReq)message.getT();
        MaterialDocumentBO materialDocumentIn = req.getMaterialDocumentIn();
        //校验出对象
        MaterialDocumentBO materialDocumentOut = req.getMaterialDocumentOut();
        if (Objects.nonNull(materialDocumentOut)) {
            //转换数据
            transferItemBOs(materialDocumentIn, materialDocumentOut);
        }
        return message;
    }

    private void transferItemBOs(MaterialDocumentBO materialDocumentIn, MaterialDocumentBO materialDocumentOut) {
        //关联
        materialDocumentIn.setOriginalNo(materialDocumentOut.getMaterialDocNo());
        materialDocumentIn.setOriginalNoType(MaterialDocRefOrderTypeEnum.MATERIAL_DOC.getCode());
        //生成物料ID
        Long newMaterialDocId = snowflakeGenerator.next();
        materialDocumentIn.setMaterialDocId(newMaterialDocId);
        materialDocumentIn.setUniqueNo(newMaterialDocId.toString());
        //生成物料凭证号
        String materialDocNo = sequenceNoHelper.generateMaterialDocNo();
        materialDocumentIn.setMaterialDocNo(materialDocNo);
        //出入库时这个没有价格 需要将出库的价格同步过来
        adjustmentPrice(materialDocumentIn,materialDocumentOut);

    }

    private void adjustmentPrice(MaterialDocumentBO materialDocumentIn, MaterialDocumentBO materialDocumentOut) {
        //如果出的多个item对应入的一个item，需要按权重计算平均批次价
        //使用调整后的出item来映射需要对应入item  sku-logical
        Map<String, String> demandMap = materialDocumentOut.getMaterialDocumentItems().stream()
            .collect(Collectors.groupingBy(d -> new StringJoiner(DEC)
                .add(d.getMaterialData().getMaterialCode())
                .add(d.getWarehouseData().getDemandLogicalPlantNo())
                .toString()))
            .entrySet().stream().map(entry -> {
                EnumMapping enumMapping = new EnumMapping();
                enumMapping.setKey(entry.getKey());
                BigDecimal batchPrice = materialDocCoreService.calBatchPrice(entry.getValue());
                enumMapping.setValue(batchPrice.toPlainString());
                return enumMapping;
            }).collect(Collectors.toMap(EnumMapping::getKey, EnumMapping::getValue, (k1, k2) -> k2));

        for (MaterialDocumentItemBO itemBO : materialDocumentIn.getMaterialDocumentItems()) {
            //获取对应的出的items,进行重新计算
            BigDecimal batchPrice = new BigDecimal(demandMap.get(new StringJoiner(DEC)
                .add(itemBO.getMaterialData().getMaterialCode())
                .add(itemBO.getWarehouseData().getDemandLogicalPlantNo())
                .toString()));

            batchPrice = Optional.ofNullable(batchPrice).filter(e->BigDecimal.ZERO.compareTo(e)!=0).orElse(itemBO.getQuantityData().getPrice());
            itemBO.getQuantityData().setPrice(batchPrice);
        }
    }
}
