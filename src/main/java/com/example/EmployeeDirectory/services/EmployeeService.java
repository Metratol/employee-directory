package com.example.EmployeeDirectory.services;

import com.example.EmployeeDirectory.repositories.dto.EmployeeDto;

import java.awt.print.Pageable;
import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> getAll(Pageable pageable);
    List<EmployeeDto> getAllByCity(Pageable pageable, String cityName);

    List<EmployeeDto> getAllByDepartment(Pageable pageable, String departmentName);
    List<EmployeeDto> getAllByDepartmentAndCity(Pageable pageable, String departmentName, String cityName);

    EmployeeDto getByDocsNo(Pageable pageable, String docsNo);

    EmployeeDto getByPhoneNumber(Pageable pageable, String phoneNumber);

    List<EmployeeDto> getByFullName(Pageable pageable, String firstName, String secondName);
}
