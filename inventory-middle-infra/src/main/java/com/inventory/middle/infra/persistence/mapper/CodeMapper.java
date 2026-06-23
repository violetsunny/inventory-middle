package com.inventory.middle.infra.persistence.mapper;

import com.inventory.middle.infra.persistence.entity.CodeDo;
import com.inventory.middle.infra.persistence.entity.CountCodeParamPO;
import com.inventory.middle.infra.persistence.entity.ListCodeParamPO;
import com.inventory.middle.infra.persistence.entity.SelectOneCodeParam2PO;
import com.inventory.middle.infra.persistence.entity.SelectOneCodeParamPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CodeMapper {

    int insert(CodeDo record);

    int insertSelective(CodeDo record);

    void batchInsert(List<CodeDo> poList);

    int updateByIdSelective(CodeDo po);

    int updateByIdAndStatusSelective(@Param("po") CodeDo codePO, @Param("id") Long id, @Param("status") String status);

    List<CodeDo> list(ListCodeParamPO paramPO);

    void batchUpdate(List<CodeDo> list);

    CodeDo selectOne(SelectOneCodeParamPO paramPO);

    CodeDo selectByInnerCode(SelectOneCodeParam2PO paramPO);

    int count(CountCodeParamPO paramPO);
}