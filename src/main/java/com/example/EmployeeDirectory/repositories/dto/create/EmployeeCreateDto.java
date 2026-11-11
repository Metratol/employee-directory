package com.example.EmployeeDirectory.repositories.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeCreateDto(
        @NotNull @Size(min = 2, max = 25) String firstName,
        @NotNull @Size(min = 2, max = 40) String secondName,
        @NotNull @Size(min = 10, max = 10)String docsNo,

        @NotNull @Pattern(regexp = "\\+?[0-9]{7,15}") String phoneNumber,
        @NotNull @NotBlank String departmentName,
        @NotNull @NotBlank String cityName) {
}
