package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.Code;
import java.util.List;

/**
 * 备件流转码Repository接口
 */
public interface CodeRepository {

    /**
     * 根据ID查询
     */
    Code findById(Long id);

    /**
     * 保存
     */
    boolean store(Code entity);

    /**
     * 更新
     */
    boolean update(Code entity);

    /**
     * 根据ID列表查询
     */
    List<Code> findByIds(List<Long> ids);

    /**
     * 根据码值查询
     */
    Code findByCode(String code);

    /**
     * 根据innerCode查询
     */
    Code findByInnerCode(String innerCode);

    /**
     * 根据innerCode列表查询
     */
    List<Code> listByInnerCodes(List<String> innerCodes);

    /**
     * 根据businessNo + sourceNo查询
     */
    List<Code> listBySourceAndBusiness(String sourceNo, String businessNo);

    /**
     * 根据条件查询列表
     */
    List<Code> list(CodeQueryParam param);

    /**
     * 批量插入
     */
    void batchInsert(List<Code> entities);

    /**
     * 批量更新（按ID更新部分字段）
     */
    void batchUpdate(List<Code> entities);
}
