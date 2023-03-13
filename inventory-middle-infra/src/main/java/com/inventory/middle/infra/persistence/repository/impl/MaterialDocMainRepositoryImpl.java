package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.MaterialDocMain;
import com.inventory.middle.domain.model.types.MaterialDocMainId;
import com.inventory.middle.domain.repository.MaterialDocMainRepository;
import com.inventory.middle.infra.persistence.convertor.MaterialDocMainConvertor;
import com.inventory.middle.infra.persistence.entity.MaterialDocMainDo;
import com.inventory.middle.infra.persistence.mapper.MaterialDocMainMapper;
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
 * 物料凭证主表Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:22
 */
@Repository
public class MaterialDocMainRepositoryImpl extends ServiceImpl<MaterialDocMainMapper, MaterialDocMainDo> implements MaterialDocMainRepository, IService<MaterialDocMainDo> {

	@Resource
	private MaterialDocMainConvertor convertor;

	@Override
	public PageResponse<MaterialDocMain> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<MaterialDocMainDo> page = baseMapper.queryPage(new PlusPageQuery<MaterialDocMainDo>(pageQuery).getPage(params),params);
		List<MaterialDocMainDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<MaterialDocMain> dtoList = records.stream().map(convertor::toMaterialDocMain).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public MaterialDocMain findById(MaterialDocMainId id) {
		MaterialDocMainDo materialdocmainDo = baseMapper.findById(id.get());
		return convertor.toMaterialDocMain(materialdocmainDo);
	}

	@Override
	public boolean store(MaterialDocMain materialdocmain) {
		MaterialDocMainDo materialdocmainDo = convertor.fromMaterialDocMain(materialdocmain);
		return this.saveOrUpdate(materialdocmainDo);
	}

	@Override
	public boolean update(MaterialDocMain materialdocmain) {
		MaterialDocMainDo materialdocmainDo = convertor.fromMaterialDocMain(materialdocmain);
		return this.saveOrUpdate(materialdocmainDo);
	}

	@Override
	public boolean delete(List<MaterialDocMainId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
