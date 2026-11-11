package com.example.EmployeeDirectory.web.controllers;

import com.example.EmployeeDirectory.dto.create.EmployeeCreateDto;
import com.example.EmployeeDirectory.dto.update.EmployeeUpdateDto;
import com.example.EmployeeDirectory.services.EmployeeService;
import com.example.EmployeeDirectory.web.exception.ModelNotFoundException;
import com.example.EmployeeDirectory.web.responses.EmployeeResponseDto;
import com.example.EmployeeDirectory.web.views.EmployeeView;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper objectMapper;

    @MockitoBean
    private EmployeeService employeeService;


    @Test
    void getAll_shouldCallGetAllByDepartmentAndCity_whenBothParamsPresent() throws Exception {
        Page<EmployeeView> page = new PageImpl<>(List.of(new EmployeeView("Иван", "Иванов", "+79991234567", "IT", "Москва")));
        given(employeeService.getAllByDepartmentAndCity(any(Pageable.class), eq("IT"), eq("Москва")))
                .willReturn(page);

        mockMvc.perform(get("/employee")
                        .param("city", "Москва")
                        .param("department", "IT")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].firstName").value("Иван"));

        then(employeeService).should().getAllByDepartmentAndCity(any(Pageable.class), eq("IT"), eq("Москва"));
        then(employeeService).should(org.mockito.Mockito.never()).getAllByCity(any(), any());
        then(employeeService).should(org.mockito.Mockito.never()).getAllByDepartment(any(), any());
        then(employeeService).should(org.mockito.Mockito.never()).getAll(any());
    }

    @Test
    void getAll_shouldCallGetAllByCity_whenOnlyCityPresent() throws Exception {
        Page<EmployeeView> page = new PageImpl<>(List.of(new EmployeeView("Иван", "Иванов", "+79991234567", "IT", "Москва")));
        given(employeeService.getAllByCity(any(Pageable.class), eq("Москва"))).willReturn(page);

        mockMvc.perform(get("/employee")
                        .param("city", "Москва")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));

        then(employeeService).should().getAllByCity(any(Pageable.class), eq("Москва"));
        then(employeeService).should(org.mockito.Mockito.never()).getAll(any());
    }

    @Test
    void getAll_shouldCallGetAllByDepartment_whenOnlyDepartmentPresent() throws Exception {
        Page<EmployeeView> page = new PageImpl<>(List.of(new EmployeeView("Иван", "Иванов", "+79991234567", "IT", "Москва")));
        given(employeeService.getAllByDepartment(any(Pageable.class), eq("IT"))).willReturn(page);

        mockMvc.perform(get("/employee")
                        .param("department", "IT")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());

        then(employeeService).should().getAllByDepartment(any(Pageable.class), eq("IT"));
        then(employeeService).should(org.mockito.Mockito.never()).getAll(any());
    }

    @Test
    void getAll_shouldCallGetAll_whenNoParamsPresent() throws Exception {
        Page<EmployeeView> page = new PageImpl<>(List.of(new EmployeeView("Иван", "Иванов", "+79991234567", "IT", "Москва")));
        given(employeeService.getAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/employee")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());

        then(employeeService).should().getAll(any(Pageable.class));
    }


    @Test
    void byPhoneNumber_shouldReturn200AndBody() throws Exception {
        String phone = "+79859999999";
        EmployeeView view = new EmployeeView("Иван", "Иванов", phone, "IT", "Москва");
        given(employeeService.getByPhoneNumber(phone)).willReturn(view);

        mockMvc.perform(get("/employee/phoneNumber")
                        .param("phoneNumber", phone))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value(phone))
                .andExpect(jsonPath("$.firstName").value("Иван"));

        then(employeeService).should().getByPhoneNumber(phone);
    }

    @Test
    void byPhoneNumber_shouldReturn400_whenPhoneFormatInvalid() throws Exception {
        mockMvc.perform(get("/employee/phoneNumber")
                        .param("phoneNumber", "+79"))
                .andExpect(status().isBadRequest());

        then(employeeService).shouldHaveNoInteractions();
    }

    @Test
    void byPhoneNumber_shouldReturn404_whenEmployeeMissing() throws Exception {
        given(employeeService.getByPhoneNumber("+79991234567"))
                .willThrow(new ModelNotFoundException("Не найден"));

        mockMvc.perform(get("/employee/phoneNumber")
                        .param("phoneNumber", "+79991234567"))
                .andExpect(status().isNotFound());
    }


    @Test
    void byDocsNumber_shouldReturn200AndBody() throws Exception {
        String docs = "1022029000";
        EmployeeView view = new EmployeeView("Иван", "Иванов", "+79991234567", "IT", "Москва");
        given(employeeService.getByDocsNo(docs)).willReturn(view);

        mockMvc.perform(get("/employee/docs")
                        .param("docsNumber", docs))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Иван"));

        then(employeeService).should().getByDocsNo(docs);
    }

    @Test
    void byDocsNumber_shouldReturn404_whenEmployeeMissing() throws Exception {
        given(employeeService.getByDocsNo("1022029000"))
                .willThrow(new ModelNotFoundException("Не найден"));

        mockMvc.perform(get("/employee/docs")
                        .param("docsNumber", "1022029000"))
                .andExpect(status().isNotFound());
    }


    @Test
    void byFullName_shouldReturn200AndBody() throws Exception {
        Page<EmployeeView> page = new PageImpl<>(List.of(new EmployeeView("Иван", "Иванов", "+79991234567", "IT", "Москва")));
        given(employeeService.getByFullName(eq("Иван"), eq("Иванов"), any(Pageable.class)))
                .willReturn(page);

        mockMvc.perform(get("/employee/fullname")
                        .param("name", "Иван")
                        .param("secondName", "Иванов")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].firstName").value("Иван"));
    }


    @Test
    void create_shouldReturn201AndBody() throws Exception {
        EmployeeCreateDto request = new EmployeeCreateDto("Иван", "Иванов", "1022029000","+79991234567", "IT", "Москва");
        EmployeeResponseDto response = new EmployeeResponseDto("Иван", "Иванов", "+79991234567",  "IT", "Москва");

        given(employeeService.create(any(EmployeeCreateDto.class))).willReturn(response);

        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Иван"));

        ArgumentCaptor<EmployeeCreateDto> captor = ArgumentCaptor.forClass(EmployeeCreateDto.class);
        then(employeeService).should().create(captor.capture());
        EmployeeCreateDto expected = new EmployeeCreateDto("Иван", "Иванов",  "1022029000", "+79991234567", "IT", "Москва");
        assertThat(captor.getValue()).isEqualTo(expected);;
    }

    @Test
    void create_shouldReturn400_whenBodyInvalid() throws Exception {
        EmployeeCreateDto invalid = new EmployeeCreateDto(null, null, null, null, null, null);

        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());

        then(employeeService).shouldHaveNoInteractions();
    }


    @Test
    void update_shouldReturn200AndBody() throws Exception {
        EmployeeUpdateDto request = new EmployeeUpdateDto("1022029000", "Иван", "Иванов", "+79991234567", "IT", "Москва");
        EmployeeResponseDto response = new EmployeeResponseDto( "Иван", "Иванов","+79991234567","IT","Москва");

        given(employeeService.update(any(EmployeeUpdateDto.class))).willReturn(response);

        mockMvc.perform(patch("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Иван"));

        ArgumentCaptor<EmployeeUpdateDto> captor = ArgumentCaptor.forClass(EmployeeUpdateDto.class);
        then(employeeService).should().update(captor.capture());
        assertThat(captor.getValue().docsNo()).isEqualTo("1022029000");
    }


    @Test
    void delete_shouldReturn200AndMessage() throws Exception {
        given(employeeService.delete("1022029000")).willReturn(true);

        mockMvc.perform(delete("/employee/{docsNo}", "1022029000"))
                .andExpect(status().isOk())
                .andExpect(content().string("Удален пользователь"));

        then(employeeService).should().delete("1022029000");
    }

    @Test
    void delete_shouldReturn404_whenEmployeeMissing() throws Exception {
        given(employeeService.delete("1022029000"))
                .willThrow(new ModelNotFoundException("Сотрудник не найден"));

        mockMvc.perform(delete("/employee/{docsNo}", "1022029000"))
                .andExpect(status().isNotFound());
    }
}