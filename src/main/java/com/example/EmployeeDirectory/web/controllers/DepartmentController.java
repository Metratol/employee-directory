package com.example.EmployeeDirectory.web.controllers;

import com.example.EmployeeDirectory.repositories.dto.create.DepartmentCreateDto;
import com.example.EmployeeDirectory.services.DepartmentService;
import com.example.EmployeeDirectory.web.responses.DepartmentResponseDto;
import com.example.EmployeeDirectory.web.views.DepartmentView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController{

    private final DepartmentService department;


    @GetMapping
    public ResponseEntity<List<DepartmentView>> all(){
        return ResponseEntity.ok(department.getAll());
    }
    @GetMapping("/{city}")
    public ResponseEntity<List<DepartmentView>> byCity(@PathVariable("city") String city){
        return ResponseEntity.ok(department.getAllDepartmentsByCity(city));
    }
    @PostMapping
    public ResponseEntity<DepartmentResponseDto> create(@Valid @RequestBody DepartmentCreateDto create){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(department.create(create));
    }




}
