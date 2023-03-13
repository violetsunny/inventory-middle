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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 逻辑仓库表Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:11
 */
@Repository
public class LogicalPlantRepositoryImpl extends ServiceImpl<LogicalPlantMapper, LogicalPlantDo> implements LogicalPlantRepository, IService<LogicalPlantDo> {

	@Resource
	private LogicalPlantConvertor convertor;

	@Override
	public PageResponse<LogicalPlant> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<LogicalPlantDo> page = baseMapper.queryPage(new PlusPageQuery<LogicalPlantDo>(pageQuery).getPage(params),params);
		List<LogicalPlantDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<LogicalPlant> dtoList = records.stream().map(convertor::toLogicalPlant).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public LogicalPlant findById(LogicalPlantId id) {
		LogicalPlantDo logicalplantDo = baseMapper.findById(id.get());
		return convertor.toLogicalPlant(logicalplantDo);
	}

	@Override
	public boolean store(LogicalPlant logicalplant) {
		LogicalPlantDo logicalplantDo = convertor.fromLogicalPlant(logicalplant);
		return this.saveOrUpdate(logicalplantDo);
	}

	@Override
	public boolean update(LogicalPlant logicalplant) {
		LogicalPlantDo logicalplantDo = convertor.fromLogicalPlant(logicalplant);
		return this.saveOrUpdate(logicalplantDo);
	}

	@Override
	public boolean delete(List<LogicalPlantId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
