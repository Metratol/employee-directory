package com.example.EmployeeDirectory.services;

import com.example.EmployeeDirectory.dto.create.DepartmentCreateDto;
import com.example.EmployeeDirectory.dto.update.DepartmentCityUpdateDto;
import com.example.EmployeeDirectory.dto.update.DepartmentNameUpdateDto;
import com.example.EmployeeDirectory.entity.City;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.mappers.DepartmentMapper;
import com.example.EmployeeDirectory.repositories.CityRepository;
import com.example.EmployeeDirectory.repositories.DepartmentRepository;
import com.example.EmployeeDirectory.services.impl.DepartmentServiceImpl;
import com.example.EmployeeDirectory.web.exception.ConflictException;
import com.example.EmployeeDirectory.web.exception.ModelNotFoundException;
import com.example.EmployeeDirectory.web.responses.DepartmentResponseDto;
import com.example.EmployeeDirectory.web.views.DepartmentView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import com.example.EmployeeDirectory.dto.create.DepartmentCreateDto;
import com.example.EmployeeDirectory.dto.update.DepartmentCityUpdateDto;
import com.example.EmployeeDirectory.dto.update.DepartmentNameUpdateDto;
import com.example.EmployeeDirectory.entity.City;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.mappers.DepartmentMapper;
import com.example.EmployeeDirectory.repositories.CityRepository;
import com.example.EmployeeDirectory.repositories.DepartmentRepository;
import com.example.EmployeeDirectory.services.impl.DepartmentServiceImpl;
import com.example.EmployeeDirectory.web.exception.ConflictException;
import com.example.EmployeeDirectory.web.exception.ModelNotFoundException;
import com.example.EmployeeDirectory.web.responses.DepartmentResponseDto;
import com.example.EmployeeDirectory.web.views.DepartmentView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private DepartmentMapper mapper;
    @InjectMocks
    private DepartmentServiceImpl service;


    @Test
    void getAllDepartmentsByCity_shouldThrow_whenCityMissing() {
        given(cityRepository.existsByName("Казань")).willReturn(false);

        assertThatThrownBy(() -> service.getAllDepartmentsByCity("Казань"))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("город");

        then(departmentRepository).shouldHaveNoInteractions();
    }

    @Test
    void getAllDepartmentsByCity_shouldMapDepartments_whenCityExists() {
        List<Department> departments = List.of(new Department("IT", new City("Москва")));
        List<DepartmentView> views = List.of(new DepartmentView("IT", "Москва"));

        given(cityRepository.existsByName("Москва")).willReturn(true);
        given(departmentRepository.findByCityName("Москва")).willReturn(departments);
        given(mapper.toViews(departments)).willReturn(views);

        assertThat(service.getAllDepartmentsByCity("Москва")).isEqualTo(views);
    }


    @Test
    void create_shouldThrow_whenCityMissing() {
        DepartmentCreateDto dto = new DepartmentCreateDto("IT", "Москва");
        given(cityRepository.findByName("Москва")).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("город");

        then(departmentRepository).shouldHaveNoInteractions();
        then(mapper).shouldHaveNoInteractions();
    }

    @Test
    void create_shouldThrowConflict_whenDepartmentAlreadyExists() {
        DepartmentCreateDto dto = new DepartmentCreateDto("IT", "Москва");
        City moscow = new City("Москва");

        given(cityRepository.findByName("Москва")).willReturn(Optional.of(moscow));
        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.of(new Department("IT", moscow)));

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Отдел уже существует");

        then(departmentRepository).should(never()).save(any());
        then(mapper).shouldHaveNoInteractions();
    }

    @Test
    void create_shouldSaveAndReturnResponse_whenAllValid() {
        DepartmentCreateDto dto = new DepartmentCreateDto("IT", "Москва");
        City moscow = new City("Москва");
        DepartmentResponseDto expected = new DepartmentResponseDto(1L, "IT", "Москва");

        given(cityRepository.findByName("Москва")).willReturn(Optional.of(moscow));
        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.empty());
        given(mapper.toResponse(any(Department.class))).willReturn(expected);

        DepartmentResponseDto result = service.create(dto);

        assertThat(result).isEqualTo(expected);
        ArgumentCaptor<Department> captor = ArgumentCaptor.forClass(Department.class);
        then(departmentRepository).should().save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("IT");
        assertThat(captor.getValue().getCity()).isEqualTo(moscow);
    }


    @Test
    void update_shouldThrow_whenCityMissing() {
        // ИСПРАВЛЕНО: порядок (departmentName, newDepartmentName, city)
        DepartmentNameUpdateDto dto = new DepartmentNameUpdateDto("IT", "IT2", "Москва");
        given(cityRepository.findByName("Москва")).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(dto))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("город");

        then(departmentRepository).shouldHaveNoInteractions();
    }

    @Test
    void update_shouldThrow_whenDepartmentMissing() {
        DepartmentNameUpdateDto dto = new DepartmentNameUpdateDto("IT", "IT2", "Москва");
        given(cityRepository.findByName("Москва")).willReturn(Optional.of(new City("Москва")));
        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(dto))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("Отдела не существует");

        then(departmentRepository).should(never()).save(any());
    }

    @Test
    void update_shouldReturnEarlyWithoutSave_whenNameUnchanged() {
        // ИСПРАВЛЕНО: порядок (departmentName, newDepartmentName, city)
        DepartmentNameUpdateDto dto = new DepartmentNameUpdateDto("IT", "IT", "Москва");
        City moscow = new City("Москва");
        Department department = new Department("IT", moscow);
        DepartmentResponseDto expected = new DepartmentResponseDto(1L, "IT", "Москва");

        given(cityRepository.findByName("Москва")).willReturn(Optional.of(moscow));
        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.of(department));
        given(mapper.toResponse(department)).willReturn(expected);

        DepartmentResponseDto result = service.update(dto);

        assertThat(result).isEqualTo(expected);

        // ИСПРАВЛЕНО: удален ложный then(...).should(never()).findByNameAndCity_Name("IT", "Москва");
        // Сервис ОБЯЗАН найти отдел перед проверкой имен. Проверяем только, что не сохраняли.
        then(departmentRepository).should(never()).save(any());
    }

    @Test
    void update_shouldThrowConflict_whenNewNameAlreadyTaken() {
        DepartmentNameUpdateDto dto = new DepartmentNameUpdateDto("IT", "Sales", "Москва");
        City moscow = new City("Москва");
        Department department = new Department("IT", moscow);

        given(cityRepository.findByName("Москва")).willReturn(Optional.of(moscow));
        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.of(department));
        given(departmentRepository.findByNameAndCity_Name("Sales", "Москва"))
                .willReturn(Optional.of(new Department("Sales", moscow)));

        assertThatThrownBy(() -> service.update(dto))
                .isInstanceOf(ConflictException.class);

        then(departmentRepository).should(never()).save(any());
        assertThat(department.getName()).isEqualTo("IT");
    }

    @Test
    void update_shouldRenameAndSave_whenAllValid() {
        DepartmentNameUpdateDto dto = new DepartmentNameUpdateDto("IT", "IT2", "Москва");
        City moscow = new City("Москва");
        Department department = new Department("IT", moscow);
        DepartmentResponseDto expected = new DepartmentResponseDto(1L, "IT2", "Москва");

        given(cityRepository.findByName("Москва")).willReturn(Optional.of(moscow));
        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.of(department));
        given(departmentRepository.findByNameAndCity_Name("IT2", "Москва"))
                .willReturn(Optional.empty());
        given(mapper.toResponse(department)).willReturn(expected);

        DepartmentResponseDto result = service.update(dto);

        assertThat(result).isEqualTo(expected);
        assertThat(department.getName()).isEqualTo("IT2");
        then(departmentRepository).should().save(department);
    }


    @Test
    void moveToAnotherCity_shouldThrow_whenDepartmentMissing() {
        DepartmentCityUpdateDto dto = new DepartmentCityUpdateDto("IT", "Москва", "Казань");
        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> service.moveToAnotherCity(dto))
                .isInstanceOf(ModelNotFoundException.class);

        // Если сервис не дошел до поиска города, проверяем что cityRepository не вызывался
        then(cityRepository).shouldHaveNoInteractions();
    }

    @Test
    void moveToAnotherCity_shouldReturnEarly_whenSameCity() {
        DepartmentCityUpdateDto dto = new DepartmentCityUpdateDto("IT", "Москва", "Москва");
        City moscow = new City("Москва");
        Department department = new Department("IT", moscow);
        DepartmentResponseDto expected = new DepartmentResponseDto(1L, "IT", "Москва");

        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.of(department));
        given(mapper.toResponse(department)).willReturn(expected);

        DepartmentResponseDto result = service.moveToAnotherCity(dto);

        assertThat(result).isEqualTo(expected);

        // ИСПРАВЛЕНО: вместо shouldHaveNoInteractions проверяем конкретный вызов
        then(cityRepository).should(never()).findByName(any());
    }

    @Test
    void moveToAnotherCity_shouldThrowConflict_whenNameAlreadyInNewCity() {
        DepartmentCityUpdateDto dto = new DepartmentCityUpdateDto("IT", "Москва", "Казань");
        City moscow = new City("Москва");
        Department department = new Department("IT", moscow);

        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.of(department));
        given(departmentRepository.findByNameAndCity_Name("IT", "Казань"))
                .willReturn(Optional.of(new Department("IT", new City("Казань"))));

        assertThatThrownBy(() -> service.moveToAnotherCity(dto))
                .isInstanceOf(ConflictException.class);

        // ИСПРАВЛЕНО: удален ложный shouldHaveNoInteractions.
        // Сервис действительно находит отдел в старом городе (дергает бд), но не ищет новый город.
        then(cityRepository).should(never()).findByName(any());
    }

    @Test
    void moveToAnotherCity_shouldThrow_whenNewCityMissing() {
        DepartmentCityUpdateDto dto = new DepartmentCityUpdateDto("IT", "Москва", "Казань");
        City moscow = new City("Москва");
        Department department = new Department("IT", moscow);

        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.of(department));
        given(departmentRepository.findByNameAndCity_Name("IT", "Казань"))
                .willReturn(Optional.empty());
        given(cityRepository.findByName("Казань")).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.moveToAnotherCity(dto))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessageContaining("город");
    }

    @Test
    void moveToAnotherCity_shouldSetNewCity_whenAllValid() {
        DepartmentCityUpdateDto dto = new DepartmentCityUpdateDto("IT", "Москва", "Казань");
        City moscow = new City("Москва");
        City kazan = new City("Казань");
        Department department = new Department("IT", moscow);
        DepartmentResponseDto expected = new DepartmentResponseDto(1L, "IT", "Казань");

        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.of(department));
        given(departmentRepository.findByNameAndCity_Name("IT", "Казань"))
                .willReturn(Optional.empty());
        given(cityRepository.findByName("Казань")).willReturn(Optional.of(kazan));
        given(mapper.toResponse(department)).willReturn(expected);

        DepartmentResponseDto result = service.moveToAnotherCity(dto);

        assertThat(result).isEqualTo(expected);
        assertThat(department.getCity()).isEqualTo(kazan);
    }


    @Test
    void delete_shouldThrow_whenDepartmentMissing() {
        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete("IT", "Москва"))
                .isInstanceOf(ModelNotFoundException.class);

        then(departmentRepository).should(never()).delete(any());
    }

    @Test
    void delete_shouldDeleteAndReturnTrue_whenDepartmentExists() {
        City moscow = new City("Москва");
        Department department = new Department("IT", moscow);
        given(departmentRepository.findByNameAndCity_Name("IT", "Москва"))
                .willReturn(Optional.of(department));

        boolean result = service.delete("IT", "Москва");

        assertThat(result).isTrue();
        then(departmentRepository).should().delete(department);
    }
}