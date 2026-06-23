package com.inventory.middle.infra.persistence.mapper;

import com.inventory.middle.infra.persistence.entity.CodeApplyOrderDo;
import com.inventory.middle.infra.persistence.entity.CodeApplyOrderParamPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dongguo.tao
 */
@Mapper
public interface CodeApplyOrderMapper {

    /**
     * 新增码申请单
     * @param orderPO
     * @return
     */
    int insert(CodeApplyOrderDo orderPO);

    /**
     * 更新码申请单
     * @param orderPO
     * @return
     */
    int update(CodeApplyOrderDo orderPO);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CodeApplyOrderDo getById(Long id);

    /**
     * 根据条件查询申请单
     * @param paramPO
     * @return
     */
    List<CodeApplyOrderDo> listByCondition(CodeApplyOrderParamPO paramPO);
}