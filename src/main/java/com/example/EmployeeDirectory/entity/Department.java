package com.example.EmployeeDirectory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "city_id"})})
public class Department extends Base {

    @Column(name = "name", nullable = false, length = 70)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private City city;

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    private List<Employee> employees = new ArrayList<>();

    public Department(String name, City city) {
        this.name = name;
        this.city = city;
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
