package com.inventory.middle.domain.service.impl;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.alibaba.fastjson.JSON;
import com.inventory.middle.domain.common.constants.CommonConstant;
import com.inventory.middle.domain.common.constants.ResponseCodeEnum;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.handles.HandleMessage;
import com.inventory.middle.domain.handles.IHandleChain;
import com.inventory.middle.domain.model.bo.material.*;
import com.inventory.middle.domain.model.enums.*;
import com.inventory.middle.domain.service.MaterialDocCoreService;
import com.inventory.middle.domain.service.MaterialDocDomainService;
import com.inventory.middle.domain.service.external.SequenceNoHelper;
import com.inventory.middle.domain.service.material.model.MaterialDocInvReq;
import com.inventory.middle.domain.service.material.model.MaterialDocInvRes;
import com.inventory.middle.domain.service.outbound.MaterialDocOutboundService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kdla.framework.common.aspect.watch.StopWatchWrapper;
import top.kdla.framework.domain.ApplicationContextHelp;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.inventory.middle.domain.common.constants.CommonConstant.COMMA;
import static com.inventory.middle.domain.common.constants.ResponseCodeEnum.MDOC_NOT_EXIST;
import static com.inventory.middle.domain.common.constants.ResponseCodeEnum.PARAM_VALID_ERROR;

/**
 * 物料凭证（出入库）领域服务实现
 * 迁移自: com.inventory.middle.biz.service.impl.MaterialDocBizServiceImpl
 */
@Service
@Slf4j
public class MaterialDocDomainServiceImpl implements MaterialDocDomainService {

    @Resource
    private SnowflakeGenerator snowflakeGenerator;

    @Resource
    private SequenceNoHelper sequenceNoHelper;

    @Resource
    private MaterialDocOutboundService materialDocOutboundCommonService;

    @Resource
    private MaterialDocCoreService materialDocCoreService;

    // ──────────────────────────────────────────────
    // Public API
    // ──────────────────────────────────────────────

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialDocInvRes createMaterialDoc(MaterialDocumentBO materialDocument) {
        initMaterialDocument(materialDocument);
        Integer materialDocCategory = materialDocument.getMaterialDocCategory();
        if (MaterialDocCategoryEnum.IN.getCode().equals(materialDocCategory)
                || MaterialDocCategoryEnum.INOUT.getCode().equals(materialDocCategory)) {
            return domainOutInboundMaterialDoc(materialDocument);
        } else if (MaterialDocCategoryEnum.CANCEL.getCode().equals(materialDocCategory)) {
            throw new BusinessException("请调用 reverseMaterialDoc 方法");
        } else if (MaterialDocCategoryEnum.OUT.getCode().equals(materialDocCategory)) {
            return domainOutboundMaterialDoc(materialDocument);
        }
        return null;
    }

    @Override
    @StopWatchWrapper(logHead = "inboundMaterialDoc", msg = "入库物料凭证整体")
    @Transactional(rollbackFor = Exception.class)
    public MaterialDocInvRes inboundMaterialDoc(MaterialDocumentBO materialDocument) {
        if (!MaterialDocCategoryEnum.IN.getCode().equals(materialDocument.getMaterialDocCategory())
                || !MaterialDocCategoryEnum.IN.equals(
                        BusinessTypeEnum.materialDocCategoryByAdjustType(materialDocument.getAdjustType()))) {
            throw new BusinessException("请使用入库的移动类型");
        }
        initMaterialDocument(materialDocument);
        return domainOutInboundMaterialDoc(materialDocument);
    }

    @Override
    @StopWatchWrapper(logHead = "outboundMaterialDoc", msg = "出库物料凭证整体")
    @Transactional(rollbackFor = Exception.class)
    public MaterialDocInvRes outboundMaterialDoc(MaterialDocumentBO materialDocument) {
        if (!MaterialDocCategoryEnum.OUT.getCode().equals(materialDocument.getMaterialDocCategory())
                || !MaterialDocCategoryEnum.OUT.equals(
                        BusinessTypeEnum.materialDocCategoryByAdjustType(materialDocument.getAdjustType()))) {
            throw new BusinessException("请使用出库的移动类型");
        }
        initMaterialDocument(materialDocument);
        return domainOutboundMaterialDoc(materialDocument);
    }

    @Override
    @StopWatchWrapper(logHead = "outInboundMaterialDoc", msg = "出入库物料凭证整体")
    @Transactional(rollbackFor = Exception.class)
    public MaterialDocInvRes outInboundMaterialDoc(MaterialDocumentBO materialDocument) {
        if (!MaterialDocCategoryEnum.INOUT.getCode().equals(materialDocument.getMaterialDocCategory())
                || !MaterialDocCategoryEnum.INOUT.equals(
                        BusinessTypeEnum.materialDocCategoryByAdjustType(materialDocument.getAdjustType()))) {
            throw new BusinessException("请使用出入库的移动类型");
        }
        initMaterialDocument(materialDocument);
        return domainOutInboundMaterialDoc(materialDocument);
    }

    @Override
    @StopWatchWrapper(logHead = "reverseMaterialDoc", msg = "物料凭证冲销")
    @Transactional(rollbackFor = Exception.class)
    public MaterialDocInvRes reverseMaterialDoc(MaterialDocumentBO reverseMaterialDocument) {
        // 1. 校验原凭证号
        String originalDocNo = reverseMaterialDocument.getMaterialDocNo();
        if (StringUtils.isBlank(originalDocNo)) {
            throw new BusinessException(PARAM_VALID_ERROR.getCode(), "原凭证号不能为空");
        }

        // 2. 查询原凭证
        MaterialDocumentBO originalDoc = materialDocCoreService.getMaterialDoc(originalDocNo);
        if (originalDoc == null) {
            throw new BusinessException(MDOC_NOT_EXIST.getCode(), "原凭证不存在: " + originalDocNo);
        }

        // 3. 取冲销 adjustType（原 adjustType → reverseCode）
        MaterialAdjustTypeEnum originalAdjustTypeEnum = MaterialAdjustTypeEnum.tansfer(originalDoc.getAdjustType());
        if (originalAdjustTypeEnum == null || StringUtils.isBlank(originalAdjustTypeEnum.getReverseCode())) {
            throw new BusinessException(PARAM_VALID_ERROR.getCode(),
                    "原凭证移动类型[" + originalDoc.getAdjustType() + "]不支持冲销");
        }
        String reverseAdjustType = originalAdjustTypeEnum.getReverseCode();

        // 4. 构建冲销凭证：复制原凭证关键字段，覆盖 adjustType / materialDocNo / originalNo
        reverseMaterialDocument.setAdjustType(reverseAdjustType);
        reverseMaterialDocument.setMaterialDocCategory(MaterialDocCategoryEnum.CANCEL.getCode());
        reverseMaterialDocument.setOriginalNo(originalDocNo);
        // 冲销凭证号由 initMaterialDocument 重新生成（清空旧值让框架分配）
        reverseMaterialDocument.setMaterialDocNo(null);
        initMaterialDocument(reverseMaterialDocument);

        // 5. 路由到冲销 handle chain 执行（与入出库路由机制一致）
        String beanName = MaterialAdjustTypeEnum.beanNameByCode(reverseAdjustType);
        MaterialDocInvRes result;
        if (StringUtils.isBlank(beanName)) {
            // 无专属 bean 时走默认出库路由（退库类冲销）
            result = domainOutboundMaterialDoc(reverseMaterialDocument);
        } else {
            result = domainOutInboundMaterialDoc(reverseMaterialDocument);
        }

        log.info("reverseMaterialDoc success, originalDocNo={} reverseDocNo={}",
                originalDocNo, result != null ? result.getMaterialDocNo() : null);
        return result;
    }

    // ──────────────────────────────────────────────
    // Private helpers
    // ──────────────────────────────────────────────

    private void initMaterialDocument(MaterialDocumentBO materialDocument) {
        materialDocument.setMaterialDocId(snowflakeGenerator.next());
        if (StringUtils.isBlank(materialDocument.getMaterialDocNo())) {
            materialDocument.setMaterialDocNo(sequenceNoHelper.generateMaterialDocNo());
        }
        materialDocument.setMaterialDocGroup(
                MaterialAdjustTypeEnum.getMaterialDocGroup(materialDocument.getAdjustType()).getCode());
        BusinessTypeEnum businessTypeEnum = BusinessTypeEnum.businessTypeByAdjustType(materialDocument.getAdjustType());
        materialDocument.setBusinessType(businessTypeEnum.getCode());
        materialDocument.setIo(businessTypeEnum.getIo());
        log.info("initMaterialDocument {}", JSON.toJSONString(materialDocument));
    }

    @SuppressWarnings("unchecked")
    private MaterialDocInvRes domainOutInboundMaterialDoc(MaterialDocumentBO materialDocument) {
        String materialDocBean = MaterialAdjustTypeEnum.beanNameByCode(materialDocument.getAdjustType());
        if (StringUtils.isBlank(materialDocBean)) {
            return null;
        }
        IHandleChain materialDocHandleChain = ApplicationContextHelp.getBean(materialDocBean, IHandleChain.class);
        HandleMessage msg = new HandleMessage();
        MaterialDocInvReq req = new MaterialDocInvReq();
        req.setMaterialDocument(materialDocument);
        msg.setT(req);
        materialDocHandleChain.doProcess(msg);
        return (MaterialDocInvRes) msg.getE();
    }

    private MaterialDocInvRes domainOutboundMaterialDoc(MaterialDocumentBO materialDocument) {
        MaterialDocInvRes materialDocInvRes = new MaterialDocInvRes();
        materialDocOutboundCommonService.handleOutbound(materialDocument);
        materialDocInvRes.setMaterialDocNo(materialDocument.getMaterialDocNo());
        List<MaterialBatchNoBO> materialBatchNos = materialDocument.getMaterialDocumentItems().stream()
                .map(d -> {
                    MaterialBatchNoBO bo = new MaterialBatchNoBO();
                    bo.setMaterialCode(d.getMaterialCode());
                    bo.setBatchNo(d.getBatchNo());
                    bo.setBatchPrice(d.getQuantityData().getPrice());
                    return bo;
                }).collect(Collectors.toList());
        materialDocInvRes.setMaterialBatchNos(materialBatchNos);
        Set<String> batchNos = materialBatchNos.stream()
                .map(MaterialBatchNoBO::getBatchNo).collect(Collectors.toSet());
        materialDocInvRes.setBatchNo(StringUtils.join(batchNos, COMMA));
        materialDocInvRes.setFullMaterialDocNo(materialDocument.getMaterialDocNo());
        return materialDocInvRes;
    }
}

