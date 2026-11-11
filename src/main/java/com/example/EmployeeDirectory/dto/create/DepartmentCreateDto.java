package com.example.EmployeeDirectory.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentCreateDto(
        @NotBlank @Size(min=2, max=70) String name,
        @NotBlank String cityName) {
}
