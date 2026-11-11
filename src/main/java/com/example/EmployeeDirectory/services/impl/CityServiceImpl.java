package com.example.EmployeeDirectory.services.impl;

import com.example.EmployeeDirectory.entity.City;
import com.example.EmployeeDirectory.mappers.CityMapper;
import com.example.EmployeeDirectory.web.exception.ConflictException;
import com.example.EmployeeDirectory.repositories.CityRepository;
import com.example.EmployeeDirectory.repositories.dto.create.CityCreateDto;
import com.example.EmployeeDirectory.web.responses.CityResponseDto;
import com.example.EmployeeDirectory.services.CityService;
import com.example.EmployeeDirectory.web.views.CityView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository repository;
    private final CityMapper mapper;

    public CityServiceImpl(CityRepository repository, CityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<CityView> getAll() {
        return mapper.toViews(repository.findAll());
    }
    @Transactional
    @Override
    public CityResponseDto create(CityCreateDto city) {
        if(repository.existsByName(city.name())){
            throw new ConflictException("Город с указанным названием уже существует");
        }
        City newCity = new City(city.name());
        repository.save(newCity);
        return mapper.toResponse(newCity);



    }

    @Override
    public boolean delete(String name) {
        return repository.deleteByName(name);
    }
}
