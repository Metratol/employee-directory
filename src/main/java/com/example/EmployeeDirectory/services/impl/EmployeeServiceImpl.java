package com.example.EmployeeDirectory.services.impl;

import com.example.EmployeeDirectory.entity.City;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.mappers.EmployeeMapper;
import com.example.EmployeeDirectory.repositories.CityRepository;
import com.example.EmployeeDirectory.repositories.DepartmentRepository;
import com.example.EmployeeDirectory.repositories.EmployeeRepository;
import com.example.EmployeeDirectory.repositories.dto.create.EmployeeCreateDto;
import com.example.EmployeeDirectory.repositories.dto.update.EmployeeUpdateDto;
import com.example.EmployeeDirectory.services.EmployeeService;
import com.example.EmployeeDirectory.web.exception.ModelNotFoundException;
import com.example.EmployeeDirectory.web.responses.EmployeeResponseDto;
import com.example.EmployeeDirectory.web.views.EmployeeView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final CityRepository cityRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper mapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CityRepository cityRepository, DepartmentRepository departmentRepository, EmployeeMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.cityRepository = cityRepository;
        this.departmentRepository = departmentRepository;
        this.mapper = mapper;
    }


    @Override
    public Page<EmployeeView> getAll(Pageable pageable) {
        return employeeRepository.findAllDto(pageable);
    }

    @Override
    public Page<EmployeeView> getAllByCity(Pageable pageable, String cityName) {
        return employeeRepository.findAllDtoByCityName(pageable, cityName);
    }

    @Override
    public Page<EmployeeView> getAllByDepartment(Pageable pageable, String departmentName) {
        return employeeRepository.findAllDtoByDepartmentName(pageable, departmentName);
    }

    @Override
    public Page<EmployeeView> getAllByDepartmentAndCity(Pageable pageable, String departmentName, String cityName) {
        return employeeRepository.findAllDtoByCityAndDepartmentNames(pageable,cityName, departmentName);
    }

    @Override
    public EmployeeView getByDocsNo(String docsNo) {
        return employeeRepository.findDtoByDocsNo(docsNo).orElseThrow(() -> new ModelNotFoundException("Не найден пользователь с указанным номером документов"));
    }

    @Override
    public EmployeeView getByPhoneNumber(String phoneNumber) {
        return employeeRepository.findDtoByPhoneNumber(phoneNumber).orElseThrow(() -> new ModelNotFoundException("Не найден пользователь с указанным номером телефона"));
    }

    @Override
    public List<EmployeeView> getByFullName(String firstName, String secondName) {
        return employeeRepository.findAllDtoByFullName(firstName, secondName);
    }

    @Transactional
    @Override
    public EmployeeResponseDto create(EmployeeCreateDto dto) {
        City city = cityRepository.findByName(dto.cityName())
                .orElseThrow(() -> new ModelNotFoundException("Указанный город не найден! Проверьте название или добавьте новый город через /cities."));
        Department department = departmentRepository.findByNameAndCity_Name(dto.departmentName(),dto.cityName())
                .orElseThrow(() -> new ModelNotFoundException("Указанный отдел не найден! Проверьте название или добавьте новый отдел через /departments."));
        Employee employee = mapper.toModel(dto, department);
        employeeRepository.save(employee);
        return mapper.toResponse(employee);
    }
    @Transactional
    @Override
    public EmployeeResponseDto update(EmployeeUpdateDto dto) {
        Employee employee = employeeRepository.findByDocsNo(dto.docsNo()).
                orElseThrow(() -> new ModelNotFoundException("Пользователь с указанными документами не найден"));
        Department department = departmentRepository.findByNameAndCity_Name(dto.departmentName(),dto.cityName())
                .orElseThrow(() -> new ModelNotFoundException("Указанный отдел не найден! Проверьте название или добавьте новый отдел через /departments."));
        mapper.update(dto, department, employee);

        employeeRepository.save(employee);

        return mapper.toResponse(employee);
    }

    @Override
    public boolean delete(String docsNo) {
        return employeeRepository.deleteByDocsNo(docsNo);
    }


}
