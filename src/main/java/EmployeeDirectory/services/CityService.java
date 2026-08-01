package EmployeeDirectory.services;

import EmployeeDirectory.repositories.dto.CityDto;

import java.util.List;

public interface CityService {

    List<CityDto> getAll();

}
