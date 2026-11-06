package com.example.EmployeeDirectory.services;

import com.example.EmployeeDirectory.repositories.dto.create.EmployeeCreateDto;
import com.example.EmployeeDirectory.web.responses.EmployeeResponseDto;
import com.example.EmployeeDirectory.web.views.EmployeeView;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface EmployeeService {

    List<EmployeeView> getAll(Pageable pageable);
    List<EmployeeView> getAllByCity(Pageable pageable, String cityName);

    List<EmployeeView> getAllByDepartment(Pageable pageable, String departmentName);
    List<EmployeeView> getAllByDepartmentAndCity(Pageable pageable, String departmentName, String cityName);

    EmployeeView getByDocsNo(Pageable pageable, String docsNo);

    EmployeeView getByPhoneNumber(Pageable pageable, String phoneNumber);

    List<EmployeeView> getByFullName(Pageable pageable, String firstName, String secondName);
    EmployeeResponseDto create (EmployeeCreateDto dto);
}
