package com.example.EmployeeDirectory.repositories;

import com.example.EmployeeDirectory.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByCityName(String cityName);
    boolean existsByName(String name);
    Optional<Department> findByNameAndCity_Name(String name, String cityName);
    boolean deleteByNameAndCity_Name(String name, String cityName);
}
