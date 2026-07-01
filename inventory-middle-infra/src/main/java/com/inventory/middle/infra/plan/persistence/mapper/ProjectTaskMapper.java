package com.inventory.middle.infra.plan.persistence.mapper;

import com.inventory.middle.infra.plan.persistence.entity.ProjectTaskPO;
import org.apache.ibatis.annotations.Param;

public interface ProjectTaskMapper {

    int insert(ProjectTaskPO record);

    int insertSelective(ProjectTaskPO record);

    ProjectTaskPO selectByPrimaryKey(Long id);

    ProjectTaskPO selectByTaskNo(@Param("taskNo") String taskNo);

    ProjectTaskPO selectByRequestIdAndProjectId(@Param("requestId") String requestId,
                                                @Param("projectId") Long projectId);

    ProjectTaskPO selectByRequestId(@Param("requestId") String requestId);

    int updateByPrimaryKeySelective(ProjectTaskPO record);

    int updateByTaskNoSelective(ProjectTaskPO record);

    int updateByRequestIdSelective(ProjectTaskPO record);

}
