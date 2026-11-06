package com.example.EmployeeDirectory.mappers;

import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.repositories.dto.create.EmployeeCreateDto;
import com.example.EmployeeDirectory.web.responses.EmployeeResponseDto;
import com.example.EmployeeDirectory.web.views.EmployeeView;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    List<EmployeeView> toViews(List<Employee> employees);
    Employee toModel(EmployeeCreateDto dto);
    EmployeeResponseDto toResponse(Employee employee);
}
