package com.example.EmployeeDirectory.repositories.dto.update;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmployeeUpdateDto(
        @Size(min = 2, max = 25) String firstName,
        @Size(min = 2, max = 40) String secondName,
        @Pattern(regexp = "\\+?[0-9]{7,15}") String phoneNumber,
        String departmentName,
        String cityName
) {}