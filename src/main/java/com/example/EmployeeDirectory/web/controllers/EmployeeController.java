package com.example.EmployeeDirectory.web.controllers;

import com.example.EmployeeDirectory.repositories.dto.create.EmployeeCreateDto;
import com.example.EmployeeDirectory.repositories.dto.update.EmployeeUpdateDto;
import com.example.EmployeeDirectory.services.EmployeeService;
import com.example.EmployeeDirectory.web.exception.ConflictException;
import com.example.EmployeeDirectory.web.responses.EmployeeResponseDto;
import com.example.EmployeeDirectory.web.views.EmployeeView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Validated
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final int PAGE_SIZE = 10;
    @GetMapping
    public ResponseEntity<Page<EmployeeView>> getAll(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String department,
            @PageableDefault(size = PAGE_SIZE) Pageable pageable
    ) {
        if (city != null && department != null) {
            return ResponseEntity.ok(
                    employeeService.getAllByDepartmentAndCity(
                            pageable, department, city
                    )
            );
        }

        if (city != null) {
            return ResponseEntity.ok(
                    employeeService.getAllByCity(pageable, city)
            );
        }

        if (department != null) {
            return ResponseEntity.ok(
                    employeeService.getAllByDepartment(pageable, department)
            );
        }

        return ResponseEntity.ok(employeeService.getAll(pageable));
    }
    @GetMapping("/phoneNumber")
    public ResponseEntity<EmployeeView> byPhoneNumber(@RequestParam("phoneNumber")
                                                             @Pattern(regexp = "^(\\+7|7|8)?\\d{10}$",
                                                                     message = "Некорректный номер телефона")
                                                                        String phone){
        return ResponseEntity.ok(employeeService.getByPhoneNumber(phone));
    }
    @GetMapping("/docs")
    public ResponseEntity<EmployeeView> byDocsNumber(@RequestParam("docsNumber") String docs){
        return ResponseEntity.ok(employeeService.getByDocsNo(docs));
    }
    @GetMapping("/fullname")
    public ResponseEntity<Page<EmployeeView>> byFullName(@RequestParam String name, @RequestParam String secondName, @PageableDefault(size = PAGE_SIZE) Pageable pageable){
        return ResponseEntity.ok(employeeService.getByFullName(name,secondName, pageable));
    }
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> create(@Valid @RequestBody EmployeeCreateDto createDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.create(createDto));
    }
    @PatchMapping
    public ResponseEntity<EmployeeResponseDto> update(@Valid @RequestBody EmployeeUpdateDto dto){
        return ResponseEntity.ok(employeeService.update(dto));
    }
    @DeleteMapping("/{docsNo}")
    public ResponseEntity<String> delete(@PathVariable("docsNo") String docsNo){
        if(!employeeService.delete(docsNo)) throw new ConflictException("Не удалось удалить");
        return ResponseEntity.ok("Удален пользователь");
    }
}
