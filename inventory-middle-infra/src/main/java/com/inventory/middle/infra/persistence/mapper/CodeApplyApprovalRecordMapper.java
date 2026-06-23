package com.inventory.middle.infra.persistence.mapper;

import com.inventory.middle.infra.persistence.entity.CodeApplyApprovalRecordDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dongguo.tao
 */
@Mapper
public interface CodeApplyApprovalRecordMapper {

    /**
     * 新增码申审批记录
     * @param recordPO
     * @return
     */
    int insert(CodeApplyApprovalRecordDo recordPO);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CodeApplyApprovalRecordDo getById(Long id);


}