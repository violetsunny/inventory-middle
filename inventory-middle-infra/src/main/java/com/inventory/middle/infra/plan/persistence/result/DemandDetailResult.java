package com.inventory.middle.infra.plan.persistence.result;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DemandDetailResult implements Serializable {

    private static final long serialVersionUID = -1242364866108053632L;

    private String materialCode;

    private String materialDesc;

    private String materialUnit;

    private String logicalPlantNo;

    private List<SingleDemandResult> demandList;

    public void append(SingleDemandResult single) {
        if (null == demandList) {
            demandList = Lists.newArrayList();
        }
        demandList.add(single);
    }
}
