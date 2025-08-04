package com.railse.project.workforcemgmt.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.PropertyNamingStrategyBase;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.railse.project.workforcemgmt.common.model.enums.ReferenceType;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategyBase.SnakeCaseStrategy.class)
public class AssignByReferenceRequest {
    private Long referenceId;
    private ReferenceType referenceType;
    private Long assigneeId;   
}
