package com.example.EmployeeDirectory.services.impl;

import com.example.EmployeeDirectory.entity.City;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.mappers.DepartmentMapper;
import com.example.EmployeeDirectory.repositories.dto.update.DepartmentCityUpdateDto;
import com.example.EmployeeDirectory.repositories.dto.update.DepartmentNameUpdateDto;
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
            throw new ModelNotFoundException("Не найден город с указанным названием! Проверьте название.");
        }
        return mapper.toViews(departmentRepository.findByCityName(cityName));
    }
    @Transactional
    @Override
    public DepartmentResponseDto create(DepartmentCreateDto dto) {
        Optional<City> city = cityRepository.findByName(dto.cityName());
        if(city.isEmpty()){
            throw new ModelNotFoundException("Указанный город не найден! Проверьте название или добавьте новый город. Через /cities");
        }
        if(departmentRepository.findByNameAndCity_Name(dto.name(),dto.cityName()).isPresent()){
            throw new ConflictException("Отдел уже существует");
        }
        Department department = new Department(dto.name(), city.get());
        departmentRepository.save(department);
        return mapper.toResponse(department);

    }
    @Transactional
    @Override
    public DepartmentResponseDto update(DepartmentNameUpdateDto dto) {
        departmentRepository.findByNameAndCity_Name(dto.newDepartmentName(),dto.city()).orElseThrow(() -> new ConflictException("Отдел с данным названием уже существует в указанном городе"));
        Optional<City> city = cityRepository.findByName(dto.city());
        if(city.isEmpty()){
            throw new ModelNotFoundException("Указанный город не найден! Проверьте название.");
        }
        Optional<Department> container = departmentRepository.findByNameAndCity_Name(dto.departmentName(), dto.city());
        if(container.isEmpty()){
            throw new ModelNotFoundException("Отдела не существует. Проверьте название и город.");
        }
        Department department = container.get();
        department.setName(dto.newDepartmentName());

        departmentRepository.save(department);
        return mapper.toResponse(department);
    }
    @Transactional
    @Override
    public DepartmentResponseDto moveToAnotherCity(DepartmentCityUpdateDto dto) {
        departmentRepository.findByNameAndCity_Name(dto.departmentName(),dto.newCityName()).orElseThrow(() -> new ConflictException("Отдел с данным названием уже существует в указанном городе"));
        City city = cityRepository.findByName(dto.newCityName())
                .orElseThrow(() -> new ModelNotFoundException("Указанный город не найден! Проверьте название или добавьте новый город. Через /cities."));
        Department department = departmentRepository.findByNameAndCity_Name(dto.departmentName(), dto.oldCityName())
                .orElseThrow(() ->  new ModelNotFoundException("Отдела не существует. Проверьте название или город."));
        department.setCity(city);

        departmentRepository.save(department);
        return mapper.toResponse(department);
    }
    @Transactional
    @Override
    public boolean delete(String name, String cityName) {
        return departmentRepository.deleteByNameAndCity_Name(name, cityName);
    }

}
