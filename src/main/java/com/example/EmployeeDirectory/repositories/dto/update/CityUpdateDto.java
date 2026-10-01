package com.example.EmployeeDirectory.repositories.dto.update;

import jakarta.validation.constraints.Size;

public record CityUpdateDto(String oldName, @Size(min=4, max=70) String newName) {
}
