package com.arka99.FinalStudentERP.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Employee")
public class Employee {

    @NonNull
    @Id
    @Column(name = "ID")
    private Long id;
    @NonNull
    @Column(name = "Name")
    private String name;
    @NonNull
    @Column(name = "email")
    private String email;
    @NonNull
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return username.equals(employee.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
