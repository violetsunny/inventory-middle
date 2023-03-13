package com.inventory.middle.infra.persistence.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inventory.middle.domain.model.entity.MdocSubInOut;
import com.inventory.middle.domain.model.types.MdocSubInOutId;
import com.inventory.middle.domain.repository.MdocSubInOutRepository;
import com.inventory.middle.infra.persistence.convertor.MdocSubInOutConvertor;
import com.inventory.middle.infra.persistence.entity.MdocSubInOutDo;
import com.inventory.middle.infra.persistence.mapper.MdocSubInOutMapper;
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
 * 物料凭证子表-出入库信息Repository实现类
 *
 * @author kll
 * @email kll@job.cn
 * @date 2023-03-13 19:42:22
 */
@Repository
public class MdocSubInOutRepositoryImpl extends ServiceImpl<MdocSubInOutMapper, MdocSubInOutDo> implements MdocSubInOutRepository, IService<MdocSubInOutDo> {

	@Resource
	private MdocSubInOutConvertor convertor;

	@Override
	public PageResponse<MdocSubInOut> queryPage(PageQuery pageQuery, Map<String, Object> params) {
		IPage<MdocSubInOutDo> page = baseMapper.queryPage(new PlusPageQuery<MdocSubInOutDo>(pageQuery).getPage(params),params);
		List<MdocSubInOutDo> records = page.getRecords();
		if (CollectionUtils.isEmpty(records)) {
			return PageResponse.of(page.getSize(), page.getCurrent());
		}
		List<MdocSubInOut> dtoList = records.stream().map(convertor::toMdocSubInOut).collect(Collectors.toList());
		return PageResponse.of(dtoList, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public MdocSubInOut findById(MdocSubInOutId id) {
		MdocSubInOutDo mdocsubinoutDo = baseMapper.findById(id.get());
		return convertor.toMdocSubInOut(mdocsubinoutDo);
	}

	@Override
	public boolean store(MdocSubInOut mdocsubinout) {
		MdocSubInOutDo mdocsubinoutDo = convertor.fromMdocSubInOut(mdocsubinout);
		return this.saveOrUpdate(mdocsubinoutDo);
	}

	@Override
	public boolean update(MdocSubInOut mdocsubinout) {
		MdocSubInOutDo mdocsubinoutDo = convertor.fromMdocSubInOut(mdocsubinout);
		return this.saveOrUpdate(mdocsubinoutDo);
	}

	@Override
	public boolean delete(List<MdocSubInOutId> ids) {
		List<Long> tempIds = new ArrayList<>();
		ids.forEach(tempId -> {
			tempIds.add(tempId.get());
		});
		return this.removeByIds(tempIds);
	}

}
