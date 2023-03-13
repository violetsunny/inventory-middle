package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.StorageLocation;
import com.inventory.middle.domain.model.types.StorageLocationId;
import com.inventory.middle.domain.repository.StorageLocationRepository;
import com.inventory.middle.infra.persistence.convertor.StorageLocationConvertor;
import com.inventory.middle.infra.persistence.entity.StorageLocationDo;
import com.inventory.middle.infra.persistence.mapper.StorageLocationMapper;
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
 * 存储地点表Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:24
 */
@Repository
public class StorageLocationRepositoryImpl extends ServiceImpl<StorageLocationMapper, StorageLocationDo> implements StorageLocationRepository, IService<StorageLocationDo> {

	@Resource
	private StorageLocationConvertor convertor;

	@Override
	public PageResponse<StorageLocation> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<StorageLocationDo> page = baseMapper.queryPage(new PlusPageQuery<StorageLocationDo>(pageQuery).getPage(params),params);
		List<StorageLocationDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<StorageLocation> dtoList = records.stream().map(convertor::toStorageLocation).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public StorageLocation findById(StorageLocationId id) {
		StorageLocationDo storagelocationDo = baseMapper.findById(id.get());
		return convertor.toStorageLocation(storagelocationDo);
	}

	@Override
	public boolean store(StorageLocation storagelocation) {
		StorageLocationDo storagelocationDo = convertor.fromStorageLocation(storagelocation);
		return this.saveOrUpdate(storagelocationDo);
	}

	@Override
	public boolean update(StorageLocation storagelocation) {
		StorageLocationDo storagelocationDo = convertor.fromStorageLocation(storagelocation);
		return this.saveOrUpdate(storagelocationDo);
	}

	@Override
	public boolean delete(List<StorageLocationId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
