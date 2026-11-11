package com.example.EmployeeDirectory.repositories.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentNameUpdateDto(@NotNull @NotBlank String departmentName, @Size(min=6, max=70) String newDepartmentName, @NotNull @NotBlank String city) {
}
