package com.example.EmployeeDirectory.repositories.dto;


import jakarta.validation.constraints.Size;

public record CityDto(@Size(min=4, max=70) String name) {
}

