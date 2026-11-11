package com.example.EmployeeDirectory.web.controllers;

import com.example.EmployeeDirectory.dto.create.DepartmentCreateDto;
import com.example.EmployeeDirectory.dto.update.DepartmentCityUpdateDto;
import com.example.EmployeeDirectory.dto.update.DepartmentNameUpdateDto;
import com.example.EmployeeDirectory.services.DepartmentService;
import com.example.EmployeeDirectory.web.exception.ModelNotFoundException;
import com.example.EmployeeDirectory.web.responses.DepartmentResponseDto;
import com.example.EmployeeDirectory.web.views.DepartmentView;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper objectMapper;

    @MockitoBean
    private DepartmentService departmentService;
    @Test
    void create_ShouldReturn201AndBody() throws Exception{
        DepartmentCreateDto dto = new DepartmentCreateDto("IT","Москва");
        DepartmentResponseDto response = new DepartmentResponseDto(1,"IT","Москва");
        given(departmentService.create(dto)).willReturn(response);

        mockMvc.perform(post("/department")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("IT"))
                .andExpect(jsonPath("$.city").value("Москва"));


        ArgumentCaptor<DepartmentCreateDto> captor = ArgumentCaptor.forClass(DepartmentCreateDto.class);
        then(departmentService).should().create(captor.capture());
        assertThat(captor.getValue().name()).isEqualTo("IT");
        assertThat(captor.getValue().cityName()).isEqualTo("Москва");
    }

    @Test
    void create_shouldReturn400_whenBodyInvalid() throws Exception {
        DepartmentCreateDto invalid = new DepartmentCreateDto(null,null);
        mockMvc.perform(post("/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid))
            ).andExpect(status().isBadRequest());
        then(departmentService).shouldHaveNoInteractions();
    }
    @Test
    void create_shouldReturn400_whenBodyIsMalformedJson() throws Exception {
        mockMvc.perform(post("/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{not-json"))
                .andExpect(status().isBadRequest());

        then(departmentService).shouldHaveNoInteractions();

    }

    @Test
    void updateCity_shouldReturn200AndBody() throws Exception{
        DepartmentCityUpdateDto request = new DepartmentCityUpdateDto("IT","Москва","Ларнака");
        DepartmentResponseDto response = new DepartmentResponseDto(1,"IT","Ларнака");
        given(departmentService.moveToAnotherCity(any(DepartmentCityUpdateDto.class))).willReturn(response);

        mockMvc.perform(patch("/department/updateCity")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("IT"))
                .andExpect(jsonPath("$.city").value("Ларнака"));
        ArgumentCaptor<DepartmentCityUpdateDto> captor = ArgumentCaptor.forClass(DepartmentCityUpdateDto.class);
        then(departmentService).should().moveToAnotherCity(captor.capture());
        DepartmentCityUpdateDto captured = captor.getValue();
        assertThat(captured.departmentName()).isEqualTo("IT");
        assertThat(captured.oldCityName()).isEqualTo("Москва");
        assertThat(captured.newCityName()).isEqualTo("Ларнака");
    }
    @Test
    void updateCity_shouldReturn400_whenBodyInvalid() throws Exception {
        DepartmentCityUpdateDto invalid = new DepartmentCityUpdateDto(null,null, null);
        mockMvc.perform(patch("/department/updateCity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid))
        ).andExpect(status().isBadRequest());
        then(departmentService).shouldHaveNoInteractions();
    }
    @Test
    void updateName_shouldReturn400_whenBodyInvalid() throws Exception {
        DepartmentNameUpdateDto invalid = new DepartmentNameUpdateDto(null,null, null);
        mockMvc.perform(patch("/department/updateName")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid))
        ).andExpect(status().isBadRequest());
        then(departmentService).shouldHaveNoInteractions();
    }

    @Test
    void updateName_shouldReturn200AndBody() throws Exception{
        DepartmentNameUpdateDto request = new DepartmentNameUpdateDto("IT","Вайбкодеры","Ларнака");
        DepartmentResponseDto response = new DepartmentResponseDto(1,"Вайбкодеры","Ларнака");
        given(departmentService.update(any(DepartmentNameUpdateDto.class))).willReturn(response);

        mockMvc.perform(patch("/department/updateName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Вайбкодеры"))
                .andExpect(jsonPath("$.city").value("Ларнака"));
        ArgumentCaptor<DepartmentNameUpdateDto> captor = ArgumentCaptor.forClass(DepartmentNameUpdateDto.class);
        then(departmentService).should().update(captor.capture());
        DepartmentNameUpdateDto captured = captor.getValue();
        assertThat(captured.departmentName()).isEqualTo("IT");
        assertThat(captured.newDepartmentName()).isEqualTo("Вайбкодеры");
        assertThat(captured.city()).isEqualTo("Ларнака");
    }



    @Test
    void all_shouldReturn200AndList() throws Exception{
        given(departmentService.getAll()).willReturn(List.of(
                new DepartmentView("IT","Москва"),
                new DepartmentView("HR","Краснодар")));
        mockMvc.perform(get("/department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].cityName").value("Москва"))
                .andExpect(jsonPath("$[0].name").value("IT"))
                .andExpect(jsonPath("$[1].name").value("HR"))
                .andExpect(jsonPath("$[1].cityName").value("Краснодар"));

    }


    @Test
    void all_shouldReturnEmptyArray_whenNoCities() throws Exception{
        given(departmentService.getAll()).willReturn(List.of());
        mockMvc.perform(get("/department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

    }

    @Test
    void byCity_shouldReturn200AndList() throws Exception {
        String citySearch = "Москва";
        given(departmentService.getAllDepartmentsByCity(citySearch))
                .willReturn(List.of(
                        new DepartmentView("IT","Москва"),
                        new DepartmentView("HR","Москва")));
        mockMvc.perform(get("/department/{city}","Москва"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].cityName").value(citySearch))
                .andExpect(jsonPath("$[0].name").value("IT"))
                .andExpect(jsonPath("$[1].name").value("HR"))
                .andExpect(jsonPath("$[1].cityName").value(citySearch));
    }

    @Test
    void byCity_shouldReturn404_whenCityMissing() throws Exception {
        given(departmentService.getAllDepartmentsByCity("Атлантида"))
                .willThrow(new ModelNotFoundException("Город не найден"));

        mockMvc.perform(get("/department/{city}", "Атлантида"))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_shouldReturn200AndMessage() throws Exception{
        given(departmentService.delete("IT","Москва")).willReturn(true);
        mockMvc.perform(delete("/department/{cityName}/{departmentName}", "Москва", "IT"))
                .andExpect(status().isOk())
                .andExpect(content().string("Удален отдел IT в городе Москва"));
    }
    @Test
    void delete_shouldReturn404_whenDepartmentMissing() throws Exception{
        given(departmentService.delete("Отдел мальчишек","Москва")).willThrow(new ModelNotFoundException("Отдел не найден"));
        mockMvc.perform(delete("/department/{cityName}/{departmentName}", "Москва","Отдел мальчишек"))
                .andExpect(status().isNotFound());

    }




}
