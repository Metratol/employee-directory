package EmployeeDirectory.entity;

import jakarta.persistence.*;

import java.util.Objects;

@MappedSuperclass
public class Base {

    @Column(name = "id", nullable = false, updatable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    public Long getId() {
        return id;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (getClass() != org.hibernate.Hibernate.getClass(o)) return false;

        Base that = (Base) o;

        return getId() != null && Objects.equals(getId(), that.getId());
    }


    public int hashCode() {
        return getId() != null ? getId().hashCode() : super.hashCode();
    }
}
