package com.inventory.middle.infra.persistence.mapper;

import com.inventory.middle.infra.persistence.entity.CodeApplyOrderDetailDo;
import com.inventory.middle.infra.persistence.entity.CodeApplyOrderDetailParamPO;
import com.inventory.middle.infra.persistence.entity.CodeApplyOrderStatisticsPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dongguo.tao
 */
@Mapper
public interface CodeApplyOrderDetailMapper {

    /**
     * 新增码申请单详情
     * @param orderDetailPO
     * @return
     */
    int insert(CodeApplyOrderDetailDo orderDetailPO);


    /**
     * 批量新增码申请单详情
     * @param list
     * @return
     */
    int insertBatch(List<CodeApplyOrderDetailDo> list);

    /**
     * 根据条件查询信息
     * @param paramPO
     * @return
     */
    List<CodeApplyOrderDetailDo> queryByCondition(CodeApplyOrderDetailParamPO paramPO);

    /**
     * 根据申请单id查询
     * @param applyOrderId
     * @return
     */
    List<CodeApplyOrderDetailDo> listByApplyOrderId(Long applyOrderId);

    /**
     * 查询申每个请单中流转码数量
     * @param idList
     * @return
     */
    List<CodeApplyOrderStatisticsPO> queryCountByOrderIds(@Param("applyOrderIdList")List<Long> idList);
}