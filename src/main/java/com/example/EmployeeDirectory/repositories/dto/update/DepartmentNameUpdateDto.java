package com.example.EmployeeDirectory.repositories.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentNameUpdateDto(@NotBlank String departmentName,@NotBlank @Size(min=2, max=70) String newDepartmentName,  @NotBlank String city) {
}
