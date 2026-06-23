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
}
