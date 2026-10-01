package com.example.EmployeeDirectory.services.impl;

import com.example.EmployeeDirectory.entity.City;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.web.exception.ConflictException;
import com.example.EmployeeDirectory.repositories.CityRepository;
import com.example.EmployeeDirectory.repositories.DepartmentRepository;
import com.example.EmployeeDirectory.repositories.dto.create.DepartmentCreateDto;
import com.example.EmployeeDirectory.services.DepartmentService;
import com.example.EmployeeDirectory.web.exception.ModelNotFoundException;
import com.example.EmployeeDirectory.web.responses.DepartmentResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final ModelMapper modelMapper;
    private final DepartmentRepository departmentRepository;
    private final CityRepository cityRepository;

    public DepartmentServiceImpl(ModelMapper modelMapper, DepartmentRepository departmentRepository, CityRepository cityRepository) {
        this.modelMapper = modelMapper;
        this.departmentRepository = departmentRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<DepartmentCreateDto> getAll() {
        return departmentRepository.findAll().stream()
                .map(department -> modelMapper.map(department, DepartmentCreateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DepartmentCreateDto> getAllDepartmentsByCity(String cityName) {
        if(!cityRepository.existsByName(cityName)){
            throw new ConflictException("Не найден город с указанным названием! Проверьте название.");
        }
        return departmentRepository.findByCityName(cityName).stream().
                map(department -> modelMapper.map(department, DepartmentCreateDto.class))
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public DepartmentResponseDto create(DepartmentCreateDto dto) {
        Optional<City> city = cityRepository.findByName(dto.name());
        if(city.isEmpty()){
            throw new ModelNotFoundException("Указанный город не найден! Проверьте название или добавьте новый город.");
        }
        if(!departmentRepository.existsByName(dto.cityName())){
            throw new ConflictException("Отдел уже существует");
        }
        Department department = new Department(dto.name(), city.get());
        departmentRepository.save(department);
        return modelMapper.map(department, DepartmentResponseDto.class);
    }

}
