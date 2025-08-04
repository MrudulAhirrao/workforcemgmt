package com.railse.project.workforcemgmt.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.PropertyNamingStrategyBase;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.railse.project.workforcemgmt.common.model.enums.ReferenceType;
import com.railse.project.workforcemgmt.model.TaskEvent;
import com.railse.project.workforcemgmt.model.enums.Priority;
import com.railse.project.workforcemgmt.model.enums.Task;
import com.railse.project.workforcemgmt.model.enums.TaskStatus;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategyBase.SnakeCaseStrategy.class)
public class TaskManagementDto {
    private Long id;
    private Long referenceId;
    private ReferenceType referenceType;
    private Task task;
    private String description;
    private TaskStatus status;
    private Long assigneeId;
    private Long taskDeadlineTime;
    private Priority priority;
    private List<TaskEvent> history;
}
