package com.example.EmployeeDirectory.repositories.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeUpdateDto(
        @NotBlank @Size(min = 10,max = 10) String docsNo,
        @NotBlank @Size(min = 2, max = 20) String firstName,
        @NotBlank @Size(min = 2, max = 25) String secondName,
        @NotBlank @Pattern(regexp = "^(\\+7|7|8)?\\d{10}$") String phoneNumber,
        @NotBlank String departmentName,
        @NotBlank String cityName
) {}