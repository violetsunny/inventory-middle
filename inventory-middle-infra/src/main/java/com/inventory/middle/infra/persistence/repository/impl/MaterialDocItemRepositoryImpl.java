package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.MaterialDocItem;
import com.inventory.middle.domain.model.types.MaterialDocItemId;
import com.inventory.middle.domain.repository.MaterialDocItemRepository;
import com.inventory.middle.infra.persistence.convertor.MaterialDocItemConvertor;
import com.inventory.middle.infra.persistence.entity.MaterialDocItemDo;
import com.inventory.middle.infra.persistence.mapper.MaterialDocItemMapper;
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
 * 物料凭证-itemRepository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:10
 */
@Repository
public class MaterialDocItemRepositoryImpl extends ServiceImpl<MaterialDocItemMapper, MaterialDocItemDo> implements MaterialDocItemRepository, IService<MaterialDocItemDo> {

	@Resource
	private MaterialDocItemConvertor convertor;

	@Override
	public PageResponse<MaterialDocItem> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<MaterialDocItemDo> page = baseMapper.queryPage(new PlusPageQuery<MaterialDocItemDo>(pageQuery).getPage(params),params);
		List<MaterialDocItemDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<MaterialDocItem> dtoList = records.stream().map(convertor::toMaterialDocItem).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public MaterialDocItem findById(MaterialDocItemId id) {
		MaterialDocItemDo materialdocitemDo = baseMapper.findById(id.get());
		return convertor.toMaterialDocItem(materialdocitemDo);
	}

	@Override
	public boolean store(MaterialDocItem materialdocitem) {
		MaterialDocItemDo materialdocitemDo = convertor.fromMaterialDocItem(materialdocitem);
		return this.saveOrUpdate(materialdocitemDo);
	}

	@Override
	public boolean update(MaterialDocItem materialdocitem) {
		MaterialDocItemDo materialdocitemDo = convertor.fromMaterialDocItem(materialdocitem);
		return this.saveOrUpdate(materialdocitemDo);
	}

	@Override
	public boolean delete(List<MaterialDocItemId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
