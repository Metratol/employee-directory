package com.example.EmployeeDirectory.mappers;


import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.web.responses.DepartmentResponseDto;
import com.example.EmployeeDirectory.web.views.DepartmentView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    List<DepartmentView> toViews(List<Department> department);
    @Mapping(target = "city", source = "city.name")
    DepartmentResponseDto toResponse(Department department);

}
