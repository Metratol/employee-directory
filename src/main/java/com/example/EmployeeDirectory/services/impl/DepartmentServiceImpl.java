package com.example.EmployeeDirectory.services.impl;

import com.example.EmployeeDirectory.entity.City;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.mappers.DepartmentMapper;
import com.example.EmployeeDirectory.web.exception.ConflictException;
import com.example.EmployeeDirectory.repositories.CityRepository;
import com.example.EmployeeDirectory.repositories.DepartmentRepository;
import com.example.EmployeeDirectory.repositories.dto.create.DepartmentCreateDto;
import com.example.EmployeeDirectory.services.DepartmentService;
import com.example.EmployeeDirectory.web.exception.ModelNotFoundException;
import com.example.EmployeeDirectory.web.responses.DepartmentResponseDto;
import com.example.EmployeeDirectory.web.views.DepartmentView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {


    private final DepartmentRepository departmentRepository;
    private final CityRepository cityRepository;
    private final DepartmentMapper mapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, CityRepository cityRepository, DepartmentMapper mapper) {
        this.departmentRepository = departmentRepository;
        this.cityRepository = cityRepository;
        this.mapper = mapper;
    }

    @Override
    public List<DepartmentView> getAll() {
    return mapper.toViews(departmentRepository.findAll());
    }

    @Override
    public List<DepartmentView> getAllDepartmentsByCity(String cityName) {
        if(!cityRepository.existsByName(cityName)){
            throw new ConflictException("Не найден город с указанным названием! Проверьте название.");
        }
        return mapper.toViews(departmentRepository.findByCityName(cityName));
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
        return mapper.toResponse(department);

    }

}
