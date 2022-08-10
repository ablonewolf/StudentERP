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
@Table(name = "Trainer")
public class Trainer {
    @Id
    @NonNull
    @Column(name = "ID")
    private Long id;
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

        Trainer trainer = (Trainer) o;

        return username.equals(trainer.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
