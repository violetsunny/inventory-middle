package com.inventory.middle.infra.plan.persistence.dao;

import com.alibaba.fastjson.JSONObject;
import com.inventory.middle.domain.plan.common.constants.PlanCommonConstants;
import com.inventory.middle.domain.plan.common.enums.DemandStatusEnum;
import com.inventory.middle.domain.plan.common.enums.IsDeleteEnum;
import com.inventory.middle.domain.plan.common.enums.OrderPlanTypeEnum;
import com.inventory.middle.domain.plan.common.enums.ProjectPlanFlagEnum;
import com.inventory.middle.domain.plan.common.ex.Checker;
import com.inventory.middle.infra.plan.util.DateUtils;
import com.inventory.middle.infra.plan.persistence.condition.demand.OrderDemandQueryCondition;
import com.inventory.middle.infra.plan.persistence.mapper.*;
import com.inventory.middle.infra.plan.persistence.result.DemandDetailResult;
import com.inventory.middle.infra.plan.persistence.mapper.DemandPlanMapper;
import com.inventory.middle.infra.plan.persistence.mapper.DemandPlanMaterialDetailMapper;
import com.inventory.middle.infra.plan.persistence.mapper.DemandPlanMaterialMapper;
import com.inventory.middle.infra.plan.persistence.mapper.DemandPlanMaterialPeriodMapper;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialPO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialPeriodPO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanPO;
import com.inventory.middle.infra.plan.persistence.entity.OrderDemandPO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author:Vincent.Xiao
 * @date:2021/9/29 15:22
 */
@Component
@Slf4j
public class DemandPlanMaterialDao {

    @Autowired
    private DemandPlanMaterialMapper demandPlanMaterialMapper;

    @Autowired
    private DemandPlanMaterialPeriodMapper demandPlanMaterialPeriodMapper;

    @Autowired
    private DemandPlanMaterialDetailMapper demandPlanMaterialDetailMapper;

    @Autowired
    private DemandPlanMapper demandPlanMapper;

    @Resource
    private com.inventory.middle.infra.plan.persistence.mapper.OrderDemandMapper orderDemandMapper;

    @Transactional(rollbackFor = Exception.class)
    public void insertMaterialPeriod(Long demandPlanId, List<DemandPlanMaterialPO> materialPOList, Map<String, List<DemandPlanMaterialPeriodPO>> materialPeriodMap) {

        process(demandPlanId, materialPOList, materialPeriodMap);
    }

    private void process(Long demandPlanId, List<DemandPlanMaterialPO> materialPOList, Map<String, List<DemandPlanMaterialPeriodPO>> materialPeriodMap) {
        List<DemandPlanMaterialPeriodPO> updateList = new ArrayList<>();
        List<DemandPlanMaterialPeriodPO> insertList = new ArrayList<>();
        for (DemandPlanMaterialPO po : materialPOList) {
            List<DemandPlanMaterialPeriodPO> periodPOList = getDemandPlanMaterialPeriodPOS(demandPlanId, po);
            if (CollectionUtils.isEmpty(periodPOList)) {
                po.setDemandPlanId(demandPlanId);
                //当前记录不存在
                demandPlanMaterialMapper.insertSelective(po);
                List<DemandPlanMaterialPeriodPO> materialPeriodPOS = materialPeriodMap.get(po.getMaterialCode());
                if (CollectionUtils.isNotEmpty(materialPeriodPOS)) {
                    materialPeriodPOS.forEach(demandPlanMaterialPeriodPO -> {
                        if (checkBeginTime(demandPlanMaterialPeriodPO.getPlanPeriod())) {
                            demandPlanMaterialPeriodPO.setDemandPlanId(po.getDemandPlanId());
                            demandPlanMaterialPeriodPO.setDemandPlanMaterialId(po.getId());
                            insertList.add(demandPlanMaterialPeriodPO);
                        }
                    });
                }
            } else {
                //物料对应的周期已存在，需要更新数量
                List<DemandPlanMaterialPeriodPO> materialPeriodPOS = materialPeriodMap.get(po.getMaterialCode());
                for (DemandPlanMaterialPeriodPO materialPeriodPO : materialPeriodPOS) {
                    Optional<DemandPlanMaterialPeriodPO> optional = periodPOList.stream().filter(periodPO -> {
                        return Objects.equals(periodPO.getPlanPeriod(), materialPeriodPO.getPlanPeriod());
                    }).findFirst();
                    if (optional.isPresent()) {
                        if (checkBeginTime(materialPeriodPO.getPlanPeriod())
                                && (!Objects.equals(materialPeriodPO.getPlanAmount(), optional.get().getPlanAmount())
                                || !Objects.equals(materialPeriodPO.getExtInfo(), optional.get().getExtInfo()))) {
                            materialPeriodPO.setId(optional.get().getId());
                            if (materialPeriodPO.getPlanAmount() == 0) {
                                materialPeriodPO.setDeleted(IsDeleteEnum.YES.getValue());
                            }
                            updateList.add(materialPeriodPO);
                        }
                    } else {
                        if (checkBeginTime(materialPeriodPO.getPlanPeriod())) {
                            materialPeriodPO.setDemandPlanId(periodPOList.get(0).getDemandPlanId());
                            materialPeriodPO.setDemandPlanMaterialId(periodPOList.get(0).getDemandPlanMaterialId());
                            if (materialPeriodPO.getPlanAmount() == 0) {
                                materialPeriodPO.setDeleted(IsDeleteEnum.YES.getValue());
                            }
                            insertList.add(materialPeriodPO);
                        }

                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(insertList)) {
            demandPlanMaterialPeriodMapper.batchInsert(insertList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            demandPlanMaterialPeriodMapper.updateBatch(updateList);
        }
        //批量完成之后，需要将没有关联的物料剔除
        deleteMaterial(demandPlanId,materialPOList);
    }


    /**
     * 如果物料已经没有具体的需求了，则将对应的物料删除
     *
     * @param demandPlanId
     * @param materialPOList
     */
    private void deleteMaterial(Long demandPlanId, List<DemandPlanMaterialPO> materialPOList) {
        if(CollectionUtils.isEmpty(materialPOList)){
            return;
        }
        List<String> materialCodeList = materialPOList.stream().map(DemandPlanMaterialPO::getMaterialCode).distinct().collect(Collectors.toList());
        List<DemandPlanMaterialPeriodPO> periodPOList = demandPlanMaterialPeriodMapper.queryByPlanIdAndMaterialCode(demandPlanId, materialCodeList);
        if (CollectionUtils.isEmpty(periodPOList)){
            //没有了，说明都要删
            demandPlanMaterialMapper.deleteByPlanIdAndMaterialCode(demandPlanId,materialCodeList);
        }
        List<String> existList = periodPOList.stream().map(DemandPlanMaterialPeriodPO::getMaterialCode).distinct().collect(Collectors.toList());
        materialCodeList.removeAll(existList);
        if (CollectionUtils.isNotEmpty(materialCodeList)){
            demandPlanMaterialMapper.deleteByPlanIdAndMaterialCode(demandPlanId,materialCodeList);
        }

    }

    /**
     * 根据条件查询
     *
     * @param demandPlanId
     * @param po
     * @return
     */
    private List<DemandPlanMaterialPeriodPO> getDemandPlanMaterialPeriodPOS(Long demandPlanId, DemandPlanMaterialPO po) {
        DemandPlanMaterialPeriodPO queryCondition = new DemandPlanMaterialPeriodPO();
        queryCondition.setDemandPlanId(Objects.isNull(demandPlanId) ? po.getDemandPlanId() : demandPlanId);
        queryCondition.setMaterialCode(po.getMaterialCode());
        queryCondition.setDeleted(IsDeleteEnum.NO.getValue());
        List<DemandPlanMaterialPeriodPO> periodPOList = demandPlanMaterialPeriodMapper.queryByCondition(queryCondition);
        return periodPOList;
    }


    @Transactional(rollbackFor = Exception.class)
    public void insertMaterialPeriod(DemandPlanPO demandPlanPO, List<DemandPlanMaterialPO> materialPOList, Map<String, List<DemandPlanMaterialPeriodPO>> materialPeriodMap) {
        demandPlanMapper.insertSelective(demandPlanPO);
        long id = demandPlanPO.getId();
        process(id, materialPOList, materialPeriodMap);
    }

    /**
     * 判断截至时间是否大于等于今天
     *
     * @param period
     * @return
     */
    boolean checkBeginTime(String period) {
        try {
            if (StringUtils.isEmpty(period)) {
                return false;
            }
            Date end = new Date();
            if (period.split(PlanCommonConstants.DELIMITER_HYPHEN).length == 1) {
                end = DateUtils.stringToDate(period.split(PlanCommonConstants.DELIMITER_HYPHEN)[0], DateUtils.YYYY_MM_DD_SLASH);
            } else if (period.split(PlanCommonConstants.DELIMITER_HYPHEN).length == 2) {
                end = DateUtils.stringToDate(period.split(PlanCommonConstants.DELIMITER_HYPHEN)[1], DateUtils.YYYY_MM_DD_SLASH);
            }
            if (!DateUtils.toLocalDate(new Date()).isAfter(DateUtils.toLocalDate(end))) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            log.error("checkBeginTime error", e);
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean cancelDemandPlanMaterial(DemandPlanMaterialPO po) {
        po.setStatus(DemandStatusEnum.INVALID.getCode());
        int result = demandPlanMaterialMapper.updateByPrimaryKeySelective(po);
        if (result == 0) {
            return false;
        }
        DemandPlanMaterialPeriodPO periodPO = new DemandPlanMaterialPeriodPO();
        periodPO.setDemandPlanMaterialId(po.getId());
        periodPO.setStatus(DemandStatusEnum.OFF.getCode());
        demandPlanMaterialPeriodMapper.updateStatusByCondition(periodPO, DemandStatusEnum.INVALID.getCode());
        return true;
    }

    @Data
    static class OrderDemandInfo {
        /**
         * 订单号
         */
        private String orderNo;

        /**
         * 行号
         */
        private String documentNo;

        /**
         * 物料编码
         */
        private String materialCode;

        /**
         * 物料描述
         */
        private String materialDesc;

        /**
         * 逻辑仓编码
         */
        private String logicalPlantNo;

        /**
         * 租户id
         */
        private String tenantId;

        /**
         * 计划日期
         */
        private Date planDate;

        /**
         * 计划数量
         */
        private Long amount;

        /**
         * 合规标识 0：计划外 1：计划内
         */
        private Integer planFlag;

        /**
         * 下发时间
         */
        private Date createTime;

        /**
         * 备注
         */
        private String remark;

        /**
         * 备货计划单号
         */
        private String stockUpPlanNo;
    }

    private void buildExtInfo(DemandPlanMaterialPeriodPO po, String tenantId) {
        // 需求订单汇总
        OrderDemandQueryCondition condition = new OrderDemandQueryCondition();
        condition.setOrderType(OrderPlanTypeEnum.PROJECT_ORDER.getCode());
        condition.setTenantId(tenantId);
        condition.setLogicalPlantNo(po.getLogicalPlantNo());
        condition.setMaterialCode(po.getMaterialCode());
        try {
            condition.setPlanDate(DateUtils.stringToDate(po.getPlanPeriod(), DateUtils.YYYY_MM_DD_SLASH));
        } catch (Exception e) {
            log.error("date parse error : {}", e.getMessage());
        }
        List<OrderDemandPO> orderPlans = orderDemandMapper.queryByCondition(condition);

        // 拓展信息追加
        JSONObject extInfo = new JSONObject();
        int planFlag = ProjectPlanFlagEnum.IN.getCode();
        List<OrderDemandInfo> orderInfoBOList = new ArrayList<>();
        for (OrderDemandPO orderPlan : orderPlans) {
            OrderDemandInfo temp = JSONObject.parseObject(orderPlan.getExtInfo(), OrderDemandInfo.class);
            orderInfoBOList.add(temp);
            planFlag = planFlag & temp.getPlanFlag();
        }
        JSONObject control = new JSONObject();
        control.put("planFlag", planFlag);
        control.put("enableObjection", planFlag == 0?1:0);
        extInfo.put("control", control);
        extInfo.put("source", orderInfoBOList);

        // 赋值
        po.setExtInfo(extInfo.toJSONString());
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean modifyDemandPlanMaterialAmount(DemandPlanMaterialPO materialPO,
                                                  List<DemandPlanMaterialPeriodPO> periodPOList,
                                                  List<OrderDemandPO> orderPlanPOList,
                                                  String tenantId) {
        // 需求类型为项目订单
        if (!CollectionUtils.isEmpty(orderPlanPOList)) {
            // 手动添加订单入需求订单表
            orderDemandMapper.batchInsert(orderPlanPOList);
            // 新增拓展信息
            periodPOList.forEach(po -> buildExtInfo(po, tenantId));
        }
        demandPlanMaterialMapper.updateByPrimaryKeySelective(materialPO);
        demandPlanMaterialPeriodMapper.updateBatch(periodPOList);
        return Boolean.TRUE;
    }

    public List<DemandPlanMaterialPO> selectValidMaterialByPlanId(Long planId) {
        return demandPlanMaterialMapper.selectValidMaterialByPlanId(planId);
    }

    public DemandPlanMaterialPO selectById(Long id) {
        return demandPlanMaterialMapper.selectByPrimaryKey(id);
    }

    public DemandPlanMaterialPO selectMaterialByCode(String materialCode, String logicalPlantNo, String tenantId) {
        return demandPlanMaterialMapper.selectMaterialByCode(materialCode, logicalPlantNo, tenantId);
    }

    public int updateDetailsStatusByCondition(DemandPlanMaterialPO condition, int targetStatus) {
        return demandPlanMaterialMapper.updateStatusByCondition(condition, targetStatus);
    }

    public List<DemandPlanMaterialPO> selectByCondition(DemandPlanMaterialPO record) {
        return demandPlanMaterialMapper.selectByCondition(record);
    }
}
