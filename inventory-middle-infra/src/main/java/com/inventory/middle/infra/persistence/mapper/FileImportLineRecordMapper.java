package com.inventory.middle.infra.persistence.mapper;

import com.inventory.middle.infra.persistence.entity.FileImportLineRecordDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileImportLineRecordMapper {
    int insert(FileImportLineRecordDo record);

    int insertSelective(FileImportLineRecordDo record);

    int batchInsert(@Param("list") List<FileImportLineRecordDo> recordPOList);

}