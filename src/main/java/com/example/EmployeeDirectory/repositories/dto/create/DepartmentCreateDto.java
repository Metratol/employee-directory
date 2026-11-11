package com.example.EmployeeDirectory.repositories.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentCreateDto(
        @NotNull @Size(min=2, max=70) String name,
        @NotNull @NotBlank String cityName) {
}
