package com.example.EmployeeDirectory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "city")
public class City extends Base{



    @Column(name = "name", nullable = false, unique = true, length = 40)
    private String name;
    @OneToMany(mappedBy = "city", cascade = CascadeType.REMOVE)
    private List<Department> departments = new ArrayList<>();

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
