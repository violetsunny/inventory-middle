package com.inventory.middle.domain.service.impl;

import com.inventory.middle.domain.service.MaterialLogicalPlantRefDomainService;
import com.inventory.middle.domain.model.bo.material.CreateMaterialLogicalPlantRefBO;
import com.inventory.middle.domain.model.bo.material.MaterialLogicalPlantRefBO;
import com.inventory.middle.domain.model.bo.material.PageQueryMaterialPlantRefRequestBO;
import com.inventory.middle.domain.model.bo.material.QueryMaterialLogicalPlantRefBO;
import com.inventory.middle.domain.service.MaterialLogicalPlantRefCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.kdla.framework.dto.MultiResponse;
import top.kdla.framework.dto.PageResponse;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dongguo.tao
 * @date 2021-10-13 21:23:44
 */
@Service
@Slf4j
public class MaterialLogicalPlantRefDomainServiceImpl implements MaterialLogicalPlantRefDomainService {

    @Resource
    private MaterialLogicalPlantRefCoreService refCoreService;

    @Override
    public boolean batchCreate(List<CreateMaterialLogicalPlantRefBO> creteBOList) {
        return refCoreService.batchCreate(creteBOList);
    }

    @Override
    public List<MaterialLogicalPlantRefBO> getListByCondition(QueryMaterialLogicalPlantRefBO queryBO) {
        return refCoreService.getListByCondition(queryBO);
    }

    @Override
    public PageResponse<MaterialLogicalPlantRefBO> pageQuery(PageQueryMaterialPlantRefRequestBO bo) {
        List<MaterialLogicalPlantRefBO> list = refCoreService.pageQuery(bo);
        return PageResponse.of(list, list.size(), bo.getPageSize() > 0 ? bo.getPageSize() : 20, bo.getPageNum() > 0 ? bo.getPageNum() : 1);
    }
}

