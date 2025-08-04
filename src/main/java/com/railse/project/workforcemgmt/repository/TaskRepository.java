package com.railse.project.workforcemgmt.repository;

import java.util.List;
import java.util.Optional;

import com.railse.project.workforcemgmt.model.TaskManagement;
import com.railse.project.workforcemgmt.model.enums.Priority;

public interface TaskRepository {
   Optional<TaskManagement> findById(Long id);
   TaskManagement save(TaskManagement task);
   List<TaskManagement> findAll();
   List<TaskManagement> findByReferenceIdAndReferenceType(Long referenceId, com.railse.project.workforcemgmt.common.model.enums.ReferenceType referenceType);
   List<TaskManagement> findByAssigneeIdIn(List<Long> assigneeIds);
   List<TaskManagement> findByPriority(Priority priority);
}
