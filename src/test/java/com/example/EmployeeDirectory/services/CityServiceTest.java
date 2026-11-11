package com.example.EmployeeDirectory.services;

import com.example.EmployeeDirectory.entity.City;
import com.example.EmployeeDirectory.mappers.CityMapper;
import com.example.EmployeeDirectory.repositories.CityRepository;
import com.example.EmployeeDirectory.dto.create.CityCreateDto;
import com.example.EmployeeDirectory.services.impl.CityServiceImpl;
import com.example.EmployeeDirectory.web.exception.ConflictException;
import com.example.EmployeeDirectory.web.exception.ModelNotFoundException;
import com.example.EmployeeDirectory.web.responses.CityResponseDto;
import com.example.EmployeeDirectory.web.views.CityView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository repository;

    @Mock
    private CityMapper mapper;

    @InjectMocks
    private CityServiceImpl cityService;


    @Test
    void create_shouldThrowConflict_whenCityAlreadyExists() {
        CityCreateDto dto = new CityCreateDto("Москва");
        given(repository.existsByName("Москва")).willReturn(true);
        assertThatThrownBy(() -> cityService.create(dto))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Город с указанным названием уже существует");


        then(repository).should(never()).save(any());
        then(mapper).shouldHaveNoInteractions();
    }

    @Test
    void create_shouldSaveAndReturnDto_whenCityDoesNotExist() {
        CityCreateDto dto = new CityCreateDto("Казань");
        CityResponseDto expected = new CityResponseDto(1L, "Казань");

        given(repository.existsByName("Казань")).willReturn(false);
        given(mapper.toResponse(any(City.class))).willReturn(expected);

        CityResponseDto result = cityService.create(dto);

        assertThat(result).isEqualTo(expected);

        ArgumentCaptor<City> cityCaptor = ArgumentCaptor.forClass(City.class);
        then(repository).should().save(cityCaptor.capture());
        assertThat(cityCaptor.getValue().getName()).isEqualTo("Казань");
    }


    @Test
    void delete_shouldThrowNotFound_whenCityMissing() {
        given(repository.findByName("Омск")).willReturn(Optional.empty());

        assertThatThrownBy(() -> cityService.delete("Омск"))
                .isInstanceOf(ModelNotFoundException.class)
                .hasMessage("Город не найден");

        then(repository).should(never()).delete(any());
    }


    @Test
    void delete_shouldDeleteAndReturnTrue_whenCityExists() {
        City city = new City("Омск");
        given(repository.findByName("Омск")).willReturn(Optional.of(city));

        boolean result = cityService.delete("Омск");

        assertThat(result).isTrue();
        then(repository).should().delete(city);
    }


    @Test
    void getAll_shouldMapAllCities() {
        List<City> cities = List.of(new City("A"), new City("B"));
        List<CityView> views = List.of(new CityView("A"), new CityView("B"));

        given(repository.findAll()).willReturn(cities);
        given(mapper.toViews(cities)).willReturn(views);

        List<CityView> result = cityService.getAll();

        assertThat(result).isEqualTo(views);
    }
}