package com.example.EmployeeDirectory.repositories.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CityUpdateDto(@NotNull @NotBlank String oldName, @NotNull @Size(min=4, max=70) String newName) {
}
