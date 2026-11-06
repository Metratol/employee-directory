package com.example.EmployeeDirectory.mappers;

import com.example.EmployeeDirectory.entity.City;
import com.example.EmployeeDirectory.web.responses.CityResponseDto;
import com.example.EmployeeDirectory.web.views.CityView;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityResponseDto toResponse(City city);
    List<CityView> toViews(List<City> models);
}
