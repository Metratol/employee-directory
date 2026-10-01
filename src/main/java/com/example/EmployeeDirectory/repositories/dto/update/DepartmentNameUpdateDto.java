package com.example.EmployeeDirectory.repositories.dto.update;

import jakarta.validation.constraints.Size;

public record DepartmentNameUpdateDto(String departmentName, @Size(min=6, max=120) String newDepartmentName) {
}
