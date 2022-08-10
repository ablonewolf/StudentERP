package com.arka99.FinalStudentERP.repository;

import com.arka99.FinalStudentERP.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer,Long> {
    Trainer findTrainerByUsername(String username);
}
