package com.inventory.middle.infra.persistence.mapper;

import com.inventory.middle.infra.persistence.entity.UpdateByTenantIdAndDateParam;
import com.inventory.middle.infra.persistence.entity.ListFileImportRecordParam;
import com.inventory.middle.infra.persistence.entity.FileImportRecordDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileImportRecordMapper {
    int insert(FileImportRecordDo record);

    int insertSelective(FileImportRecordDo record);

    /**
     * 通过id、租户，更新数据
     * @param record
     * @return
     */
    int updateByIdAndTenantIdSelective(FileImportRecordDo record);

    /**
     * 通过租户、时间范围，更新数据
     * @param param
     * @return
     */
    int updateByTenantIdAndDateSelective(UpdateByTenantIdAndDateParam param);

    /**
     * 通过id查询，不包含Blob
     * @param id
     * @return
     */

    FileImportRecordDo selectByIdWithNoBlob(Long id);

    /**
     * 列表查询，不包含Blob
     * @param param
     * @return
     */

    List<FileImportRecordDo> listWithNoBlob(ListFileImportRecordParam param);
}