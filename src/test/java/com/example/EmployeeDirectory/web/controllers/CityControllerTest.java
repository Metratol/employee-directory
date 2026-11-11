package com.example.EmployeeDirectory.web.controllers;

import com.example.EmployeeDirectory.dto.create.CityCreateDto;
import com.example.EmployeeDirectory.services.CityService;
import com.example.EmployeeDirectory.web.exception.ModelNotFoundException;
import com.example.EmployeeDirectory.web.responses.CityResponseDto;
import com.example.EmployeeDirectory.web.views.CityView;
import tools.jackson.databind.json.JsonMapper;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(CityController.class)
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper objectMapper;

    @MockitoBean
    private CityService cityService;


    @Test
    void create_shouldReturn201AndBody() throws Exception {
        CityCreateDto request = new CityCreateDto("Москва");
        CityResponseDto response = new CityResponseDto(1L, "Москва");

        given(cityService.create(any(CityCreateDto.class))).willReturn(response);

        mockMvc.perform(post("/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Москва"));


        ArgumentCaptor<CityCreateDto> captor = ArgumentCaptor.forClass(CityCreateDto.class);
        then(cityService).should().create(captor.capture());
        assertThat(captor.getValue().name()).isEqualTo("Москва");
    }

    @Test
    void create_shouldReturn400_whenBodyInvalid() throws Exception {
        CityCreateDto invalid = new CityCreateDto(null);

        mockMvc.perform(post("/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());

        then(cityService).shouldHaveNoInteractions();
    }

    @Test
    void create_shouldReturn400_whenBodyIsMalformedJson() throws Exception {
        mockMvc.perform(post("/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{not-json"))
                .andExpect(status().isBadRequest());
        then(cityService).shouldHaveNoInteractions();
    }

    // ==================== GET /cities ====================

    @Test
    void all_shouldReturn200AndList() throws Exception {
        given(cityService.getAll()).willReturn(List.of(
                new CityView("Москва"),
                new CityView("Казань")));

        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Москва"))
                .andExpect(jsonPath("$[1].name").value("Казань"));
    }

    @Test
    void all_shouldReturnEmptyArray_whenNoCities() throws Exception {
        given(cityService.getAll()).willReturn(List.of());

        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // ==================== DELETE /cities/{name} ====================

    @Test
    void delete_shouldReturn200AndMessage() throws Exception {
        given(cityService.delete("Москва")).willReturn(true);

        mockMvc.perform(delete("/cities/{name}", "Москва"))
                .andExpect(status().isOk())
                .andExpect(content().string("Город Москва успешно удален"));

        then(cityService).should().delete("Москва");
    }

    @Test
    void delete_shouldReturn404_whenCityMissing() throws Exception {
        given(cityService.delete("Атлантида"))
                .willThrow(new ModelNotFoundException("Город не найден"));

        mockMvc.perform(delete("/cities/{name}", "Атлантида"))
                .andExpect(status().isNotFound());
    }
}