package EmployeeDirectory.services;

import EmployeeDirectory.repositories.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> getAll();
    List<DepartmentDto> getAllDepartmentsByCity(String cityName);
}
