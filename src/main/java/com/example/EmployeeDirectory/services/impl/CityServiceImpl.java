package com.example.EmployeeDirectory.services.impl;

import com.example.EmployeeDirectory.entity.City;
import com.example.EmployeeDirectory.web.exception.ConflictException;
import com.example.EmployeeDirectory.repositories.CityRepository;
import com.example.EmployeeDirectory.repositories.dto.create.CityCreateDto;
import com.example.EmployeeDirectory.web.responses.CityResponseDto;
import com.example.EmployeeDirectory.services.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository repository;
    private final ModelMapper modelMapper;

    public CityServiceImpl(CityRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CityCreateDto> getAll() {
        return repository.findAll().stream()
                .map(city -> modelMapper.map(city, CityCreateDto.class))
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public CityResponseDto create(CityCreateDto city) {
        if(repository.existsByName(city.name())){
            throw new ConflictException("Город с указанным названием уже существует");
        }
        City newCity = new City(city.name());
        repository.save(newCity);
        return modelMapper.map(newCity, CityResponseDto.class);



    }
}
