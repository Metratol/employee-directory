package com.example.EmployeeDirectory.services;

import com.example.EmployeeDirectory.repositories.dto.create.EmployeeCreateDto;
import com.example.EmployeeDirectory.repositories.dto.update.EmployeeUpdateDto;
import com.example.EmployeeDirectory.web.responses.EmployeeResponseDto;
import com.example.EmployeeDirectory.web.views.EmployeeView;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EmployeeService {

    Page<EmployeeView> getAll(Pageable pageable);
    Page<EmployeeView> getAllByCity(Pageable pageable, String cityName);

    Page<EmployeeView> getAllByDepartment(Pageable pageable, String departmentName);
    Page<EmployeeView> getAllByDepartmentAndCity(Pageable pageable, String departmentName, String cityName);

    EmployeeView getByDocsNo(String docsNo);

    EmployeeView getByPhoneNumber(String phoneNumber);

    List<EmployeeView> getByFullName(String firstName, String secondName);

    EmployeeResponseDto create (EmployeeCreateDto dto);
    EmployeeResponseDto update(EmployeeUpdateDto dto);
}
