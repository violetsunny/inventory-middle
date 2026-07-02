package com.inventory.middle.domain.repository;

import com.inventory.middle.domain.model.entity.FileImportRecord;
import java.util.List;

/**
 * 文件导入记录Repository接口
 */
public interface FileImportRecordRepository {

    /**
     * 根据ID查询
     */
    FileImportRecord findById(Long id);

    /**
     * 保存
     */
    boolean store(FileImportRecord entity);

    /**
     * 更新
     */
    boolean update(FileImportRecord entity);

    /**
     * 根据ID列表查询
     */
    List<FileImportRecord> findByIds(List<Long> ids);

    /**
     * 按条件查询（不含大字段）
     */
    List<FileImportRecord> listWithNoBlob(FileImportRecordQueryParam queryParam);
}
