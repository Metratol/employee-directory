package com.example.EmployeeDirectory.services.impl;

import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.repositories.DepartmentRepository;
import com.example.EmployeeDirectory.repositories.dto.DepartmentDto;
import com.example.EmployeeDirectory.services.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final ModelMapper modelMapper;
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(ModelMapper modelMapper, DepartmentRepository departmentRepository) {
        this.modelMapper = modelMapper;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentDto> getAll() {
        return departmentRepository.findAll().stream()
                .map(department -> modelMapper.map(department, DepartmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DepartmentDto> getAllDepartmentsByCity(String cityName) {
        return departmentRepository.findByCityName(cityName).stream().
                map(department -> modelMapper.map(department, DepartmentDto.class))
                .collect(Collectors.toList());
    }
}
