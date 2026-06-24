package com.inventory.middle.application.plan.demand.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.inventory.middle.client.plan.demand.dto.DemandPlanMaterialPeriodDTO;
import com.inventory.middle.client.plan.demand.dto.DemandPlanPeriodInfoDTO;
import com.inventory.middle.domain.common.exception.BusinessException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Getter
public class DemandPlanMaterialImportExcelListener extends AnalysisEventListener<Map<Integer, String>> {

    private final int importBatchSize;
    private final List<DemandPlanMaterialPeriodDTO> periodList = new ArrayList<>();
    private final Map<Integer, String> headMap = new HashMap<>();
    private final List<Map<Integer, String>> dataList = new ArrayList<>();

    public DemandPlanMaterialImportExcelListener(int importBatchSize) {
        this.importBatchSize = importBatchSize;
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        dataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有的数据存储完毕,数据总数:{}", dataList.size());
        for (Map<Integer, String> row : dataList) {
            DemandPlanMaterialPeriodDTO periodDTO = new DemandPlanMaterialPeriodDTO();
            List<DemandPlanPeriodInfoDTO> planPeriodInfoList = new ArrayList<>();
            for (Integer headIndex : headMap.keySet()) {
                if (Objects.equals(headIndex, 0)) {
                    periodDTO.setMaterialCode(row.get(headIndex));
                } else {
                    DemandPlanPeriodInfoDTO infoDTO = new DemandPlanPeriodInfoDTO();
                    infoDTO.setPlanAmount(row.get(headIndex));
                    infoDTO.setPlanPeriod(headMap.get(headIndex));
                    planPeriodInfoList.add(infoDTO);
                }
            }
            periodDTO.setPlanPeriodInfoList(planPeriodInfoList);
            periodList.add(periodDTO);
        }
        log.info("所有的数据解析完毕,解析结果数量:{}", periodList.size());
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        int totalCount = context.readSheetHolder().getApproximateTotalRowNumber() - 1;
        if (importBatchSize < totalCount) {
            throw new BusinessException(String.format("文件超过条数限制,最多不能超过%s条", importBatchSize));
        }
        this.headMap.putAll(headMap);
    }
}
