package com.example.EmployeeDirectory.services.impl;

import com.example.EmployeeDirectory.repositories.CityRepository;
import com.example.EmployeeDirectory.repositories.dto.CityDto;
import com.example.EmployeeDirectory.services.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CityDto> getAll() {
        return cityRepository.findAll().stream()
                .map(city -> modelMapper.map(city, CityDto.class))
                .collect(Collectors.toList());
    }
}
