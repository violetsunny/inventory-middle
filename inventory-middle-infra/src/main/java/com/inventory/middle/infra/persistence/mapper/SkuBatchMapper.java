package com.inventory.middle.infra.persistence.mapper;

import com.inventory.middle.infra.persistence.entity.SkuBatchDo;
import com.inventory.middle.infra.persistence.entity.SkuBatchQueryPO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SkuBatchMapper {
    int insert(SkuBatchDo record);
    int insertOrUpdate(SkuBatchDo record);
    void batchInsertOrUpdate(List<SkuBatchDo> list);
    List<SkuBatchDo> list(SkuBatchQueryPO param);
    int count(SkuBatchQueryPO param);
}
