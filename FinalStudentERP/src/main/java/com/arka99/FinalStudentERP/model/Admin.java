package com.arka99.FinalStudentERP.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Admin {
    @Id
    @NonNull
    @Column
    private Long id;
    private String name;
    private String username;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Admin admin = (Admin) o;

        return username.equals(admin.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
