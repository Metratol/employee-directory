package com.example.EmployeeDirectory.repositories.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeCreateDto(
        @Size(min = 2, max = 25) String firstName,
        @Size(min = 2, max = 40) String secondName,
        @Size(min = 10, max = 10)String docsNo,
        @Pattern(regexp = "\\+?[0-9]{7,15}")
        String phoneNumber,
        String departmentName,
        String cityName) {
}
