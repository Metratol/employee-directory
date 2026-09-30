package com.example.EmployeeDirectory.repositories.dto;

import jakarta.validation.constraints.Size;

public record DepartmentDto(
        @Size(min=6, max=120) String name,
        String cityName) {
}
