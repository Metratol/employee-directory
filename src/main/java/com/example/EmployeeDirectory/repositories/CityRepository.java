package com.example.EmployeeDirectory.repositories;

import com.example.EmployeeDirectory.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {

    boolean existsByName(String name);
    Optional<City> findByName(String name);
    boolean deleteByName(String name);
}
