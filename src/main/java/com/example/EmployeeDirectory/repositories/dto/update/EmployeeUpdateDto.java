package com.example.EmployeeDirectory.repositories.dto.update;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeUpdateDto(
        @NotNull String docsNo,
        @NotNull @Size(min = 2, max = 25) String firstName,
        @NotNull @Size(min = 2, max = 40) String secondName,
        @NotNull @Pattern(regexp = "\\+?[0-9]{7,15}") String phoneNumber,
        @NotNull String departmentName
) {}