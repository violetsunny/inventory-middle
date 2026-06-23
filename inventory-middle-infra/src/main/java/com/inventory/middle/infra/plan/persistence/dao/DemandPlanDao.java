package com.inventory.middle.infra.plan.persistence.dao;

import com.inventory.middle.domain.plan.common.enums.DemandPlanStatusEnum;
import com.inventory.middle.infra.plan.persistence.condition.demand.DemandPlanSelectReqCondition;
import com.inventory.middle.infra.plan.persistence.result.DemandPlanDetailResult;
import com.inventory.middle.infra.plan.persistence.mapper.DemandPlanMapper;
import com.inventory.middle.infra.plan.persistence.mapper.DemandPlanMaterialDetailMapper;
import com.inventory.middle.infra.plan.persistence.mapper.DemandPlanMaterialMapper;
import com.inventory.middle.infra.plan.persistence.mapper.DemandPlanMaterialPeriodMapper;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialDetailPO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialPO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanMaterialPeriodPO;
import com.inventory.middle.infra.plan.persistence.entity.DemandPlanPO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhouxinzhong
 * @date 2021/9/28 9:48
 */
@Component
public class DemandPlanDao {

    @Autowired
    private DemandPlanMapper demandPlanMapper;

    @Autowired
    private DemandPlanMaterialPeriodMapper demandPlanMaterialPeriodMapper;

    @Autowired
    private DemandPlanMaterialMapper demandPlanMaterialMapper;

    /**
     * 插入记录
     * @param record
     * @return
     */
    public int insert(DemandPlanPO record){
        return demandPlanMapper.insertSelective(record);
    }

    public int update(DemandPlanPO record){
        return demandPlanMapper.updateByPrimaryKeySelective(record);
    }

    public DemandPlanPO selectById(Long id){
        return demandPlanMapper.selectByPrimaryKey(id);
    }

    public DemandPlanPO selectPlanInfo(Long id, String tenantId) {
        return demandPlanMapper.selectPlanInfo(id, tenantId);
    }

    public List<DemandPlanPO> selectByIds(List<Long> ids) {
        return demandPlanMapper.selectByIds(ids);
    }

    @Transactional
    public int update(DemandPlanPO record,int targetStatus){

        int result =  demandPlanMapper.updateByPrimaryKeySelective(record);
        DemandPlanMaterialPO materialCondition = new DemandPlanMaterialPO();
        materialCondition.setDemandPlanId(record.getId());

        DemandPlanMaterialPeriodPO materialPeriodCondition = new DemandPlanMaterialPeriodPO();
        materialPeriodCondition.setDemandPlanId(record.getId());

        if (targetStatus == DemandPlanStatusEnum.ON.getCode()){
            materialCondition.setStatus(0);
            materialPeriodCondition.setStatus(0);
            demandPlanMaterialMapper.updateStatusByCondition(materialCondition,1);
            demandPlanMaterialPeriodMapper.updateStatusByCondition(materialPeriodCondition,1);
        }else {
            //关闭之后,看板数据需要置为无效
            materialCondition.setStatus(1);
            materialPeriodCondition.setStatus(1);
            demandPlanMaterialMapper.updateStatusByCondition(materialCondition,0);
            demandPlanMaterialPeriodMapper.updateStatusByCondition(materialPeriodCondition,0);
        }
        return result;
    }

    public List<DemandPlanPO> selectByPage(DemandPlanSelectReqCondition reqCondition) {
        return demandPlanMapper.selectByPage(reqCondition);
    }

    public List<DemandPlanPO> selectByLogicalPlantNos(List<String> logicalPlantNos, String tenantId) {
        return demandPlanMapper.selectByLogicalPlantNos(logicalPlantNos, tenantId);
    }

    public DemandPlanPO querySingleByCondition(DemandPlanSelectReqCondition condition) {
        return demandPlanMapper.querySingleByCondition(condition);
    }
    public List<DemandPlanDetailResult> selectMaterialsInfo(Long demandPlanId, String tenantId) {
        return demandPlanMaterialMapper.selectMaterialsInfo(demandPlanId, tenantId);
    }

    /**
     * 判断是否有日期生效中的物料计划
     * @param tenantId
     * @param logicalPlantNo
     * @param materialList
     * @return
     */
    public boolean checkDuplicate(String tenantId, int demandType,String logicalPlantNo,List<String> materialList){
        if (CollectionUtils.isEmpty(materialList)){
            return false;
        }
        List<String> list = demandPlanMapper.checkDuplicate(tenantId,demandType,logicalPlantNo,materialList);
        return CollectionUtils.isNotEmpty(list);
    }
}
