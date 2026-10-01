package com.example.EmployeeDirectory.services.impl;

import com.example.EmployeeDirectory.entity.City;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.repositories.CityRepository;
import com.example.EmployeeDirectory.repositories.DepartmentRepository;
import com.example.EmployeeDirectory.repositories.EmployeeRepository;
import com.example.EmployeeDirectory.repositories.dto.create.EmployeeCreateDto;
import com.example.EmployeeDirectory.repositories.dto.update.EmployeeUpdateDto;
import com.example.EmployeeDirectory.services.EmployeeService;
import com.example.EmployeeDirectory.web.exception.ConflictException;
import com.example.EmployeeDirectory.web.exception.ModelNotFoundException;
import com.example.EmployeeDirectory.web.responses.EmployeeResponseDto;
import com.example.EmployeeDirectory.web.views.EmployeeView;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final CityRepository cityRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper, CityRepository cityRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.cityRepository = cityRepository;
        this.departmentRepository = departmentRepository;
    }


    @Override
    public List<EmployeeView> getAll(Pageable pageable) {
        return employeeRepository.findAllDto(pageable).stream().toList();
    }

    @Override
    public List<EmployeeView> getAllByCity(Pageable pageable, String cityName) {
        return null;
    }

    @Override
    public List<EmployeeView> getAllByDepartment(Pageable pageable, String departmentName) {
        return null;
    }

    @Override
    public List<EmployeeView> getAllByDepartmentAndCity(Pageable pageable, String departmentName, String cityName) {
        return null;
    }

    @Override
    public EmployeeView getByDocsNo(Pageable pageable, String docsNo) {
        return null;
    }

    @Override
    public EmployeeView getByPhoneNumber(Pageable pageable, String phoneNumber) {
        return null;
    }

    @Override
    public List<EmployeeView> getByFullName(Pageable pageable, String firstName, String secondName) {
        return null;
    }

    @Transactional
    @Override
    public EmployeeResponseDto create(EmployeeCreateDto dto) {
        Optional<City> city = cityRepository.findByName(dto.cityName());
        if(city.isEmpty()){
            throw new ModelNotFoundException("Указанный город не найден! Проверьте название или добавьте новый город.");
        }
        Optional<Department> department = departmentRepository.getByName(dto.departmentName());
        if(department.isEmpty()){
            throw new ModelNotFoundException("Указанный отдел не найден! Проверьте название или добавьте новый город.");
        }
        Employee employee = modelMapper.map(dto, Employee.class);
        employeeRepository.save(employee);
        return modelMapper.map(employee, EmployeeResponseDto.class);
    }


}
