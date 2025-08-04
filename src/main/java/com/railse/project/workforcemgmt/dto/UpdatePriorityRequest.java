package com.railse.project.workforcemgmt.dto;

import com.railse.project.workforcemgmt.model.enums.Priority;

import lombok.Data;

@Data
public class UpdatePriorityRequest {
    private Priority priority;
}
