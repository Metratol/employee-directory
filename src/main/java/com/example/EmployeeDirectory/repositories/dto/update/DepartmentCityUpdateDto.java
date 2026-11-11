package com.example.EmployeeDirectory.repositories.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentCityUpdateDto(@NotNull @NotBlank @Size(min = 2) String departmentName, @NotNull @NotBlank String oldCityName, @NotNull @Size(min = 2) String newCityName) {
}
