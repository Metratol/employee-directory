package com.example.EmployeeDirectory.repositories.dto.create;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CityCreateDto(@NotNull @Size(min=4, max=70) String name) {
}

