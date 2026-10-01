package com.example.EmployeeDirectory.services;

import com.example.EmployeeDirectory.repositories.dto.create.CityCreateDto;
import com.example.EmployeeDirectory.web.responses.CityResponseDto;

import java.util.List;

public interface CityService {

    List<CityCreateDto> getAll();
    CityResponseDto create(CityCreateDto city);


}
