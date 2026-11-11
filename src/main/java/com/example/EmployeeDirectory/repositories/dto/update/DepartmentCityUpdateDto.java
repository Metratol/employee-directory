package com.example.EmployeeDirectory.repositories.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentCityUpdateDto(@NotBlank @Size(min = 2) String departmentName, @NotBlank String oldCityName, @NotBlank @Size(min = 2) String newCityName) {
}
