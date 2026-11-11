package com.example.EmployeeDirectory.repository;

import com.example.EmployeeDirectory.entity.City;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.repositories.EmployeeRepository;
import com.example.EmployeeDirectory.web.views.EmployeeView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findAllDtoByCityAndDepartmentNames_shouldReturnCorrectProjection() {
        City moscow = entityManager.persist(new City("Москва"));
        City perm = entityManager.persist(new City("Пермь"));
        Department itMoscow = entityManager.persist(new Department("IT", moscow));
        Department hrPerm = entityManager.persist(new Department("HR", perm));

        entityManager.persist(new Employee("Иван", "Иванов", "+7999", "111", itMoscow));
        entityManager.persist(new Employee("Петр", "Петров", "+7998", "222", hrPerm));
        entityManager.flush();

        Page<EmployeeView> page = employeeRepository.findAllDtoByCityAndDepartmentNames(
                PageRequest.of(0, 10), "Москва", "IT");

        assertThat(page.getContent()).hasSize(1);
        EmployeeView view = page.getContent().get(0);
        assertThat(view.firstName()).isEqualTo("Иван");
        assertThat(view.departmentName()).isEqualTo("IT");
        assertThat(view.cityName()).isEqualTo("Москва");
    }
    @Test
    void findAllDtoByCityAndDepartmentNames_shouldReturnEmpty_whenNoMatch() {
        City moscow = entityManager.persist(new City("Москва"));
        Department itMoscow = entityManager.persist(new Department("IT", moscow));
        entityManager.persist(new Employee("Иван", "Иванов", "+7999", "111", itMoscow));
        entityManager.flush();

        Page<EmployeeView> page = employeeRepository.findAllDtoByCityAndDepartmentNames(
                PageRequest.of(0, 10), "Казань", "IT");

        assertThat(page.getContent()).isEmpty();
    }

    @Test
    void findDtoByDocsNo_shouldReturnView_whenExists() {
        City moscow = entityManager.persist(new City("Москва"));
        Department it = entityManager.persist(new Department("IT", moscow));
        entityManager.persist(new Employee("Иван", "Иванов", "+7999", "111", it));
        entityManager.flush();

        Optional<EmployeeView> result = employeeRepository.findDtoByDocsNo("111");

        assertThat(result).isPresent();
        assertThat(result.get().firstName()).isEqualTo("Иван");
    }
}