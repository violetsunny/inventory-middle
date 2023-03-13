package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.MdocSubMap;
import com.inventory.middle.domain.model.types.MdocSubMapId;
import com.inventory.middle.domain.repository.MdocSubMapRepository;
import com.inventory.middle.infra.persistence.convertor.MdocSubMapConvertor;
import com.inventory.middle.infra.persistence.entity.MdocSubMapDo;
import com.inventory.middle.infra.persistence.mapper.MdocSubMapMapper;
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
 * 物料凭证-标签-移动平均价Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:22
 */
@Repository
public class MdocSubMapRepositoryImpl extends ServiceImpl<MdocSubMapMapper, MdocSubMapDo> implements MdocSubMapRepository, IService<MdocSubMapDo> {

	@Resource
	private MdocSubMapConvertor convertor;

	@Override
	public PageResponse<MdocSubMap> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<MdocSubMapDo> page = baseMapper.queryPage(new PlusPageQuery<MdocSubMapDo>(pageQuery).getPage(params),params);
		List<MdocSubMapDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<MdocSubMap> dtoList = records.stream().map(convertor::toMdocSubMap).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public MdocSubMap findById(MdocSubMapId id) {
		MdocSubMapDo mdocsubmapDo = baseMapper.findById(id.get());
		return convertor.toMdocSubMap(mdocsubmapDo);
	}

	@Override
	public boolean store(MdocSubMap mdocsubmap) {
		MdocSubMapDo mdocsubmapDo = convertor.fromMdocSubMap(mdocsubmap);
		return this.saveOrUpdate(mdocsubmapDo);
	}

	@Override
	public boolean update(MdocSubMap mdocsubmap) {
		MdocSubMapDo mdocsubmapDo = convertor.fromMdocSubMap(mdocsubmap);
		return this.saveOrUpdate(mdocsubmapDo);
	}

	@Override
	public boolean delete(List<MdocSubMapId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
