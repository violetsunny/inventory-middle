package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.MdocSubExt;
import com.inventory.middle.domain.model.types.MdocSubExtId;
import com.inventory.middle.domain.repository.MdocSubExtRepository;
import com.inventory.middle.infra.persistence.convertor.MdocSubExtConvertor;
import com.inventory.middle.infra.persistence.entity.MdocSubExtDo;
import com.inventory.middle.infra.persistence.mapper.MdocSubExtMapper;
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
 * 物料凭证-标签-扩展Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 20:32:10
 */
@Repository
public class MdocSubExtRepositoryImpl extends ServiceImpl<MdocSubExtMapper, MdocSubExtDo> implements MdocSubExtRepository, IService<MdocSubExtDo> {

	@Resource
	private MdocSubExtConvertor convertor;

	@Override
	public PageResponse<MdocSubExt> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<MdocSubExtDo> page = baseMapper.queryPage(new PlusPageQuery<MdocSubExtDo>(pageQuery).getPage(params),params);
		List<MdocSubExtDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<MdocSubExt> dtoList = records.stream().map(convertor::toMdocSubExt).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public MdocSubExt findById(MdocSubExtId id) {
		MdocSubExtDo mdocsubextDo = baseMapper.findById(id.get());
		return convertor.toMdocSubExt(mdocsubextDo);
	}

	@Override
	public boolean store(MdocSubExt mdocsubext) {
		MdocSubExtDo mdocsubextDo = convertor.fromMdocSubExt(mdocsubext);
		return this.saveOrUpdate(mdocsubextDo);
	}

	@Override
	public boolean update(MdocSubExt mdocsubext) {
		MdocSubExtDo mdocsubextDo = convertor.fromMdocSubExt(mdocsubext);
		return this.saveOrUpdate(mdocsubextDo);
	}

	@Override
	public boolean delete(List<MdocSubExtId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
