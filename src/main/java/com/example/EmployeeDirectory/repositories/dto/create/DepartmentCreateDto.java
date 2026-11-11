package com.example.EmployeeDirectory.repositories.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentCreateDto(
        @NotNull @Size(min=6, max=120) String name,
        @NotNull String cityName) {
}
