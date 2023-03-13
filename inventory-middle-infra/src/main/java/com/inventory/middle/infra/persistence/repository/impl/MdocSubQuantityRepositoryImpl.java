package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.MdocSubQuantity;
import com.inventory.middle.domain.model.types.MdocSubQuantityId;
import com.inventory.middle.domain.repository.MdocSubQuantityRepository;
import com.inventory.middle.infra.persistence.convertor.MdocSubQuantityConvertor;
import com.inventory.middle.infra.persistence.entity.MdocSubQuantityDo;
import com.inventory.middle.infra.persistence.mapper.MdocSubQuantityMapper;
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
 * 物料凭证子表-数量Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:21
 */
@Repository
public class MdocSubQuantityRepositoryImpl extends ServiceImpl<MdocSubQuantityMapper, MdocSubQuantityDo> implements MdocSubQuantityRepository, IService<MdocSubQuantityDo> {

	@Resource
	private MdocSubQuantityConvertor convertor;

	@Override
	public PageResponse<MdocSubQuantity> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<MdocSubQuantityDo> page = baseMapper.queryPage(new PlusPageQuery<MdocSubQuantityDo>(pageQuery).getPage(params),params);
		List<MdocSubQuantityDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<MdocSubQuantity> dtoList = records.stream().map(convertor::toMdocSubQuantity).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public MdocSubQuantity findById(MdocSubQuantityId id) {
		MdocSubQuantityDo mdocsubquantityDo = baseMapper.findById(id.get());
		return convertor.toMdocSubQuantity(mdocsubquantityDo);
	}

	@Override
	public boolean store(MdocSubQuantity mdocsubquantity) {
		MdocSubQuantityDo mdocsubquantityDo = convertor.fromMdocSubQuantity(mdocsubquantity);
		return this.saveOrUpdate(mdocsubquantityDo);
	}

	@Override
	public boolean update(MdocSubQuantity mdocsubquantity) {
		MdocSubQuantityDo mdocsubquantityDo = convertor.fromMdocSubQuantity(mdocsubquantity);
		return this.saveOrUpdate(mdocsubquantityDo);
	}

	@Override
	public boolean delete(List<MdocSubQuantityId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
