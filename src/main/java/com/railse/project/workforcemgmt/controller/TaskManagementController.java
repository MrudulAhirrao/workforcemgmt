package com.railse.project.workforcemgmt.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railse.project.workforcemgmt.common.model.response.Response;
import com.railse.project.workforcemgmt.dto.AddCommentRequest;
import com.railse.project.workforcemgmt.dto.AssignByReferenceRequest;
import com.railse.project.workforcemgmt.dto.TaskCreateRequest;
import com.railse.project.workforcemgmt.dto.TaskFetchByDateRequest;
import com.railse.project.workforcemgmt.dto.TaskManagementDto;
import com.railse.project.workforcemgmt.dto.UpdatePriorityRequest;
import com.railse.project.workforcemgmt.dto.UpdateTaskRequest;
import com.railse.project.workforcemgmt.model.enums.Priority;
import com.railse.project.workforcemgmt.service.TaskManagementService;

@RestController
@RequestMapping("/task-mgmt")
public class TaskManagementController {

    private final TaskManagementService taskManagementService;

    public TaskManagementController(TaskManagementService taskManagementService) {
        this.taskManagementService = taskManagementService;
    }

    @GetMapping("/{id}")
    public Response<TaskManagementDto> getTaskById(@PathVariable Long id) {
        return new Response<>(taskManagementService.findTaskById(id));
    }

    @PostMapping("/create")
    public Response<List<TaskManagementDto>> createTasks(@RequestBody TaskCreateRequest request) {
        return new Response<>(taskManagementService.createTasks(request));
    }

    @PostMapping("/update")
    public Response<List<TaskManagementDto>> updateTasks(@RequestBody UpdateTaskRequest request) {
        return new Response<>(taskManagementService.updateTasks(request));
    }

    @PostMapping("/assign-by-ref")
    public Response<String> assignByReference(@RequestBody AssignByReferenceRequest request) {
        return new Response<>(taskManagementService.assignByReference(request));
    }

    @PostMapping("/fetch-by-date/v2")
    public Response<List<TaskManagementDto>> fetchByDate(@RequestBody TaskFetchByDateRequest request) {
        return new Response<>(taskManagementService.fetchTasksByDate(request));
    }

    @PatchMapping("/{id}/priority")
    public Response<TaskManagementDto> updateTaskPriority(
            @PathVariable Long id,
            @RequestBody UpdatePriorityRequest request) {
        return new Response<>(taskManagementService.updateTaskPriority(id, request.getPriority()));
    }

    @GetMapping("/priority/{priority}")
    public Response<List<TaskManagementDto>> getTasksByPriority(@PathVariable Priority priority) {
        return new Response<>(taskManagementService.findTasksByPriority(priority));
    }

    @PostMapping("/{id}/comments")
    public Response<TaskManagementDto> addComment(
            @PathVariable Long id,
            @RequestBody AddCommentRequest request) {
        return new Response<>(taskManagementService.addComment(id, request));
    }
}
