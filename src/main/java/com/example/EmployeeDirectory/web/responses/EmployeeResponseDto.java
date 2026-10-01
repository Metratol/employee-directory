package com.example.EmployeeDirectory.web.responses;

import com.example.EmployeeDirectory.entity.Department;

public record EmployeeResponseDto(String firstName, String secondName, String phoneNumber, Department departmentName, String cityName){
}
