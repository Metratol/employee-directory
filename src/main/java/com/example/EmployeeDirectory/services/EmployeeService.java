package com.example.EmployeeDirectory.services;

import com.example.EmployeeDirectory.repositories.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getAll();
    List<EmployeeDto> getAllByCity(String cityName);

    List<EmployeeDto> getAllByDepartment(String departmentName);
    List<EmployeeDto> getAllByDepartmentAndCity(String departmentName, String cityName);

    EmployeeDto getByDocsNo(String docsNo);

    EmployeeDto getByPhoneNumber(String phoneNumber);

    List<EmployeeDto> getByFullName(String firstName, String secondName);
}
