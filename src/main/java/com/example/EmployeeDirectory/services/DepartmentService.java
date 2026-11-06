package com.example.EmployeeDirectory.services;

import com.example.EmployeeDirectory.repositories.dto.create.DepartmentCreateDto;
import com.example.EmployeeDirectory.web.responses.DepartmentResponseDto;
import com.example.EmployeeDirectory.web.views.DepartmentView;

import java.util.List;

public interface DepartmentService {
    List<DepartmentView> getAll();
    List<DepartmentView> getAllDepartmentsByCity(String cityName);
    DepartmentResponseDto create(DepartmentCreateDto dto);
}



