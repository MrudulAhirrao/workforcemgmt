package com.railse.project.workforcemgmt.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import com.railse.project.workforcemgmt.dto.TaskManagementDto;
import com.railse.project.workforcemgmt.model.TaskManagement;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ITaskManagementMapper {
    ITaskManagementMapper INSTANCE = Mappers.getMapper(ITaskManagementMapper.class);
   TaskManagementDto modelToDto(TaskManagement model);
   TaskManagement dtoToModel(TaskManagementDto dto);
   List<TaskManagementDto> modelListToDtoList(List<TaskManagement> models);
}
