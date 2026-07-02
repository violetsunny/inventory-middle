package com.inventory.middle.application.convertor;

import com.inventory.middle.domain.plan.task.service.ProjectTaskBO;
import com.inventory.middle.infra.plan.persistence.entity.ProjectTaskPO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectTaskConvertor {

    ProjectTaskPO toPO(ProjectTaskBO bo);
}
