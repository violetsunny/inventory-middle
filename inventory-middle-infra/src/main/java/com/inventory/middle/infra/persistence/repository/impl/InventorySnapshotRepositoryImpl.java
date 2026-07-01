package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.InventorySnapshot;
import com.inventory.middle.domain.model.types.InventorySnapshotId;
import com.inventory.middle.domain.repository.InventorySnapshotRepository;
import com.inventory.middle.infra.persistence.convertor.InventorySnapshotConvertor;
import com.inventory.middle.infra.persistence.entity.InventorySnapshotDo;
import com.inventory.middle.infra.persistence.mapper.InventorySnapshotMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.infra.dal.mybatis.util.PlusPageQuery;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存快照Repository实现类
 */
@Repository
public class InventorySnapshotRepositoryImpl extends ServiceImpl<InventorySnapshotMapper, InventorySnapshotDo> implements InventorySnapshotRepository, IService<InventorySnapshotDo> {

    @Resource
    private InventorySnapshotConvertor convertor;

    @Override
    public PageResponse<InventorySnapshot> queryPage(PageQuery pageQuery, Map<String, Object> params) {
        IPage<InventorySnapshotDo> page = baseMapper.queryPage(new PlusPageQuery<InventorySnapshotDo>(pageQuery).getPage(params), params);
        List<InventorySnapshotDo> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return PageResponse.of(page.getSize(), page.getCurrent());
        }
        List<InventorySnapshot> dtoList = records.stream().map(convertor::toInventorySnapshot).collect(Collectors.toList());
        return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
    }

    @Override
    public InventorySnapshot findById(InventorySnapshotId id) {
        InventorySnapshotDo doObj = baseMapper.findById(id.get());
        return convertor.toInventorySnapshot(doObj);
    }

    @Override
    public List<InventorySnapshot> queryMaterialTotal(Map<String, Object> params) {
        List<InventorySnapshotDo> doList = baseMapper.queryMaterialTotal(params);
        if (CollectionUtils.isEmpty(doList)) {
            return Collections.emptyList();
        }
        return doList.stream().map(convertor::toInventorySnapshot).collect(Collectors.toList());
    }

    @Override
    public List<InventorySnapshot> queryByMaterialAndLogical(Map<String, Object> params) {
        List<InventorySnapshotDo> doList = baseMapper.queryByMaterialAndLogical(params);
        if (CollectionUtils.isEmpty(doList)) {
            return Collections.emptyList();
        }
        return doList.stream().map(convertor::toInventorySnapshot).collect(Collectors.toList());
    }

    @Override
    public List<InventorySnapshot> queryList(Map<String, Object> params) {
        List<InventorySnapshotDo> doList = baseMapper.queryList(params);
        if (CollectionUtils.isEmpty(doList)) {
            return Collections.emptyList();
        }
        return doList.stream().map(convertor::toInventorySnapshot).collect(Collectors.toList());
    }

    @Override
    public boolean store(InventorySnapshot inventorysnapshot) {
        InventorySnapshotDo doObj = convertor.fromInventorySnapshot(inventorysnapshot);
        return this.saveOrUpdate(doObj);
    }

    @Override
    public boolean update(InventorySnapshot inventorysnapshot) {
        InventorySnapshotDo doObj = convertor.fromInventorySnapshot(inventorysnapshot);
        return this.saveOrUpdate(doObj);
    }

    @Override
    public boolean delete(List<InventorySnapshotId> ids) {
        List<Long> tempIds = new ArrayList<>();
        ids.forEach(tempId -> tempIds.add(tempId.get()));
        return this.removeByIds(tempIds);
    }

    @Override
    public boolean disableById(Long id) {
        return this.update(Wrappers.<InventorySnapshotDo>lambdaUpdate()
                .eq(InventorySnapshotDo::getId, id)
                .set(InventorySnapshotDo::getDeleted, 1));
    }

    @Override
    public boolean adjustDown(Long id, BigDecimal number, Integer stockType) {
        String column;
        if (Integer.valueOf(2).equals(stockType)) {
            column = "damaged";
        } else if (Integer.valueOf(3).equals(stockType)) {
            column = "inspection";
        } else {
            column = "unrestricted";
        }
        return this.update(Wrappers.<InventorySnapshotDo>lambdaUpdate()
                .eq(InventorySnapshotDo::getId, id)
                .setSql(column + " = " + column + " - " + number.toPlainString()));
    }

    @Override
    public List<InventorySnapshot> queryBatchNoList(String materialCode, String logicalPlantNo, String storageLocationNo, String tenantId) {
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<InventorySnapshotDo> wrapper = Wrappers.<InventorySnapshotDo>lambdaQuery()
                .eq(InventorySnapshotDo::getMaterialCode, materialCode)
                .eq(InventorySnapshotDo::getLogicalPlantNo, logicalPlantNo)
                .eq(InventorySnapshotDo::getDeleted, 0);
        if (org.springframework.util.StringUtils.hasText(storageLocationNo)) {
            wrapper.eq(InventorySnapshotDo::getStorageLocationNo, storageLocationNo);
        }
        if (org.springframework.util.StringUtils.hasText(tenantId)) {
            wrapper.eq(InventorySnapshotDo::getTenantId, tenantId);
        }
        List<InventorySnapshotDo> doList = this.list(wrapper);
        if (CollectionUtils.isEmpty(doList)) {
            return Collections.emptyList();
        }
        return doList.stream().map(convertor::toInventorySnapshot).collect(Collectors.toList());
    }
}
