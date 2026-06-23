package com.inventory.middle.application.plan.bom.convertor;

import com.inventory.middle.client.plan.bom.dto.*;
import com.inventory.middle.domain.plan.bom.bo.*;
import com.inventory.middle.infra.plan.persistence.condition.bom.BomCaseQueryCondition;
import com.inventory.middle.infra.plan.persistence.condition.bom.BomCaseResult;
import com.inventory.middle.infra.plan.persistence.entity.bom.BomCasePO;
import com.inventory.middle.infra.plan.persistence.entity.bom.BomNodePO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BOM 转换器（静态工具方法）
 * 负责 DTO ↔ BO ↔ PO 之间的映射
 */
public final class BomCaseConvertor {

    private BomCaseConvertor() {
    }

    // ==================== DTO → BO ====================

    public static BomTreeRenderRequestBO toRenderBO(BomTreeRenderReqDTO dto) {
        if (dto == null) {
            return null;
        }
        BomTreeRenderRequestBO bo = new BomTreeRenderRequestBO();
        bo.setMaterialCode(dto.getMaterialCode());
        bo.setLogicalPlantNo(dto.getLogicalPlantNo());
        bo.setTenantId(dto.getTenantId());
        bo.setNodeAsRoot(dto.isNodeAsRoot());
        bo.setShowLeaf(dto.isShowLeaf());
        bo.setShowEnable(dto.isShowEnable());
        return bo;
    }

    public static BomCaseConfigurationBO toConfigurationBO(BomCaseConfigurationDTO dto) {
        if (dto == null) {
            return null;
        }
        BomCaseConfigurationBO bo = new BomCaseConfigurationBO();
        bo.setTenantId(dto.getTenantId());
        bo.setUserId(dto.getUserId());
        bo.setUserName(dto.getUserName());
        bo.setBomCase(toCaseBO(dto.getBomCase()));
        bo.setParent(toNodeBO(dto.getParent()));
        if (dto.getChildren() != null) {
            bo.setChildren(dto.getChildren().stream()
                    .map(BomCaseConvertor::toNodeBO)
                    .collect(Collectors.toList()));
        }
        return bo;
    }

    public static BomChangeStatusReqBO toChangeStatusBO(BomChangeStatusReqDTO dto) {
        if (dto == null) {
            return null;
        }
        BomChangeStatusReqBO bo = new BomChangeStatusReqBO();
        bo.setId(dto.getId());
        bo.setStatus(dto.getStatus());
        bo.setTenantId(dto.getTenantId());
        return bo;
    }

    public static BomCaseQueryCondition toQueryCondition(BomCaseQueryReqDTO dto) {
        if (dto == null) {
            return new BomCaseQueryCondition();
        }
        BomCaseQueryCondition condition = new BomCaseQueryCondition();
        condition.setCode(dto.getCode());
        condition.setName(dto.getName());
        condition.setLogicalPlantNos(dto.getLogicalPlantNos());
        condition.setCompanyName(dto.getCompanyName());
        condition.setType(dto.getType());
        condition.setStatus(dto.getStatus());
        condition.setTenantId(dto.getTenantId());
        return condition;
    }

    private static BomCaseBO toCaseBO(BomCaseDTO dto) {
        if (dto == null) {
            return null;
        }
        BomCaseBO bo = new BomCaseBO();
        bo.setId(dto.getId());
        bo.setCode(dto.getCode());
        bo.setName(dto.getName());
        bo.setCompanyCode(dto.getCompanyCode());
        bo.setCompanyName(dto.getCompanyName());
        bo.setLogicalPlantNo(dto.getLogicalPlantNo());
        bo.setLogicalPlantName(dto.getLogicalPlantName());
        bo.setType(dto.getType());
        bo.setStatus(dto.getStatus());
        bo.setRemark(dto.getRemark());
        return bo;
    }

    private static BomNodeBO toNodeBO(BomNodeDTO dto) {
        if (dto == null) {
            return null;
        }
        BomNodeBO bo = new BomNodeBO();
        bo.setId(dto.getId());
        bo.setBomCaseId(dto.getBomCaseId());
        bo.setMaterialCode(dto.getMaterialCode());
        bo.setMaterialDesc(dto.getMaterialDesc());
        bo.setMaterialUnit(dto.getMaterialUnit());
        bo.setMaterialAttr(dto.getMaterialAttr());
        bo.setMaterialSpec(dto.getMaterialSpec());
        bo.setLogicalPlantNo(dto.getLogicalPlantNo());
        bo.setLogicalPlantName(dto.getLogicalPlantName());
        bo.setAmount(dto.getAmount());
        bo.setType(dto.getType());
        bo.setTenantId(dto.getTenantId());
        bo.setStatus(dto.getStatus());
        return bo;
    }

    // ==================== BO/PO → PO ====================

    public static BomCasePO toCasePO(BomCaseBO bo) {
        if (bo == null) {
            return null;
        }
        BomCasePO po = new BomCasePO();
        po.setId(bo.getId());
        po.setCode(bo.getCode());
        po.setName(bo.getName());
        po.setCompanyCode(bo.getCompanyCode());
        po.setCompanyName(bo.getCompanyName());
        po.setLogicalPlantNo(bo.getLogicalPlantNo());
        po.setLogicalPlantName(bo.getLogicalPlantName());
        po.setType(bo.getType());
        po.setStatus(bo.getStatus());
        po.setRemark(bo.getRemark());
        po.setTenantId(bo.getTenantId());
        return po;
    }

    public static BomNodePO toNodePO(BomNodeBO bo) {
        if (bo == null) {
            return null;
        }
        BomNodePO po = new BomNodePO();
        po.setId(bo.getId());
        po.setBomCaseId(bo.getBomCaseId());
        po.setMaterialCode(bo.getMaterialCode());
        po.setMaterialDesc(bo.getMaterialDesc());
        po.setMaterialUnit(bo.getMaterialUnit());
        po.setMaterialAttr(bo.getMaterialAttr());
        po.setMaterialSpec(bo.getMaterialSpec());
        po.setLogicalPlantNo(bo.getLogicalPlantNo());
        po.setLogicalPlantName(bo.getLogicalPlantName());
        po.setAmount(bo.getAmount());
        po.setType(bo.getType());
        po.setTenantId(bo.getTenantId());
        po.setStatus(bo.getStatus());
        return po;
    }

    /** Convert HierarchicalBomNodeBO back to BomNodePO (for tree traversal re-query) */
    public static BomNodePO toNodePO(HierarchicalBomNodeBO bo) {
        if (bo == null) {
            return null;
        }
        BomNodePO po = new BomNodePO();
        po.setId(bo.getId());
        po.setBomCaseId(bo.getBomCaseId());
        po.setMaterialCode(bo.getMaterialCode());
        po.setMaterialDesc(bo.getMaterialDesc());
        po.setMaterialUnit(bo.getMaterialUnit());
        po.setMaterialAttr(bo.getMaterialAttr());
        po.setMaterialSpec(bo.getMaterialSpec());
        po.setLogicalPlantNo(bo.getLogicalPlantNo());
        po.setLogicalPlantName(bo.getLogicalPlantName());
        po.setAmount(bo.getAmount());
        po.setType(bo.getType());
        po.setTenantId(bo.getTenantId());
        po.setStatus(bo.getStatus());
        return po;
    }

    // ==================== PO → BO ====================

    public static HierarchicalBomNodeBO toHierarchicalBO(BomNodePO po) {
        if (po == null) {
            return null;
        }
        HierarchicalBomNodeBO bo = new HierarchicalBomNodeBO();
        bo.setId(po.getId());
        bo.setBomCaseId(po.getBomCaseId());
        bo.setMaterialCode(po.getMaterialCode());
        bo.setMaterialDesc(po.getMaterialDesc());
        bo.setMaterialUnit(po.getMaterialUnit());
        bo.setMaterialAttr(po.getMaterialAttr());
        bo.setMaterialSpec(po.getMaterialSpec());
        bo.setLogicalPlantNo(po.getLogicalPlantNo());
        bo.setLogicalPlantName(po.getLogicalPlantName());
        bo.setAmount(po.getAmount());
        bo.setType(po.getType());
        bo.setTenantId(po.getTenantId());
        bo.setStatus(po.getStatus());
        return bo;
    }

    // ==================== PO/Result → DTO ====================

    public static BomCaseDetailDTO toDetailDTO(BomCasePO casePO, BomNodePO parentPO) {
        BomCaseDetailDTO dto = new BomCaseDetailDTO();
        dto.setBomCase(toCaseDTO(casePO));
        dto.setParent(toNodeDTO(parentPO));
        return dto;
    }

    private static BomCaseDTO toCaseDTO(BomCasePO po) {
        if (po == null) {
            return null;
        }
        BomCaseDTO dto = new BomCaseDTO();
        dto.setId(po.getId());
        dto.setCode(po.getCode());
        dto.setName(po.getName());
        dto.setCompanyCode(po.getCompanyCode());
        dto.setCompanyName(po.getCompanyName());
        dto.setLogicalPlantNo(po.getLogicalPlantNo());
        dto.setLogicalPlantName(po.getLogicalPlantName());
        dto.setType(po.getType());
        dto.setStatus(po.getStatus());
        dto.setRemark(po.getRemark());
        return dto;
    }

    public static BomNodeDTO toNodeDTO(BomNodePO po) {
        if (po == null) {
            return null;
        }
        BomNodeDTO dto = new BomNodeDTO();
        dto.setId(po.getId());
        dto.setBomCaseId(po.getBomCaseId());
        dto.setMaterialCode(po.getMaterialCode());
        dto.setMaterialDesc(po.getMaterialDesc());
        dto.setMaterialUnit(po.getMaterialUnit());
        dto.setMaterialAttr(po.getMaterialAttr());
        dto.setMaterialSpec(po.getMaterialSpec());
        dto.setLogicalPlantNo(po.getLogicalPlantNo());
        dto.setLogicalPlantName(po.getLogicalPlantName());
        dto.setAmount(po.getAmount());
        dto.setType(po.getType());
        dto.setTenantId(po.getTenantId());
        dto.setStatus(po.getStatus());
        return dto;
    }

    public static List<BomNodeDTO> toBomNodeDTOs(List<BomNodePO> pos) {
        if (pos == null) {
            return Collections.emptyList();
        }
        return pos.stream().map(BomCaseConvertor::toNodeDTO).collect(Collectors.toList());
    }

    public static List<BomCaseQueryResDTO> toQueryResDTOs(List<BomCaseResult> results) {
        if (results == null) {
            return Collections.emptyList();
        }
        return results.stream().map(BomCaseConvertor::toQueryResDTO).collect(Collectors.toList());
    }

    private static BomCaseQueryResDTO toQueryResDTO(BomCaseResult result) {
        if (result == null) {
            return null;
        }
        BomCaseQueryResDTO dto = new BomCaseQueryResDTO();
        dto.setBomId(result.getBomId());
        dto.setCode(result.getCode());
        dto.setName(result.getName());
        dto.setLogicalPlantNo(result.getLogicalPlantNo());
        dto.setLogicalPlantName(result.getLogicalPlantName());
        dto.setCompanyCode(result.getCompanyCode());
        dto.setCompanyName(result.getCompanyName());
        dto.setType(result.getType());
        dto.setParentName(result.getParentName());
        dto.setMaterialCode(result.getMaterialCode());
        dto.setUnit(result.getUnit());
        dto.setStatus(result.getStatus());
        dto.setCreateUserName(result.getCreateUserName());
        dto.setCreateTime(result.getCreateTime());
        dto.setUpdateTime(result.getUpdateTime());
        return dto;
    }

    // ==================== BO → DTO (tree) ====================

    public static BomTreeDTO toBomTreeDTO(BomTreeBO treeBO) {
        if (treeBO == null) {
            return null;
        }
        BomTreeDTO dto = new BomTreeDTO();
        dto.setRoot(toHierarchicalDTO(treeBO.getRoot()));
        return dto;
    }

    private static HierarchicalBomNodeDTO toHierarchicalDTO(HierarchicalBomNodeBO bo) {
        if (bo == null) {
            return null;
        }
        HierarchicalBomNodeDTO dto = new HierarchicalBomNodeDTO();
        dto.setId(bo.getId());
        dto.setBomCaseId(bo.getBomCaseId());
        dto.setMaterialCode(bo.getMaterialCode());
        dto.setMaterialDesc(bo.getMaterialDesc());
        dto.setMaterialUnit(bo.getMaterialUnit());
        dto.setMaterialAttr(bo.getMaterialAttr());
        dto.setMaterialSpec(bo.getMaterialSpec());
        dto.setLogicalPlantNo(bo.getLogicalPlantNo());
        dto.setLogicalPlantName(bo.getLogicalPlantName());
        dto.setAmount(bo.getAmount());
        dto.setType(bo.getType());
        dto.setTenantId(bo.getTenantId());
        dto.setStatus(bo.getStatus());
        dto.setCode(bo.getCode());
        dto.setName(bo.getName());
        if (bo.getChildren() != null) {
            List<HierarchicalBomNodeDTO> childDTOs = new ArrayList<>();
            for (HierarchicalBomNodeBO child : bo.getChildren()) {
                childDTOs.add(toHierarchicalDTO(child));
            }
            dto.setChildren(childDTOs);
        }
        return dto;
    }
}
