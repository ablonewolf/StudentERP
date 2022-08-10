package com.arka99.FinalStudentERP.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Trainee")
public class Trainee {
    @Id
    @NonNull
    @Column(name = "ID")
    private long id;
    @NonNull
    @Column(name = "Name")
    private String name;
    @NonNull
    @Column(name = "UserName")
    private String username;
    @NonNull
    @Column(name = "Email")
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trainee trainee = (Trainee) o;

        return username.equals(trainee.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
