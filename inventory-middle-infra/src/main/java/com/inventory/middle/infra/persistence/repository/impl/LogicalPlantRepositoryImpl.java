package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.LogicalPlant;
import com.inventory.middle.domain.model.types.LogicalPlantId;
import com.inventory.middle.domain.repository.LogicalPlantRepository;
import com.inventory.middle.infra.persistence.convertor.LogicalPlantConvertor;
import com.inventory.middle.infra.persistence.entity.LogicalPlantDo;
import com.inventory.middle.infra.persistence.mapper.LogicalPlantMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import top.kdla.framework.dto.PageQuery;
import top.kdla.framework.dto.PageResponse;
import top.kdla.framework.infra.dal.mybatis.util.PlusPageQuery;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 逻辑仓库表Repository实现类
 */
@Repository
public class LogicalPlantRepositoryImpl extends ServiceImpl<LogicalPlantMapper, LogicalPlantDo> implements LogicalPlantRepository, IService<LogicalPlantDo> {

    @Resource
    private LogicalPlantConvertor convertor;

    @Override
    public PageResponse<LogicalPlant> queryPage(PageQuery pageQuery, Map<String, Object> params) {
        IPage<LogicalPlantDo> page = baseMapper.queryPage(new PlusPageQuery<LogicalPlantDo>(pageQuery).getPage(params), params);
        List<LogicalPlantDo> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return PageResponse.of(page.getSize(), page.getCurrent());
        }
        List<LogicalPlant> dtoList = records.stream().map(convertor::toLogicalPlant).collect(Collectors.toList());
        return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
    }

    @Override
    public LogicalPlant findById(LogicalPlantId id) {
        LogicalPlantDo doObj = baseMapper.findById(id.get());
        return convertor.toLogicalPlant(doObj);
    }

    @Override
    public LogicalPlant findByLogicalPlantNo(String logicalPlantNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("logicalPlantNo", logicalPlantNo);
        LogicalPlantDo doObj = baseMapper.queryEntity(params);
        return convertor.toLogicalPlant(doObj);
    }

    @Override
    public List<LogicalPlant> listByIdsOrNos(String tenantId, List<Long> idList, List<String> noList) {
        Map<String, Object> params = new HashMap<>();
        if (tenantId != null) {
            params.put("tenantId", tenantId);
        }
        if (!CollectionUtils.isEmpty(idList)) {
            params.put("idList", idList);
        }
        if (!CollectionUtils.isEmpty(noList)) {
            params.put("noList", noList);
        }
        List<LogicalPlantDo> doList = baseMapper.queryList(params);
        if (CollectionUtils.isEmpty(doList)) {
            return Collections.emptyList();
        }
        return doList.stream().map(convertor::toLogicalPlant).collect(Collectors.toList());
    }

    @Override
    public boolean store(LogicalPlant logicalplant) {
        LogicalPlantDo doObj = convertor.fromLogicalPlant(logicalplant);
        return this.saveOrUpdate(doObj);
    }

    @Override
    public boolean update(LogicalPlant logicalplant) {
        LogicalPlantDo doObj = convertor.fromLogicalPlant(logicalplant);
        return this.saveOrUpdate(doObj);
    }

    @Override
    public boolean delete(List<LogicalPlantId> ids) {
        List<Long> tempIds = new ArrayList<>();
        ids.forEach(tempId -> tempIds.add(tempId.get()));
        return this.removeByIds(tempIds);
    }

    @Override
    public List<LogicalPlant> listByWarehouseNo(String warehouseNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("warehouseNo", warehouseNo);
        List<LogicalPlantDo> doList = baseMapper.queryList(params);
        if (CollectionUtils.isEmpty(doList)) return Collections.emptyList();
        return doList.stream().map(convertor::toLogicalPlant).collect(Collectors.toList());
    }

    /**
     * 按外部仓库编码查询逻辑仓（plan 迁移：queryLogicalPlantByOutPlantNo）
     * 注意：原 scm-plan-management 约定用 logicalPlantName 存储外部仓编码，此处沿用该约定
     */
    @Override
    public LogicalPlant findByOutPlantNo(String outPlantNo, String tenantId) {
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<LogicalPlantDo> wrapper =
                com.baomidou.mybatisplus.core.toolkit.Wrappers.<LogicalPlantDo>lambdaQuery()
                        .eq(LogicalPlantDo::getLogicalPlantName, outPlantNo)
                        .eq(LogicalPlantDo::getDeleted, 0);
        if (tenantId != null) {
            wrapper.eq(LogicalPlantDo::getTenantId, tenantId);
        }
        LogicalPlantDo doObj = this.getOne(wrapper, false);
        return convertor.toLogicalPlant(doObj);
    }
}
