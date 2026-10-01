package com.example.EmployeeDirectory.repositories.dto.create;

import jakarta.validation.constraints.Size;

public record DepartmentCreateDto(
        @Size(min=6, max=120) String name,
        String cityName) {
}
