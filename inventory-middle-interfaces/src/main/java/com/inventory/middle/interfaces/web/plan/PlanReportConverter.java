package com.inventory.middle.interfaces.web.plan;

import com.inventory.middle.interfaces.web.plan.dto.MaterialPlanQueryReqDTO;
import com.inventory.middle.interfaces.web.plan.vo.*;
import com.inventory.middle.client.plan.config.dto.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 计划报表转换器
 *
 * @author Danny.Lee
 * @date 2021/10/15
 */
@Mapper(componentModel = "spring")
public interface PlanReportConverter {

    /**
     * 报表转换
     */
    MaterialPlanReportVO convertReport(MaterialPlanReportDTO report);

    /**
     * 报表标题转换
     */
    default MaterialPlanReportTitleVO convertReportTitle(MaterialPlanReportTitleDTO reportTitle){
        if ( reportTitle == null ) {
            return null;
        }
        MaterialPlanReportTitleVO title = new MaterialPlanReportTitleVO();
        title.setMaterialCode( reportTitle.getMaterialCode() );
        title.setMaterialDesc( reportTitle.getMaterialDesc() );
        title.setPlanVersion( reportTitle.getPlanVersion() );
        if(null != reportTitle.getCreateDate()){
            title.setCreateDate(reportTitle.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        title.setOperator( reportTitle.getOperator() );
        return title;
    }

    /**
     * 报表参数转换
     */
    MaterialPlanReportParamVO convertReportParam(MaterialPlanReportParamDTO reportParam);

    /**
     * 报表时段转换
     */
    MaterialPlanReportPeriodVO convertReportPeriod(MaterialPlanReportPeriodDTO reportPeriod);

    /**
     * 报表期初指标转换
     */
    MaterialPlanReportInitIndicatorVO convertReportInitIndicator(MaterialPlanReportInitIndicatorDTO reportIndicator);

    /**
     * 报表指标转换
     */
    MaterialPlanReportIndicatorVO convertReportIndicator(MaterialPlanReportIndicatorDTO reportIndicator);

    /**
     * 物料计划转换
     */
    MaterialPlanVO convertMaterialPlan(MaterialPlanInstanceDTO materialPlanInstance);

    /**
     * 物料计划集合转换
     */
    List<MaterialPlanVO> convertMaterialPlans(List<MaterialPlanInstanceDTO> materialPlanInstances);

    /**
     * 计划产出结果转换
     */
    PlanGenerateResultVO convertPlanInstance(PlanInstanceDTO planInstance);

    /**
     * 物料计划分页查询转换
     */
    default MaterialPlanInstanceQueryRequest convertMaterialPlanQueryRequest(MaterialPlanQueryReqDTO request) {
        MaterialPlanInstanceQueryRequest condition = new MaterialPlanInstanceQueryRequest();
        condition.setPlanCode(request.getPlanCode());
        condition.setPlanType(request.getPlanType());
        condition.setCalStartTimeStart(request.getCalStartTimeStart());
        condition.setCalStartTimeEnd(request.getCalStartTimeEnd());
        condition.setPlanVersion(request.getPlanVersion());
        if (StringUtils.isNoneBlank(request.getMaterialCode())) {
            condition.setMaterialCodes(Lists.newArrayList(request.getMaterialCode()));
        }
        if (StringUtils.isNoneBlank(request.getLogicalPlantNo())) {
            condition.setLogicalPlantNos(Lists.newArrayList(request.getLogicalPlantNo()));
        }
        condition.setPageNum(request.getPageNum());
        condition.setPageSize(request.getPageSize());
        return condition;
    }

    MaterialPlanBomTreeVO convertMaterialBomTree(MaterialPlanInstanceBomNodeDTO materialPlanInstanceBomDTO);
}
