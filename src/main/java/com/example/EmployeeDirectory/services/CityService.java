package com.example.EmployeeDirectory.services;

import com.example.EmployeeDirectory.repositories.dto.create.CityCreateDto;
import com.example.EmployeeDirectory.web.responses.CityResponseDto;
import com.example.EmployeeDirectory.web.views.CityView;

import java.util.List;

public interface CityService {

    List<CityView> getAll();
    CityResponseDto create(CityCreateDto city);
    boolean delete(String name);


}
