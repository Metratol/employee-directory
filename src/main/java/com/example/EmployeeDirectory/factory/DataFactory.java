package com.example.EmployeeDirectory.factory;

import com.example.EmployeeDirectory.entity.City;
import com.example.EmployeeDirectory.entity.Department;
import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.repositories.CityRepository;
import com.example.EmployeeDirectory.repositories.DepartmentRepository;
import com.example.EmployeeDirectory.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataFactory implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public void run(String... args) {

        // Если данные уже есть, ничего не создаём
        if (cityRepository.count() > 0
                || departmentRepository.count() > 0
                || employeeRepository.count() > 0) {
            return;
        }

        // Создаём города
        City moscow = cityRepository.save(new City("Москва"));
        City spb = cityRepository.save(new City("Санкт-Петербург"));
        City kazan = cityRepository.save(new City("Казань"));

        // Создаём отделы
        Department itMoscow =
                departmentRepository.save(new Department("IT Москва", moscow));

        Department hrMoscow =
                departmentRepository.save(new Department("HR Москва", moscow));

        Department itSpb =
                departmentRepository.save(new Department("IT Санкт-Петербург", spb));

        Department salesSpb =
                departmentRepository.save(new Department("Продажи Санкт-Петербург", spb));

        Department itKazan =
                departmentRepository.save(new Department("IT Казань", kazan));

        // Создаём сотрудников
        employeeRepository.saveAll(List.of(
                new Employee("Иван", "Иванов", "+79990000001", "1000000001", itMoscow),
                new Employee("Пётр", "Петров", "+79990000002", "1000000002", itMoscow),
                new Employee("Анна", "Смирнова", "+79990000003", "1000000003", hrMoscow),

                new Employee("Сергей", "Сидоров", "+79990000004", "1000000004", itSpb),
                new Employee("Мария", "Кузнецова", "+79990000005", "1000000005", salesSpb),

                new Employee("Алексей", "Попов", "+79990000006", "1000000006", itKazan)
        ));

        System.out.println("Тестовые данные успешно созданы!");
    }
}