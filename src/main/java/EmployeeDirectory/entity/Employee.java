package EmployeeDirectory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee extends Base {

    @Column(name = "first_name",length = 20, nullable = false)
    private String firstName;

    @Column(name = "second_name",length = 25, nullable = false)
    private String secondName;
    @Column(name = "phone_number",length = 15, unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "docs_no", length = 10, unique = true, nullable = false)
    private String docsNo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;


    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
