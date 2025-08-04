package com.railse.project.workforcemgmt.common.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.PropertyNamingStrategyBase;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategyBase.SnakeCaseStrategy.class)
public class ResponseStatus {
    private Integer code;
    private String message;
}
