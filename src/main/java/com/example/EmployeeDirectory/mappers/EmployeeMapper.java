package com.example.EmployeeDirectory.mappers;

import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.repositories.dto.create.EmployeeCreateDto;
import com.example.EmployeeDirectory.repositories.dto.update.EmployeeUpdateDto;
import com.example.EmployeeDirectory.web.responses.EmployeeResponseDto;
import com.example.EmployeeDirectory.web.views.EmployeeView;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    List<EmployeeView> toViews(List<Employee> employees);
    @Mapping(target = "department", source = "department")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(EmployeeUpdateDto dto, Department department, @MappingTarget Employee employee);
    @Mapping(target = "department", source = "department")
    Employee toModel(EmployeeCreateDto dto, Department department);

    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "cityName", source = "department.city.name")
    EmployeeView toView(Employee employee);

    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "cityName", source = "department.city.name")
    EmployeeResponseDto toResponse(Employee employee);
}