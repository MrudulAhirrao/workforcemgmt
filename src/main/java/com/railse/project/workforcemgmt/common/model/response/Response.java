
package com.railse.project.workforcemgmt.common.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.PropertyNamingStrategyBase;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.railse.project.workforcemgmt.common.exception.StatusCode;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategyBase.SnakeCaseStrategy.class)
public class Response<T> {

    private T data;
    private Pagination pagination;
    private ResponseStatus status;

    public Response(T data, Pagination pagination, ResponseStatus status) {
        this.data = data;
        this.pagination = pagination;
        this.status = status;
    }

    public Response(T data) {
        this(data, null, new ResponseStatus(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
    }
}