package com.railse.project.workforcemgmt.service;

import java.util.List;

import com.railse.project.workforcemgmt.dto.AddCommentRequest;
import com.railse.project.workforcemgmt.dto.AssignByReferenceRequest;
import com.railse.project.workforcemgmt.dto.TaskCreateRequest;
import com.railse.project.workforcemgmt.dto.TaskFetchByDateRequest;
import com.railse.project.workforcemgmt.dto.TaskManagementDto;
import com.railse.project.workforcemgmt.dto.UpdateTaskRequest;
import com.railse.project.workforcemgmt.model.enums.Priority;

public interface TaskManagementService {
   List<TaskManagementDto> createTasks(TaskCreateRequest request);
   List<TaskManagementDto> updateTasks(UpdateTaskRequest request);
   String assignByReference(AssignByReferenceRequest request);
   List<TaskManagementDto> fetchTasksByDate(TaskFetchByDateRequest request);
   TaskManagementDto findTaskById(Long id);
   TaskManagementDto updateTaskPriority(Long taskId, Priority priority);
   List<TaskManagementDto> findTasksByPriority(Priority priority);
   TaskManagementDto addComment(Long taskId, AddCommentRequest request);
}
