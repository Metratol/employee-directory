package com.example.EmployeeDirectory.repositories;

import com.example.EmployeeDirectory.entity.Employee;
import com.example.EmployeeDirectory.web.views.EmployeeView;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT new com.example.EmployeeDirectory.web.views.EmployeeView(emp.firstName, emp.secondName, emp.phoneNumber, dep.name, city.name) " +
            "FROM Employee emp " +
            "JOIN emp.department dep " +
            "JOIN dep.city city " +
            "ORDER BY emp.lastName ASC, emp.firstName ASC"

    )
    Page<EmployeeView> findAllDto(Pageable pageable);

    @Query("SELECT new com.example.EmployeeDirectory.web.views.EmployeeView(emp.firstName, emp.secondName, emp.phoneNumber, dep.name, city.name) " +
            "FROM Employee emp " +
            "JOIN emp.department dep " +
            "JOIN dep.city city " +
            "WHERE city.name = :cityName "+
            "ORDER BY emp.lastName ASC, emp.firstName ASC"

    )
    Page<EmployeeView> findAllDtoByCityName(Pageable pageable, @Param("cityName") String cityName);

    @Query("SELECT new com.example.EmployeeDirectory.web.views.EmployeeView(emp.firstName, emp.secondName, emp.phoneNumber, dep.name, city.name) " +
            "FROM Employee emp " +
            "JOIN emp.department dep " +
            "JOIN dep.city city " +
            "WHERE city.name = :cityName " +
            "AND dep.name = :departmentName " +
            "ORDER BY emp.lastName ASC, emp.firstName ASC"
    )
    Page<EmployeeView> findAllDtoByCityAndDepartmentNames(Pageable pageable, @Param("cityName") String cityName, @Param("departmentName") String departmentName);

    @Query("SELECT new com.example.EmployeeDirectory.web.views.EmployeeView(emp.firstName, emp.secondName, emp.phoneNumber, dep.name, city.name) " +
            "FROM Employee emp " +
            "JOIN emp.department dep " +
            "JOIN dep.city city " +
            "WHERE dep.name = :departmentName " +
            "ORDER BY emp.lastName ASC, emp.firstName ASC"
    )
    Page<EmployeeView> findAllDtoByDepartmentName(Pageable pageable, @Param("departmentName") String departmentName);


    @Query("SELECT new com.example.EmployeeDirectory.web.views.EmployeeView(emp.firstName, emp.secondName, emp.phoneNumber, dep.name, city.name) " +
            "FROM Employee emp " +
            "JOIN emp.department dep " +
            "JOIN dep.city city " +
            "WHERE emp.firstName = :firstName " +
            "AND emp.lastName = :lastName " +
            "ORDER BY emp.lastName ASC, emp.firstName ASC"
    )
    Page<EmployeeView> findAllDtoByFullName(Pageable pageable, @Param("firstName") String firstName, @Param("lastName") String lastName);
    @Query("SELECT new com.example.EmployeeDirectory.web.views.EmployeeView(emp.firstName, emp.secondName, emp.phoneNumber, dep.name, city.name) " +
            "FROM Employee emp " +
            "JOIN emp.department dep " +
            "JOIN dep.city city " +
            "WHERE emp.docsNo = :docsNo "
    )
    Optional<EmployeeView> findDtoByDocsNo(@Param("docsNo") String docsNo);

    @Query("SELECT new com.example.EmployeeDirectory.web.views.EmployeeView(emp.firstName, emp.secondName, emp.phoneNumber, dep.name, city.name) " +
            "FROM Employee emp " +
            "JOIN emp.department dep " +
            "JOIN dep.city city " +
            "WHERE emp.phoneNumber = :phoneNumber " +
            "ORDER BY emp.lastName ASC, emp.firstName ASC"
    )
    List<EmployeeView> findDtoByPhoneNumber(@Param("phoneNumber") String phoneNumber);


}
