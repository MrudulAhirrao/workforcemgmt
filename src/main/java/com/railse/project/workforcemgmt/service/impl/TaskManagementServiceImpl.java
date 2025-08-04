package com.railse.project.workforcemgmt.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.railse.project.workforcemgmt.common.exception.ResourceNotFoundException;
import com.railse.project.workforcemgmt.dto.AddCommentRequest;
import com.railse.project.workforcemgmt.dto.AssignByReferenceRequest;
import com.railse.project.workforcemgmt.dto.TaskCreateRequest;
import com.railse.project.workforcemgmt.dto.TaskFetchByDateRequest;
import com.railse.project.workforcemgmt.dto.TaskManagementDto;
import com.railse.project.workforcemgmt.dto.UpdateTaskRequest;
import com.railse.project.workforcemgmt.mapper.ITaskManagementMapper;
import com.railse.project.workforcemgmt.model.ActivityLog;
import com.railse.project.workforcemgmt.model.TaskEvent;
import com.railse.project.workforcemgmt.model.TaskManagement;
import com.railse.project.workforcemgmt.model.UserComment;
import com.railse.project.workforcemgmt.model.enums.Priority;
import com.railse.project.workforcemgmt.model.enums.Task;
import com.railse.project.workforcemgmt.model.enums.TaskStatus;
import com.railse.project.workforcemgmt.repository.TaskRepository;
import com.railse.project.workforcemgmt.service.TaskManagementService;

@Service
public class TaskManagementServiceImpl implements TaskManagementService {

    private final TaskRepository taskRepository;
    private final ITaskManagementMapper taskMapper;

    public TaskManagementServiceImpl(TaskRepository taskRepository, ITaskManagementMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskManagementDto findTaskById(Long id) {
        TaskManagement task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        // Sort history by timestamp
        task.getHistory().sort(Comparator.comparing(TaskEvent::getTimestamp));

        return taskMapper.modelToDto(task);
    }

    @Override
    public List<TaskManagementDto> createTasks(TaskCreateRequest createRequest) {
        List<TaskManagement> createdTasks = new ArrayList<>();
        for (TaskCreateRequest.RequestItem item : createRequest.getRequests()) {
            TaskManagement newTask = new TaskManagement();
            newTask.setReferenceId(item.getReferenceId());
            newTask.setReferenceType(item.getReferenceType());
            newTask.setTask(item.getTask());
            newTask.setAssigneeId(item.getAssigneeId());
            newTask.setPriority(item.getPriority());
            newTask.setTaskDeadlineTime(item.getTaskDeadlineTime());
            newTask.setStatus(TaskStatus.ASSIGNED);
            newTask.getHistory().add(new ActivityLog("Task created."));
            createdTasks.add(taskRepository.save(newTask));
            newTask.setCreationTimestamp(System.currentTimeMillis());
            createdTasks.add(taskRepository.save(newTask));
        }
        return taskMapper.modelListToDtoList(createdTasks);
    }

    @Override
    public List<TaskManagementDto> updateTasks(UpdateTaskRequest updateRequest) {
        List<TaskManagement> updatedTasks = new ArrayList<>();
        for (UpdateTaskRequest.RequestItem item : updateRequest.getRequests()) {
            TaskManagement task = taskRepository.findById(item.getTaskId())
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + item.getTaskId()));

            if (item.getTaskStatus() != null) {
                task.setStatus(item.getTaskStatus());
                task.getHistory().add(new ActivityLog("Status changed to " + item.getTaskStatus()));
            }
            if (item.getDescription() != null) {
                task.setDescription(item.getDescription());
            }
            updatedTasks.add(taskRepository.save(task));
        }
        return taskMapper.modelListToDtoList(updatedTasks);
    }

    @Override
    public String assignByReference(AssignByReferenceRequest request) {
        List<Task> applicableTasks = Task.getTasksByReferenceType(request.getReferenceType());
        List<TaskManagement> existingTasks = taskRepository.findByReferenceIdAndReferenceType(request.getReferenceId(),
                request.getReferenceType());

        for (Task taskType : applicableTasks) {
            List<TaskManagement> tasksOfType = existingTasks.stream()
                    .filter(t -> t.getTask() == taskType && t.getStatus() != TaskStatus.COMPLETED
                            && t.getStatus() != TaskStatus.CANCELLED)
                    .collect(Collectors.toList());

            if (!tasksOfType.isEmpty()) {
                // Re-assign the first task in the list
                TaskManagement taskToAssign = tasksOfType.get(0);
                taskToAssign.setAssigneeId(request.getAssigneeId());
                taskToAssign.setStatus(TaskStatus.ASSIGNED);
                taskRepository.save(taskToAssign);

                // Cancel any other duplicate tasks
                if (tasksOfType.size() > 1) {
                    for (int i = 1; i < tasksOfType.size(); i++) {
                        TaskManagement taskToCancel = tasksOfType.get(i);
                        taskToCancel.setStatus(TaskStatus.CANCELLED);
                        taskRepository.save(taskToCancel);
                    }
                }
            } else {
                // Create a new task if none exist
                TaskManagement newTask = new TaskManagement();
                newTask.setReferenceId(request.getReferenceId());
                newTask.setReferenceType(request.getReferenceType());
                newTask.setTask(taskType);
                newTask.setAssigneeId(request.getAssigneeId());
                newTask.setStatus(TaskStatus.ASSIGNED);
                taskRepository.save(newTask);
            }
        }
        return "Tasks assigned successfully for reference " + request.getReferenceId();
    }

    @Override
    public List<TaskManagementDto> fetchTasksByDate(TaskFetchByDateRequest request) {
        List<TaskManagement> tasks = taskRepository.findByAssigneeIdIn(request.getAssigneeIds());
        final Long start = request.getStartDate();
        final Long end = request.getEndDate();

        List<TaskManagement> filteredTasks = tasks.stream()
                .filter(task -> {
                    boolean isActive = task.getStatus() == TaskStatus.ASSIGNED
                            || task.getStatus() == TaskStatus.STARTED;
                    if (!isActive) {
                        return false;
                    }
                    boolean startedInRange = task.getCreationTimestamp() >= start && task.getCreationTimestamp() <= end;
                    boolean startedBeforeAndStillOpen = task.getCreationTimestamp() < start;
                    return startedInRange || startedBeforeAndStillOpen;
                })
                .collect(Collectors.toList());

        return taskMapper.modelListToDtoList(filteredTasks);
    }

    @Override
    public TaskManagementDto updateTaskPriority(Long taskId, Priority priority) {
        TaskManagement task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        task.setPriority(priority);
        task.getHistory().add(new ActivityLog("Priority changed to " + priority));
        TaskManagement updatedTask = taskRepository.save(task);
        return taskMapper.modelToDto(updatedTask);
    }

    @Override
    public List<TaskManagementDto> findTasksByPriority(Priority priority) {
        List<TaskManagement> tasks = taskRepository.findByPriority(priority);
        return taskMapper.modelListToDtoList(tasks);
    }

    @Override
    public TaskManagementDto addComment(Long taskId, AddCommentRequest request) {
        TaskManagement task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        task.getHistory().add(new UserComment(request.getComment()));
        TaskManagement updatedTask = taskRepository.save(task);
        return taskMapper.modelToDto(updatedTask);
    }

}
