package com.example.EmployeeDirectory.services;

import com.example.EmployeeDirectory.entity.City;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.mappers.EmployeeMapper;
import com.example.EmployeeDirectory.repositories.CityRepository;
import com.example.EmployeeDirectory.repositories.DepartmentRepository;
import com.example.EmployeeDirectory.repositories.EmployeeRepository;
import com.example.EmployeeDirectory.dto.create.EmployeeCreateDto;
import com.example.EmployeeDirectory.dto.update.EmployeeUpdateDto;
import com.example.EmployeeDirectory.services.impl.EmployeeServiceImpl;
import com.example.EmployeeDirectory.web.exception.ModelNotFoundException;
import com.example.EmployeeDirectory.web.responses.EmployeeResponseDto;
import com.example.EmployeeDirectory.web.views.EmployeeView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock private EmployeeRepository employeeRepository;
    @Mock private CityRepository cityRepository;
    @Mock private DepartmentRepository departmentRepository;
    @Mock private EmployeeMapper mapper;
    @InjectMocks private EmployeeServiceImpl service;


    @Test
    void getByDocsNo_shouldReturnView_whenEmployeeExists() {
        EmployeeView view = new EmployeeView("Иван", "Иванов", "+7999", "IT","Москва");
        given(employeeRepository.findDtoByDocsNo("1022029000")).willReturn(Optional.of(view));

        assertThat(service.getByDocsNo("1022029000")).isEqualTo(view);
    }

    @Test
    void getByDocsNo_shouldThrowNotFound_whenEmployeeMissing() {
        given(employeeRepository.findDtoByDocsNo("1022029000")).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.getByDocsNo("1022029000"))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("номером документов");
    }



    @Test
    void getByPhoneNumber_shouldReturnView_whenEmployeeExists() {
        EmployeeView view = new EmployeeView("Иван", "Иванов", "+7999", "IT","Москва");
        given(employeeRepository.findDtoByPhoneNumber("+7999")).willReturn(Optional.of(view));

        assertThat(service.getByPhoneNumber("+7999")).isEqualTo(view);
    }

    @Test
    void getByPhoneNumber_shouldThrowNotFound_whenEmployeeMissing() {
        given(employeeRepository.findDtoByPhoneNumber("+7999")).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.getByPhoneNumber("+7999"))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("телефона");
    }


    @Test
    void create_shouldThrow_whenCityMissing() {
        EmployeeCreateDto dto = dto("Иван", "Иванов", "+7999", "1022029000", "Москва", "IT");

        given(cityRepository.findByName("Москва")).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("город");

        then(departmentRepository).shouldHaveNoInteractions();
        then(employeeRepository).should(never()).save(any());
        then(mapper).shouldHaveNoInteractions();
    }

    @Test
    void create_shouldThrow_whenDepartmentMissing() {
        EmployeeCreateDto dto = dto("Иван", "Иванов", "+7999", "1022029000", "Москва", "IT");
        City moscow = new City("Москва");

        given(cityRepository.findByName("Москва")).willReturn(Optional.of(moscow));
        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("отдел");

        then(employeeRepository).should(never()).save(any());
        then(mapper).shouldHaveNoInteractions();
    }

    @Test
    void create_shouldSaveAndReturnResponse_whenEverythingFound() {
        EmployeeCreateDto dto = dto("Иван", "Иванов", "+7999", "1022029000", "Москва", "IT");
        City moscow = new City("Москва");
        Department it = new Department("IT", moscow);
        Employee employee = new Employee("Иван", "Иванов", "+7999", "1022029000", it);
        EmployeeResponseDto expected = new EmployeeResponseDto("Васька", "Иван", "+1","HR","Москва");

        given(cityRepository.findByName("Москва")).willReturn(Optional.of(moscow));
        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.of(it));
        given(mapper.toModel(dto, it)).willReturn(employee);
        given(mapper.toResponse(employee)).willReturn(expected);

        EmployeeResponseDto result = service.create(dto);

        assertThat(result).isEqualTo(expected);
        then(employeeRepository).should().save(employee);
    }


    @Test
    void update_shouldThrow_whenEmployeeMissing() {
        EmployeeUpdateDto dto = updateDto("1022029000", "Москва", "IT");

        given(employeeRepository.findByDocsNo("1022029")).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(dto))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("документами");

        then(departmentRepository).shouldHaveNoInteractions();
        then(employeeRepository).should(never()).save(any());
        then(mapper).shouldHaveNoInteractions();
    }

    @Test
    void update_shouldThrow_whenDepartmentMissing() {
        EmployeeUpdateDto dto = updateDto("1022029000", "Москва", "IT");
        Employee employee = new Employee("Иван", "Иванов", "+7999", "1022029000",
                new Department("Sales", new City("Пермь")));

        given(employeeRepository.findByDocsNo("1022029000")).willReturn(Optional.of(employee));
        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(dto))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("отдел");

        then(employeeRepository).should(never()).save(any());
        then(mapper).shouldHaveNoInteractions();
    }

    @Test
    void update_shouldApplyMapperAndSave_whenEverythingFound() {
        EmployeeUpdateDto dto = updateDto("1022029", "Москва", "IT");
        Employee employee = new Employee("Иван", "Иванов", "+7999", "1022029000",
                new Department("Sales", new City("Пермь")));
        Department it = new Department("IT", new City("Москва"));
        EmployeeResponseDto expected = new EmployeeResponseDto("Васька", "Иван", "+2","IT","Казань");

        given(employeeRepository.findByDocsNo("1022029000")).willReturn(Optional.of(employee));
        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.of(it));
        given(mapper.toResponse(employee)).willReturn(expected);

        EmployeeResponseDto result = service.update(dto);

        assertThat(result).isEqualTo(expected);
        then(mapper).should().update(dto, it, employee);
        then(employeeRepository).should().save(employee);
    }


    @Test
    void delete_shouldThrow_whenEmployeeMissing() {
        given(employeeRepository.findByDocsNo("1022029000")).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete("1022029000"))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("не найден");

        then(employeeRepository).should(never()).delete(any());
    }

    @Test
    void delete_shouldDeleteAndReturnTrue_whenEmployeeExists() {
        Employee employee = new Employee("Иван", "Иванов", "+7999", "1022029000",
                new Department("IT", new City("Москва")));
        given(employeeRepository.findByDocsNo("1022029")).willReturn(Optional.of(employee));

        boolean result = service.delete("1022029000");

        assertThat(result).isTrue();
        then(employeeRepository).should().delete(employee);
    }


    private EmployeeCreateDto dto(String first, String last, String phone,
                                  String docs, String city, String dept) {
        return new EmployeeCreateDto(first, last, docs, phone, dept, city);
    }

    private EmployeeUpdateDto updateDto(String docs, String city, String dept) {
        return new EmployeeUpdateDto(docs, "Иван", "Иванов", "+7999", dept, city);
    }
}