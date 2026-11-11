package com.example.EmployeeDirectory.web.controllers;

import com.example.EmployeeDirectory.repositories.dto.create.CityCreateDto;
import com.example.EmployeeDirectory.services.CityService;
import com.example.EmployeeDirectory.web.exception.ConflictException;
import com.example.EmployeeDirectory.web.responses.CityResponseDto;
import com.example.EmployeeDirectory.web.views.CityView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<CityResponseDto> create(@Valid @RequestBody CityCreateDto create) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cityService.create(create));
    }
    @GetMapping
    public ResponseEntity<List<CityView>> all() {
        return ResponseEntity.ok(cityService.getAll());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> delete(@PathVariable("name") String name){
        if(!cityService.delete(name)) throw new ConflictException("Не удалось удалить");
        return ResponseEntity.ok(String.format("Город %s успешно удален", name));
    }
}