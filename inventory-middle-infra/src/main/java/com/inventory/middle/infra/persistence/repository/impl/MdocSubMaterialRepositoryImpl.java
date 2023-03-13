package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.MdocSubMaterial;
import com.inventory.middle.domain.model.types.MdocSubMaterialId;
import com.inventory.middle.domain.repository.MdocSubMaterialRepository;
import com.inventory.middle.infra.persistence.convertor.MdocSubMaterialConvertor;
import com.inventory.middle.infra.persistence.entity.MdocSubMaterialDo;
import com.inventory.middle.infra.persistence.mapper.MdocSubMaterialMapper;
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
 * 物料凭证子表-物料信息Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:21
 */
@Repository
public class MdocSubMaterialRepositoryImpl extends ServiceImpl<MdocSubMaterialMapper, MdocSubMaterialDo> implements MdocSubMaterialRepository, IService<MdocSubMaterialDo> {

	@Resource
	private MdocSubMaterialConvertor convertor;

	@Override
	public PageResponse<MdocSubMaterial> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<MdocSubMaterialDo> page = baseMapper.queryPage(new PlusPageQuery<MdocSubMaterialDo>(pageQuery).getPage(params),params);
		List<MdocSubMaterialDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<MdocSubMaterial> dtoList = records.stream().map(convertor::toMdocSubMaterial).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public MdocSubMaterial findById(MdocSubMaterialId id) {
		MdocSubMaterialDo mdocsubmaterialDo = baseMapper.findById(id.get());
		return convertor.toMdocSubMaterial(mdocsubmaterialDo);
	}

	@Override
	public boolean store(MdocSubMaterial mdocsubmaterial) {
		MdocSubMaterialDo mdocsubmaterialDo = convertor.fromMdocSubMaterial(mdocsubmaterial);
		return this.saveOrUpdate(mdocsubmaterialDo);
	}

	@Override
	public boolean update(MdocSubMaterial mdocsubmaterial) {
		MdocSubMaterialDo mdocsubmaterialDo = convertor.fromMdocSubMaterial(mdocsubmaterial);
		return this.saveOrUpdate(mdocsubmaterialDo);
	}

	@Override
	public boolean delete(List<MdocSubMaterialId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
