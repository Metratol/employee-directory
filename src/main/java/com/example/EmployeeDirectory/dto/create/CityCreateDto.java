package com.example.EmployeeDirectory.dto.create;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CityCreateDto(@NotBlank @Size(min=4, max=40) String name) {
}

