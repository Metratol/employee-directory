package com.example.EmployeeDirectory.services;

import com.example.EmployeeDirectory.repositories.dto.CityDto;

import java.util.List;

public interface CityService {

    List<CityDto> getAll();

}
