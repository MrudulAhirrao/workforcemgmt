package com.railse.project.workforcemgmt.model;

import java.util.ArrayList;
import java.util.List;

import com.railse.project.workforcemgmt.common.model.enums.ReferenceType;
import com.railse.project.workforcemgmt.model.enums.Priority;
import com.railse.project.workforcemgmt.model.enums.Task;
import com.railse.project.workforcemgmt.model.enums.TaskStatus;

import lombok.Data;

@Data
public class TaskManagement {
    private Long id;
    private Long referenceId;
    private ReferenceType referenceType;
    private Task task;
    private String description;
    private TaskStatus status;
    private Long assigneeId;
    private Long taskDeadlineTime;
    private Priority priority;
    private Long creationTimestamp;
    private List<TaskEvent> history = new ArrayList<>();
}
