package com.example.EmployeeDirectory.web.controllers;

import com.example.EmployeeDirectory.repositories.dto.create.DepartmentCreateDto;
import com.example.EmployeeDirectory.repositories.dto.update.DepartmentCityUpdateDto;
import com.example.EmployeeDirectory.repositories.dto.update.DepartmentNameUpdateDto;
import com.example.EmployeeDirectory.services.DepartmentService;
import com.example.EmployeeDirectory.web.responses.DepartmentResponseDto;
import com.example.EmployeeDirectory.web.views.DepartmentView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

    @PatchMapping("/updateName")
    public ResponseEntity<DepartmentResponseDto> updateName(@Valid @RequestBody DepartmentNameUpdateDto update){
        return ResponseEntity.ok(department.update(update));
    }
    @PatchMapping("/updateCity")
    public ResponseEntity<DepartmentResponseDto> updateCity(@Valid @RequestBody DepartmentCityUpdateDto update){
        return ResponseEntity.ok(department.moveToAnotherCity(update));
    }
    @DeleteMapping("/delete/{cityName}/{departmentName}")
    public ResponseEntity<String> delete(@NotNull String name, @NotNull String cityName){
        department.delete(name,cityName);
        return ResponseEntity.ok(String.format("Удален отдел %s в городе %s",name, cityName));
    }




}
