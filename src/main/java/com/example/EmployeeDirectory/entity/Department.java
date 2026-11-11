package com.example.EmployeeDirectory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "city_id"})})
public class Department extends Base {

    @NonNull
    @Column(name = "name", nullable = false, length = 70)
    private String name;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private City city;

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    private List<Employee> employees = new ArrayList<>();

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
