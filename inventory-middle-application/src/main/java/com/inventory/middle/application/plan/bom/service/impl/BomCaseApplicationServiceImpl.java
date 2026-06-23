package com.inventory.middle.application.plan.bom.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.inventory.middle.application.plan.bom.convertor.BomCaseConvertor;
import com.inventory.middle.application.plan.bom.service.BomCaseApplicationService;
import com.inventory.middle.client.plan.bom.dto.*;
import com.inventory.middle.domain.common.exception.BusinessException;
import com.inventory.middle.domain.plan.bom.bo.*;
import com.inventory.middle.domain.plan.bom.common.BomCaseStatusEnum;
import com.inventory.middle.domain.plan.bom.common.BomNodeTypeEnum;
import com.inventory.middle.infra.plan.persistence.condition.bom.BomCaseQueryCondition;
import com.inventory.middle.infra.plan.persistence.condition.bom.BomCaseResult;
import com.inventory.middle.infra.plan.persistence.condition.bom.BomChangeStatusCondition;
import com.inventory.middle.infra.plan.persistence.condition.bom.BomCodeQueryCondition;
import com.inventory.middle.infra.plan.persistence.entity.bom.BomCasePO;
import com.inventory.middle.infra.plan.persistence.entity.bom.BomNodePO;
import com.inventory.middle.infra.plan.persistence.entity.bom.BomRedisConstant;
import com.inventory.middle.infra.plan.persistence.mapper.bom.BomCaseMapper;
import com.inventory.middle.infra.plan.persistence.mapper.bom.BomNodeMapper;
import com.inventory.middle.infra.plan.stub.PlanParticipantStub;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.dto.SingleResponse;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * BOM应用服务实现
 * 合并了 management BomCaseServiceImpl、facade BomCaseRpcServiceImpl、bff BomCaseServiceImpl 的逻辑
 */
@Service
@Slf4j
public class BomCaseApplicationServiceImpl implements BomCaseApplicationService {

    @Resource
    private BomCaseMapper bomCaseMapper;

    @Resource
    private BomNodeMapper bomNodeMapper;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private PlanParticipantStub planParticipantStub;

    // ==================== BomCaseRpcService 接口实现 ====================

    @Override
    public SingleResponse<ArrayList<BomTreeDTO>> renderBomTree(BomTreeRenderReqDTO requestDTO) {
        BomTreeRenderRequestBO requestBO = BomCaseConvertor.toRenderBO(requestDTO);
        if (requestBO == null || requestBO.getLogicalPlantNo() == null
                || requestBO.getTenantId() == null || requestBO.getMaterialCode() == null) {
            return SingleResponse.buildFailure("PARAM_ERROR", "参数不能为空");
        }

        List<BomTreeBO> bomTreeBOList = renderBomTree(requestBO);
        ArrayList<BomTreeDTO> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bomTreeBOList)) {
            for (BomTreeBO treeBO : bomTreeBOList) {
                result.add(BomCaseConvertor.toBomTreeDTO(treeBO));
            }
        }
        return SingleResponse.of(result);
    }

    @Override
    public SingleResponse<String> create(BomCaseConfigurationDTO requestDTO) {
        BomCaseConfigurationBO requestBO = BomCaseConvertor.toConfigurationBO(requestDTO);
        // 补全公司名称
        if (requestBO.getBomCase() != null && requestBO.getBomCase().getCompanyCode() != null) {
            String companyName = planParticipantStub.getCompanyName(
                    requestBO.getTenantId(), requestBO.getUserId(),
                    requestBO.getBomCase().getCompanyCode());
            requestBO.getBomCase().setCompanyName(companyName);
        }
        // 母件设置租户Id
        if (requestBO.getParent() != null) {
            requestBO.getParent().setTenantId(requestBO.getTenantId());
        }
        // 子件设置租户Id
        if (CollectionUtils.isNotEmpty(requestBO.getChildren())) {
            requestBO.getChildren().forEach(d -> d.setTenantId(requestBO.getTenantId()));
        }
        return SingleResponse.of(createBom(requestBO));
    }

    @Override
    public SingleResponse<String> update(BomCaseConfigurationDTO requestDTO) {
        BomCaseConfigurationBO requestBO = BomCaseConvertor.toConfigurationBO(requestDTO);
        // 补全公司名称
        if (requestBO.getBomCase() != null && requestBO.getBomCase().getCompanyCode() != null) {
            String companyName = planParticipantStub.getCompanyName(
                    requestBO.getTenantId(), requestBO.getUserId(),
                    requestBO.getBomCase().getCompanyCode());
            requestBO.getBomCase().setCompanyName(companyName);
        }
        // 母件设置租户Id
        if (requestBO.getParent() != null) {
            requestBO.getParent().setTenantId(requestBO.getTenantId());
        }
        // 子件设置租户Id
        if (CollectionUtils.isNotEmpty(requestBO.getChildren())) {
            requestBO.getChildren().forEach(d -> d.setTenantId(requestBO.getTenantId()));
        }
        return SingleResponse.of(updateBom(requestBO));
    }

    @Override
    public SingleResponse<Boolean> bomChangeStatus(BomChangeStatusReqDTO requestDTO) {
        BomChangeStatusReqBO requestBO = BomCaseConvertor.toChangeStatusBO(requestDTO);
        return SingleResponse.of(changeStatus(requestBO));
    }

    @Override
    public PageResponse<BomCaseQueryResDTO> pageQueryBom(BomCaseQueryReqDTO requestDTO) {
        BomCaseQueryCondition condition = BomCaseConvertor.toQueryCondition(requestDTO);
        // 物料编码单独处理
        if (StringUtils.isNotBlank(requestDTO.getMaterialCode())) {
            BomNodePO nodeCondition = new BomNodePO();
            nodeCondition.setMaterialCode(requestDTO.getMaterialCode());
            List<BomNodePO> pos = bomNodeMapper.queryByCondition(nodeCondition);
            if (CollectionUtils.isEmpty(pos)) {
                return PageResponse.of(Collections.emptyList(), 0L, requestDTO.getPageSize(), requestDTO.getPageNum());
            }
            condition.setIds(pos.stream().map(BomNodePO::getBomCaseId).collect(Collectors.toList()));
        }
        Page<Object> page = PageHelper.startPage(requestDTO.getPageNum(), requestDTO.getPageSize());
        List<BomCaseResult> results = bomCaseMapper.pageQueryBom(condition);
        List<BomCaseQueryResDTO> resDTOs = BomCaseConvertor.toQueryResDTOs(results);
        return PageResponse.of(resDTOs, page.getTotal(), page.getPageSize(), page.getPageNum());
    }

    @Override
    public SingleResponse<BomCaseDetailDTO> queryBomCaseDetail(BomCaseDetailReqDTO requestDTO) {
        if (requestDTO.getTenantId() == null || requestDTO.getBomCaseId() == null) {
            return SingleResponse.buildFailure("PARAM_ERROR", "参数不能为空");
        }
        BomCasePO caseCondition = new BomCasePO();
        caseCondition.setId(requestDTO.getBomCaseId());
        caseCondition.setTenantId(requestDTO.getTenantId());
        List<BomCasePO> caseList = bomCaseMapper.queryByCondition(caseCondition);
        if (CollectionUtils.isEmpty(caseList)) {
            return SingleResponse.of(null);
        }
        BomCasePO casePO = caseList.get(0);

        BomNodePO nodeCondition = new BomNodePO();
        nodeCondition.setBomCaseId(requestDTO.getBomCaseId());
        nodeCondition.setTenantId(requestDTO.getTenantId());
        nodeCondition.setType(BomNodeTypeEnum.PARENT.getCode());
        List<BomNodePO> nodes = bomNodeMapper.queryByCondition(nodeCondition);
        BomNodePO parent = nodes.isEmpty() ? null : nodes.get(0);

        BomCaseDetailDTO detailDTO = BomCaseConvertor.toDetailDTO(casePO, parent);
        return SingleResponse.of(detailDTO);
    }

    @Override
    public PageResponse<BomNodeDTO> pageQueryChildrenDetail(BomCaseChildrenQueryReqDTO requestDTO) {
        BomNodePO nodeCondition = new BomNodePO();
        nodeCondition.setBomCaseId(requestDTO.getBomCaseId());
        nodeCondition.setTenantId(requestDTO.getTenantId());
        nodeCondition.setType(BomNodeTypeEnum.CHILD.getCode());
        Page<Object> page = PageHelper.startPage(requestDTO.getPageNum(), requestDTO.getPageSize());
        List<BomNodePO> nodeList = bomNodeMapper.queryByCondition(nodeCondition);
        List<BomNodeDTO> dtos = BomCaseConvertor.toBomNodeDTOs(nodeList);
        return PageResponse.of(dtos, page.getTotal(), page.getPageSize(), page.getPageNum());
    }

    @Override
    public SingleResponse<ArrayList<BomNodeDTO>> queryChildrenDetail(BomCaseDetailReqDTO requestDTO) {
        BomNodePO nodeCondition = new BomNodePO();
        nodeCondition.setBomCaseId(requestDTO.getBomCaseId());
        nodeCondition.setTenantId(requestDTO.getTenantId());
        nodeCondition.setType(BomNodeTypeEnum.CHILD.getCode());
        List<BomNodePO> nodeList = bomNodeMapper.queryByCondition(nodeCondition);
        ArrayList<BomNodeDTO> result = new ArrayList<>(BomCaseConvertor.toBomNodeDTOs(nodeList));
        return SingleResponse.of(result);
    }

    // ==================== 内部业务逻辑 ====================

    @Transactional(rollbackFor = Exception.class)
    public String createBom(BomCaseConfigurationBO requestBO) {
        if (requestBO == null || requestBO.getBomCase() == null
                || requestBO.getParent() == null || requestBO.getChildren() == null) {
            throw new BusinessException("BOM创建参数不能为空");
        }
        // 生成BOM编码（Stub，待组N替换）
        String bomCode = generateBomCode(requestBO.getTenantId());

        BomCasePO bomCasePO = BomCaseConvertor.toCasePO(requestBO.getBomCase());
        bomCasePO.setCode(bomCode);
        bomCasePO.setCreatorId(requestBO.getUserId());
        bomCasePO.setUpdatorId(requestBO.getUserId());
        bomCasePO.setTenantId(requestBO.getTenantId());
        bomCasePO.setCreateTime(new Date());
        bomCasePO.setUpdateTime(new Date());
        bomCasePO.setDeleted(0);

        BomNodePO parent = BomCaseConvertor.toNodePO(requestBO.getParent());
        List<BomNodePO> children = requestBO.getChildren().stream()
                .map(BomCaseConvertor::toNodePO).collect(Collectors.toList());

        // 1. 新增BomCase
        bomCaseMapper.insertSelective(bomCasePO);

        // 2. 为BomNode设置bomCaseId和status
        children.forEach(d -> {
            d.setBomCaseId(bomCasePO.getId());
            d.setStatus(bomCasePO.getStatus());
        });
        parent.setBomCaseId(bomCasePO.getId());
        parent.setStatus(bomCasePO.getStatus());

        List<BomNodePO> allNodes = Lists.newArrayList(children);
        allNodes.add(parent);

        // 3. 批量新增bomNode
        int batchCount = bomNodeMapper.batchInsert(allNodes);

        // 4. 维护缓存
        if (batchCount > 0) {
            addBomTreeCache(parent, children);
        }

        return bomCode;
    }

    @Transactional(rollbackFor = Exception.class)
    public String updateBom(BomCaseConfigurationBO requestBO) {
        if (requestBO == null || requestBO.getBomCase() == null
                || requestBO.getParent() == null || requestBO.getChildren() == null) {
            throw new BusinessException("BOM更新参数不能为空");
        }

        BomCasePO bomCasePO = BomCaseConvertor.toCasePO(requestBO.getBomCase());
        bomCasePO.setUpdatorId(requestBO.getUserId());
        bomCasePO.setUpdateTime(new Date());

        BomNodePO parent = BomCaseConvertor.toNodePO(requestBO.getParent());
        List<BomNodePO> children = requestBO.getChildren().stream()
                .map(BomCaseConvertor::toNodePO).collect(Collectors.toList());

        // 1. 更新BomCase
        bomCaseMapper.updateByPrimaryKeySelective(bomCasePO);
        // 2. 删除缓存
        deleteBomTreeCache(bomCasePO.getId());
        // 3. 软删除现有节点
        bomNodeMapper.deleteByBomCaseId(bomCasePO.getId());
        // 4. 设置bomCaseId和status
        children.forEach(d -> {
            d.setBomCaseId(bomCasePO.getId());
            d.setStatus(bomCasePO.getStatus());
        });
        parent.setBomCaseId(bomCasePO.getId());
        parent.setStatus(bomCasePO.getStatus());
        List<BomNodePO> allNodes = Lists.newArrayList(children);
        allNodes.add(parent);
        // 5. 批量新增bomNode
        int batchCount = bomNodeMapper.batchInsert(allNodes);
        // 6. 维护缓存
        if (batchCount > 0) {
            addBomTreeCache(parent, children);
        }

        return bomCasePO.getCode();
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean changeStatus(BomChangeStatusReqBO requestBO) {
        BomCasePO byId = bomCaseMapper.selectByPrimaryKey(requestBO.getId());
        if (Objects.isNull(byId)) {
            throw new BusinessException("BOM单不存在");
        }
        if (!StringUtils.equals(requestBO.getTenantId(), byId.getTenantId())) {
            throw new BusinessException("无权限操作该BOM单");
        }

        BomNodePO po = new BomNodePO();
        po.setBomCaseId(requestBO.getId());
        po.setTenantId(requestBO.getTenantId());
        List<BomNodePO> bomNodePos = bomNodeMapper.queryByCondition(po);

        if (CollectionUtils.isEmpty(bomNodePos)) {
            throw new BusinessException("BOM节点数据异常");
        }

        BomNodePO parent = null;
        Iterator<BomNodePO> iter = bomNodePos.iterator();
        while (iter.hasNext()) {
            BomNodePO bomNodePO = iter.next();
            if (bomNodePO != null && bomNodePO.getType().equals(BomNodeTypeEnum.PARENT.getCode())) {
                parent = bomNodePO;
                iter.remove();
                break;
            }
        }
        if (Objects.isNull(parent)) {
            throw new BusinessException("BOM母件数据异常");
        }

        BomChangeStatusCondition condition = new BomChangeStatusCondition();
        condition.setId(requestBO.getId());
        condition.setStatus(requestBO.getStatus());

        int nodeCount = bomNodeMapper.changeNodeStatus(condition);
        int caseCount = bomCaseMapper.updateStatusById(condition);

        // 缓存清理
        if (nodeCount > 0 && caseCount > 0) {
            String childrenKey = BomRedisConstant.getBomChildrenKey(
                    parent.getTenantId(), parent.getLogicalPlantNo(), parent.getMaterialCode());
            deleteCacheKey(childrenKey);
            String parentInfoKey = BomRedisConstant.getBomParentInfoKey(
                    parent.getTenantId(), parent.getLogicalPlantNo(), parent.getMaterialCode());
            deleteCacheKey(parentInfoKey);
            for (BomNodePO child : bomNodePos) {
                String parentKey = BomRedisConstant.getBomParentKey(
                        child.getTenantId(), child.getLogicalPlantNo(), child.getMaterialCode());
                deleteCacheKey(parentKey);
            }
        }

        return nodeCount > 0 && caseCount > 0;
    }

    // ==================== BOM树渲染内部逻辑 ====================

    private List<BomTreeBO> renderBomTree(BomTreeRenderRequestBO requestBO) {
        List<BomTreeBO> bomTreeList = new ArrayList<>();
        BomNodePO parentNode = getBomParentInfo(requestBO.getTenantId(),
                requestBO.getLogicalPlantNo(), requestBO.getMaterialCode());

        if (Objects.isNull(parentNode) && requestBO.isNodeAsRoot()) {
            return null;
        }

        List<HierarchicalBomNodeBO> nodeList = new ArrayList<>();
        if (requestBO.isNodeAsRoot()) {
            HierarchicalBomNodeBO rootNode = BomCaseConvertor.toHierarchicalBO(parentNode);
            generateNodeList(rootNode, 0L, 1L, nodeList, requestBO.isShowLeaf(), requestBO.isShowEnable());
            List<HierarchicalBomNodeBO> tree = TreeUtils.generateTrees(nodeList);
            if (CollectionUtils.isNotEmpty(tree)) {
                BomTreeBO bomTreeBO = new BomTreeBO();
                bomTreeBO.setRoot(tree.get(0));
                bomTreeList.add(bomTreeBO);
            }
        } else {
            List<BomNodePO> rootNodeList = new ArrayList<>();
            BomNodePO seedNode = Optional.ofNullable(parentNode).orElseGet(() -> {
                BomNodePO node = new BomNodePO();
                node.setMaterialCode(requestBO.getMaterialCode());
                node.setLogicalPlantNo(requestBO.getLogicalPlantNo());
                node.setTenantId(requestBO.getTenantId());
                return node;
            });
            generateRootNodeList(seedNode, rootNodeList, requestBO.isShowEnable());
            rootNodeList = rootNodeList.stream().distinct().collect(Collectors.toList());
            for (BomNodePO rootNodePO : rootNodeList) {
                HierarchicalBomNodeBO rootNode = BomCaseConvertor.toHierarchicalBO(rootNodePO);
                generateNodeList(rootNode, 0L, 1L, nodeList, requestBO.isShowLeaf(), requestBO.isShowEnable());
                List<HierarchicalBomNodeBO> tree = TreeUtils.generateTrees(nodeList);
                if (CollectionUtils.isNotEmpty(tree)) {
                    BomTreeBO bomTreeBO = new BomTreeBO();
                    bomTreeBO.setRoot(tree.get(0));
                    bomTreeList.add(bomTreeBO);
                }
            }
        }
        return bomTreeList;
    }

    private void generateNodeList(HierarchicalBomNodeBO node, Long parentId, long parentTreeAmount,
                                   List<HierarchicalBomNodeBO> nodeList, boolean showLeaf, boolean showEnable) {
        HierarchicalBomNodeBO parentNode = checkIsParent(node);
        if (Objects.nonNull(parentNode)) {
            BomCasePO casePO = bomCaseMapper.selectByPrimaryKey(parentNode.getBomCaseId());
            if (Objects.isNull(casePO)) {
                return;
            }
            parentNode.setCode(casePO.getCode());
            parentNode.setName(casePO.getName());
            parentNode.setPid(parentId);
            parentNode.setAmount(node.getAmount());
            parentNode.setTreeAmount(node.getAmount() * parentTreeAmount);
            if (showEnable && Objects.equals(casePO.getStatus(), BomCaseStatusEnum.OFF.getCode())) {
                if (parentId > 0) {
                    nodeList.add(parentNode);
                }
                return;
            }
            nodeList.add(parentNode);
            BomNodePO nodeCondition = new BomNodePO();
            nodeCondition.setBomCaseId(parentNode.getBomCaseId());
            nodeCondition.setType(BomNodeTypeEnum.CHILD.getCode());
            List<BomNodePO> bomNodePOList = queryChildren(BomCaseConvertor.toNodePO(parentNode));
            for (BomNodePO nodePO : bomNodePOList) {
                HierarchicalBomNodeBO childNode = BomCaseConvertor.toHierarchicalBO(nodePO);
                generateNodeList(childNode, parentNode.getId(), parentNode.getTreeAmount(), nodeList, showLeaf, showEnable);
            }
        } else if (showLeaf) {
            node.setPid(parentId);
            node.setTreeAmount(node.getAmount() * parentTreeAmount);
            nodeList.add(node);
        }
    }

    private void generateRootNodeList(BomNodePO nodePO, List<BomNodePO> rootNodeList, boolean showEnable) {
        List<BomNodePO> parentNodes = checkIsParentList(nodePO, showEnable);
        if (CollectionUtils.isEmpty(parentNodes)) {
            return;
        }
        for (BomNodePO parentNode : parentNodes) {
            if (Objects.nonNull(parentNode)) {
                List<BomNodePO> asChildrenNodeList = queryNodeAsChild(parentNode, showEnable);
                if (CollectionUtils.isEmpty(asChildrenNodeList)) {
                    rootNodeList.add(parentNode);
                } else {
                    for (BomNodePO childNode : asChildrenNodeList) {
                        generateRootNodeList(childNode, rootNodeList, showEnable);
                    }
                }
            }
        }
    }

    private HierarchicalBomNodeBO checkIsParent(HierarchicalBomNodeBO nodeBO) {
        BomNodePO parentNode = getBomParentInfo(nodeBO.getTenantId(),
                nodeBO.getLogicalPlantNo(), nodeBO.getMaterialCode());
        if (Objects.isNull(parentNode)) {
            return null;
        }
        return BomCaseConvertor.toHierarchicalBO(parentNode);
    }

    private List<BomNodePO> checkIsParentList(BomNodePO nodeBO, boolean showEnable) {
        BomNodePO parentNode = getBomParentInfo(nodeBO.getTenantId(),
                nodeBO.getLogicalPlantNo(), nodeBO.getMaterialCode());
        BomNodePO condition = new BomNodePO();
        condition.setMaterialCode(nodeBO.getMaterialCode());
        condition.setLogicalPlantNo(nodeBO.getLogicalPlantNo());
        condition.setTenantId(nodeBO.getTenantId());
        if (Objects.isNull(parentNode)) {
            return queryNodeAsChild(condition, showEnable);
        } else {
            if (showEnable && Objects.equals(parentNode.getStatus(), BomCaseStatusEnum.OFF.getCode())) {
                return queryNodeAsChild(condition, showEnable);
            }
            return Lists.newArrayList(parentNode);
        }
    }

    // ==================== Redis 缓存操作 ====================

    private void addBomTreeCache(BomNodePO parent, List<BomNodePO> children) {
        String parentInfoKey = BomRedisConstant.getBomParentInfoKey(
                parent.getTenantId(), parent.getLogicalPlantNo(), parent.getMaterialCode());
        setCacheValue(parentInfoKey, JSON.toJSONString(parent), BomRedisConstant.ONE_DAY);

        String childrenKey = BomRedisConstant.getBomChildrenKey(
                parent.getTenantId(), parent.getLogicalPlantNo(), parent.getMaterialCode());
        setCacheValue(childrenKey, JSON.toJSONString(children), BomRedisConstant.ONE_DAY);

        for (BomNodePO child : children) {
            String parentKey = BomRedisConstant.getBomParentKey(
                    child.getTenantId(), child.getLogicalPlantNo(), child.getMaterialCode());
            setCacheValue(parentKey, JSON.toJSONString(parent), BomRedisConstant.ONE_DAY);
        }
    }

    private void deleteBomTreeCache(long bomCaseId) {
        BomNodePO condition = new BomNodePO();
        condition.setBomCaseId(bomCaseId);
        List<BomNodePO> bomNodes = bomNodeMapper.queryByCondition(condition);
        if (!CollectionUtils.isEmpty(bomNodes)) {
            for (BomNodePO node : bomNodes) {
                if (node.getType().equals(BomNodeTypeEnum.PARENT.getCode())) {
                    String childrenKey = BomRedisConstant.getBomChildrenKey(
                            node.getTenantId(), node.getLogicalPlantNo(), node.getMaterialCode());
                    deleteCacheKey(childrenKey);
                    String parentInfoKey = BomRedisConstant.getBomParentInfoKey(
                            node.getTenantId(), node.getLogicalPlantNo(), node.getMaterialCode());
                    deleteCacheKey(parentInfoKey);
                } else {
                    String parentKey = BomRedisConstant.getBomParentKey(
                            node.getTenantId(), node.getLogicalPlantNo(), node.getMaterialCode());
                    deleteCacheKey(parentKey);
                }
            }
        }
    }

    private List<BomNodePO> queryNodeAsChild(BomNodePO nodePO, boolean showEnable) {
        List<BomNodePO> result = new ArrayList<>();
        String parentKey = BomRedisConstant.getBomParentKey(
                nodePO.getTenantId(), nodePO.getLogicalPlantNo(), nodePO.getMaterialCode());
        String value = getCacheValue(parentKey);
        if (Objects.equals(value, BomRedisConstant.NULL_STRING)) {
            return Lists.newArrayList();
        }
        if (StringUtils.isNotEmpty(value)) {
            BomNodePO parentNode = JSON.parseObject(value, BomNodePO.class);
            result.add(parentNode);
        } else {
            BomNodePO condition = new BomNodePO();
            condition.setMaterialCode(nodePO.getMaterialCode());
            condition.setLogicalPlantNo(nodePO.getLogicalPlantNo());
            condition.setTenantId(nodePO.getTenantId());
            List<BomNodePO> bomNodePOList = bomNodeMapper.queryNodeAsChild(condition);
            if (CollectionUtils.isNotEmpty(bomNodePOList)) {
                result.addAll(bomNodePOList);
                setCacheValue(parentKey, JSON.toJSONString(bomNodePOList.get(0)), null);
            } else {
                setCacheValue(parentKey, BomRedisConstant.NULL_STRING, null);
            }
        }
        if (showEnable) {
            result = result.stream()
                    .filter(b -> Objects.equals(b.getStatus(), BomCaseStatusEnum.ON.getCode()))
                    .collect(Collectors.toList());
        }
        return result;
    }

    private List<BomNodePO> queryChildren(BomNodePO parentNode) {
        List<BomNodePO> children = new ArrayList<>();
        String childrenKey = BomRedisConstant.getBomChildrenKey(
                parentNode.getTenantId(), parentNode.getLogicalPlantNo(), parentNode.getMaterialCode());
        String value = getCacheValue(childrenKey);
        if (Objects.equals(BomRedisConstant.NULL_STRING, value)) {
            return children;
        }
        if (StringUtils.isNotEmpty(value)) {
            children = JSON.parseArray(value, BomNodePO.class);
            if (CollectionUtils.isNotEmpty(children)) {
                return children;
            }
        }
        BomNodePO condition = new BomNodePO();
        condition.setBomCaseId(parentNode.getBomCaseId());
        condition.setType(BomNodeTypeEnum.CHILD.getCode());
        List<BomNodePO> bomNodePOList = bomNodeMapper.queryByCondition(condition);
        if (CollectionUtils.isNotEmpty(bomNodePOList)) {
            setCacheValue(childrenKey, JSON.toJSONString(bomNodePOList), BomRedisConstant.ONE_DAY);
        } else {
            setCacheValue(childrenKey, BomRedisConstant.NULL_STRING, BomRedisConstant.ONE_DAY);
        }
        return bomNodePOList;
    }

    private BomNodePO getBomParentInfo(String tenantId, String logicalPlantNo, String materialCode) {
        String key = BomRedisConstant.getBomParentInfoKey(tenantId, logicalPlantNo, materialCode);
        String value = getCacheValue(key);
        if (Objects.equals(value, BomRedisConstant.NULL_STRING)) {
            return null;
        }
        if (StringUtils.isNotEmpty(value)) {
            return JSON.parseObject(value, BomNodePO.class);
        }
        BomNodePO condition = new BomNodePO();
        condition.setMaterialCode(materialCode);
        condition.setLogicalPlantNo(logicalPlantNo);
        condition.setTenantId(tenantId);
        condition.setType(BomNodeTypeEnum.PARENT.getCode());
        BomNodePO parentNode = bomNodeMapper.querySingleByCondition(condition);
        if (Objects.isNull(parentNode)) {
            setCacheValue(key, BomRedisConstant.NULL_STRING, null);
            return null;
        } else {
            setCacheValue(key, JSON.toJSONString(parentNode), null);
            return parentNode;
        }
    }

    // ==================== Redisson 缓存辅助方法 ====================

    private String getCacheValue(String key) {
        try {
            RBucket<String> bucket = redissonClient.getBucket(key);
            return bucket.get();
        } catch (Exception e) {
            log.warn("Redis get failed for key: {}", key, e);
            return null;
        }
    }

    private void setCacheValue(String key, String value, Long ttlSeconds) {
        try {
            RBucket<String> bucket = redissonClient.getBucket(key);
            if (ttlSeconds != null && ttlSeconds > 0) {
                bucket.set(value, ttlSeconds, TimeUnit.SECONDS);
            } else {
                bucket.set(value);
            }
        } catch (Exception e) {
            log.warn("Redis set failed for key: {}", key, e);
        }
    }

    private void deleteCacheKey(String key) {
        try {
            redissonClient.getBucket(key).delete();
        } catch (Exception e) {
            log.warn("Redis delete failed for key: {}", key, e);
        }
    }

    // ==================== 序列号生成（Stub） ====================

    /**
     * TODO: 组N - 替换为真实的 SequenceFactory 实现
     * Placeholder: uses time-based code for now
     */
    private String generateBomCode(String tenantId) {
        // TODO: replace with SequenceFactory after 组N migration
        return "BOM_NO" + String.format("%07d", System.currentTimeMillis() % 10000000);
    }
}
