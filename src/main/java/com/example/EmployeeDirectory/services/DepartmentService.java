package com.example.EmployeeDirectory.services;

import com.example.EmployeeDirectory.repositories.dto.create.DepartmentCreateDto;
import com.example.EmployeeDirectory.web.responses.DepartmentResponseDto;

import java.util.List;

public interface DepartmentService {
    List<DepartmentCreateDto> getAll();
    List<DepartmentCreateDto> getAllDepartmentsByCity(String cityName);
    DepartmentResponseDto create(DepartmentCreateDto dto);
}



