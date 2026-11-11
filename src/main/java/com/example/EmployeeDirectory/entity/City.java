package com.example.EmployeeDirectory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "city")
public class City extends Base{


    @NonNull
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
