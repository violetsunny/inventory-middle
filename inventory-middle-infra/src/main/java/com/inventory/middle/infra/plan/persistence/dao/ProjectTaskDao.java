package com.inventory.middle.infra.plan.persistence.dao;

import com.inventory.middle.infra.plan.persistence.entity.ProjectTaskPO;
import com.inventory.middle.infra.plan.persistence.mapper.ProjectTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectTaskDao {

    @Autowired
    private ProjectTaskMapper projectTaskMapper;

    public int insert(ProjectTaskPO record) {
        return projectTaskMapper.insertSelective(record);
    }

    public ProjectTaskPO selectById(Long id) {
        return projectTaskMapper.selectByPrimaryKey(id);
    }

    public ProjectTaskPO selectByTaskNo(String taskNo) {
        return projectTaskMapper.selectByTaskNo(taskNo);
    }

    public ProjectTaskPO selectByRequestIdAndProjectId(String requestId, Long projectId) {
        return projectTaskMapper.selectByRequestIdAndProjectId(requestId, projectId);
    }

    public ProjectTaskPO selectByRequestId(String requestId) {
        return projectTaskMapper.selectByRequestId(requestId);
    }

    public int update(ProjectTaskPO record) {
        return projectTaskMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByTaskNo(ProjectTaskPO record) {
        return projectTaskMapper.updateByTaskNoSelective(record);
    }

    public int updateByRequestId(ProjectTaskPO record) {
        return projectTaskMapper.updateByRequestIdSelective(record);
    }

}
