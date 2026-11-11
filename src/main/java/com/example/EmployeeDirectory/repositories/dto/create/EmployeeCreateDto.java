package com.example.EmployeeDirectory.repositories.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeCreateDto(
        @NotNull @Size(min = 2, max = 20) String firstName,
        @NotNull @Size(min = 2, max = 25) String secondName,
        @NotNull @Size(min = 10, max = 10)String docsNo,

        @NotNull @Pattern(regexp = "\\+?[0-9]{7,15}") String phoneNumber,
        @NotBlank String departmentName,
        @NotBlank String cityName) {
}
