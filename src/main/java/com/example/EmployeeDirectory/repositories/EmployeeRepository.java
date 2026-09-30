package com.example.EmployeeDirectory.repositories;

import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.repositories.dto.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT new com.example.EmployeeDirectory.repositories.dto.EmployeeDto(emp.firstName, emp.secondName, emp.phoneNumber, dep.name, city.name) " +
            "FROM Employee emp " +
            "JOIN emp.department dep " +
            "JOIN dep.city city " +
            "ORDER BY emp.lastName ASC, emp.firstName ASC"

    )
    Page<EmployeeDto> findAllDto(Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT new com.example.EmployeeDirectory.repositories.dto.EmployeeDto(emp.firstName, emp.secondName, emp.phoneNumber, dep.name, city.name) " +
            "FROM Employee emp " +
            "JOIN emp.department dep " +
            "JOIN dep.city city " +
            "WHERE city.name = :cityName "+
            "ORDER BY emp.lastName ASC, emp.firstName ASC"

    )
    Page<EmployeeDto> findAllDtoByCityName(Pageable pageable, @Param("cityName") String cityName);

    @Transactional(readOnly = true)
    @Query("SELECT new com.example.EmployeeDirectory.repositories.dto.EmployeeDto(emp.firstName, emp.secondName, emp.phoneNumber, dep.name, city.name) " +
            "FROM Employee emp " +
            "JOIN emp.department dep " +
            "JOIN dep.city city " +
            "WHERE city.name = :cityName " +
            "AND dep.name = :departmentName " +
            "ORDER BY emp.lastName ASC, emp.firstName ASC"
    )
    Page<EmployeeDto> findAllDtoByCityAndDepartmentNames(Pageable pageable, @Param("cityName") String cityName, @Param("departmentName") String departmentName);

    @Transactional(readOnly = true)
    @Query("SELECT new com.example.EmployeeDirectory.repositories.dto.EmployeeDto(emp.firstName, emp.secondName, emp.phoneNumber, dep.name, city.name) " +
            "FROM Employee emp " +
            "JOIN emp.department dep " +
            "JOIN dep.city city " +
            "WHERE dep.name = :departmentName " +
            "ORDER BY emp.lastName ASC, emp.firstName ASC"
    )
    Page<EmployeeDto> findAllDtoByDepartmentName(Pageable pageable, @Param("departmentName") String departmentName);


    @Transactional(readOnly = true)
    @Query("SELECT new com.example.EmployeeDirectory.repositories.dto.EmployeeDto(emp.firstName, emp.secondName, emp.phoneNumber, dep.name, city.name) " +
            "FROM Employee emp " +
            "JOIN emp.department dep " +
            "JOIN dep.city city " +
            "WHERE emp.firstName = :firstName " +
            "AND emp.lastName = :lastName " +
            "ORDER BY emp.lastName ASC, emp.firstName ASC"
    )
    Page<EmployeeDto> findAllDtoByFullName(@Param("firstName") String firstName, @Param("secondName") String secondName);

    @Transactional(readOnly = true)
    @Query("SELECT new com.example.EmployeeDirectory.repositories.dto.EmployeeDto(emp.firstName, emp.secondName, emp.phoneNumber, dep.name, city.name) " +
            "FROM Employee emp " +
            "JOIN emp.department dep " +
            "JOIN dep.city city " +
            "WHERE emp.docsNo = :docsNo " +
            "ORDER BY emp.lastName ASC, emp.firstName ASC"
    )
    Optional<EmployeeDto> findDtoByDocsNo(@Param("docsNo") String docsNo);

    @Transactional(readOnly = true)
    @Query("SELECT new com.example.EmployeeDirectory.repositories.dto.EmployeeDto(emp.firstName, emp.secondName, emp.phoneNumber, dep.name, city.name) " +
            "FROM Employee emp " +
            "JOIN emp.department dep " +
            "JOIN dep.city city " +
            "WHERE emp.docsNo = :docsNo " +
            "ORDER BY emp.lastName ASC, emp.firstName ASC"
    )
    Optional<EmployeeDto> findDtoByPhoneNumber(@Param("phoneNumber") String phoneNumber);


}
