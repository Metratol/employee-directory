package com.example.EmployeeDirectory.repositories.dto.create;


import jakarta.validation.constraints.Size;

public record CityCreateDto(@Size(min=4, max=70) String name) {
}

