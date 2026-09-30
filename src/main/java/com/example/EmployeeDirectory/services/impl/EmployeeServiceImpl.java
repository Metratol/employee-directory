package com.example.EmployeeDirectory.services.impl;

import com.example.EmployeeDirectory.repositories.EmployeeRepository;
import com.example.EmployeeDirectory.repositories.dto.EmployeeDto;
import com.example.EmployeeDirectory.services.EmployeeService;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public List<EmployeeDto> getAll(Pageable pageable) {
        return employeeRepository.findAllDto(pageable).stream().toList();
    }

    @Override
    public List<EmployeeDto> getAllByCity(Pageable pageable, String cityName) {
        return null;
    }

    @Override
    public List<EmployeeDto> getAllByDepartment(Pageable pageable, String departmentName) {
        return null;
    }

    @Override
    public List<EmployeeDto> getAllByDepartmentAndCity(Pageable pageable, String departmentName, String cityName) {
        return null;
    }

    @Override
    public EmployeeDto getByDocsNo(Pageable pageable, String docsNo) {
        return null;
    }

    @Override
    public EmployeeDto getByPhoneNumber(Pageable pageable, String phoneNumber) {
        return null;
    }

    @Override
    public List<EmployeeDto> getByFullName(Pageable pageable, String firstName, String secondName) {
        return null;
    }
}
