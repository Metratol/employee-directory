package com.example.EmployeeDirectory.services;

import com.example.EmployeeDirectory.repositories.dto.create.DepartmentCreateDto;
import com.example.EmployeeDirectory.repositories.dto.update.DepartmentCityUpdateDto;
import com.example.EmployeeDirectory.repositories.dto.update.DepartmentNameUpdateDto;
import com.example.EmployeeDirectory.web.responses.DepartmentResponseDto;
import com.example.EmployeeDirectory.web.views.DepartmentView;

import java.util.List;

public interface DepartmentService {
    List<DepartmentView> getAll();
    List<DepartmentView> getAllDepartmentsByCity(String cityName);
    DepartmentResponseDto create(DepartmentCreateDto dto);
    DepartmentResponseDto update(DepartmentNameUpdateDto dto);
    DepartmentResponseDto moveToAnotherCity(DepartmentCityUpdateDto dto);
    boolean delete(String name, String cityName);

}



