package com.example.EmployeeDirectory.services;

import com.example.EmployeeDirectory.repositories.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> getAll();
    List<DepartmentDto> getAllDepartmentsByCity(String cityName);
}
