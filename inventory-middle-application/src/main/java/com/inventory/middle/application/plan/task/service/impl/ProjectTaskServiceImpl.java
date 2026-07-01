package com.inventory.middle.application.plan.task.service.impl;

import com.inventory.middle.domain.plan.task.service.ProjectTaskBO;
import com.inventory.middle.domain.plan.task.service.ProjectTaskService;
import com.inventory.middle.infra.plan.persistence.dao.ProjectTaskDao;
import com.inventory.middle.infra.plan.persistence.entity.ProjectTaskPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@Service
public class ProjectTaskServiceImpl implements com.inventory.middle.application.plan.task.service.ProjectTaskService {

    @Resource
    private ProjectTaskDao projectTaskDao;

    @Resource
    private ProjectTaskService projectTaskDomainService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProjectTaskBO apply(String requestId, Long projectId, String projectType,
                               String taskRuleBody, String taskDataBody) {
        ProjectTaskBO bo = new ProjectTaskBO();
        bo.setRequestId(requestId);
        bo.setProjectId(projectId);
        bo.setProjectType(projectType);
        bo.setRequestBody(taskRuleBody != null ? taskRuleBody : taskDataBody);
        bo.setRequestStatus(0);
        ProjectTaskBO created = projectTaskDomainService.apply(bo);

        ProjectTaskPO po = new ProjectTaskPO();
        BeanUtils.copyProperties(created, po);
        po.setCreateTime(LocalDateTime.now());
        po.setUpdateTime(LocalDateTime.now());
        po.setIsDelete(0);
        projectTaskDao.insert(po);

        ProjectTaskBO result = new ProjectTaskBO();
        result.setRequestId(po.getRequestId());
        result.setTaskNo(po.getTaskNo());
        return result;
    }

    @Override
    public ProjectTaskBO query(String requestId, Long projectId, String taskNo) {
        ProjectTaskPO po = null;
        if (StringUtils.isNotBlank(taskNo)) {
            po = projectTaskDao.selectByTaskNo(taskNo);
        }
        if (po == null) {
            po = projectTaskDao.selectByRequestIdAndProjectId(requestId, projectId);
        }
        return toBO(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void notify(String requestId, Long calResultCode, String optTarget,
                       String reRequestId, String reTaskNo,
                       String originalBody, String tempData) {
        projectTaskDomainService.notify(requestId, originalBody, tempData,
                calResultCode, optTarget, reRequestId, reTaskNo);

        ProjectTaskPO po = projectTaskDao.selectByRequestId(requestId);
        if (po != null) {
            po.setOriginalBody(originalBody);
            po.setTempData(tempData);
            po.setCalResultCode(calResultCode);
            po.setOptTarget(optTarget);
            po.setReRequestId(reRequestId);
            po.setReTaskNo(reTaskNo);
            po.setRequestStatus(1);
            po.setUpdateTime(LocalDateTime.now());
            projectTaskDao.update(po);
        }
    }

    @Override
    public ProjectTaskBO detail(String taskNo) {
        ProjectTaskPO po = projectTaskDao.selectByTaskNo(taskNo);
        return toBO(po);
    }

    private ProjectTaskBO toBO(ProjectTaskPO po) {
        if (po == null) {
            return null;
        }
        ProjectTaskBO bo = new ProjectTaskBO();
        bo.setId(po.getId());
        bo.setRequestId(po.getRequestId());
        bo.setTaskNo(po.getTaskNo());
        bo.setProjectId(po.getProjectId());
        bo.setProjectType(po.getProjectType());
        bo.setRequestStatus(po.getRequestStatus());
        bo.setRequestBody(po.getRequestBody());
        bo.setOriginalBody(po.getOriginalBody());
        bo.setTempData(po.getTempData());
        bo.setCalResultCode(po.getCalResultCode());
        bo.setOptTarget(po.getOptTarget());
        bo.setReRequestId(po.getReRequestId());
        bo.setReTaskNo(po.getReTaskNo());
        bo.setCreateUserId(po.getCreateUserId());
        bo.setUpdateUserId(po.getUpdateUserId());
        bo.setCreateTime(po.getCreateTime());
        bo.setUpdateTime(po.getUpdateTime());
        bo.setReCreateTime(po.getReCreateTime());
        bo.setIsDelete(po.getIsDelete());
        return bo;
    }
}
