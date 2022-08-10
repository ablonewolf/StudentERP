package com.arka99.FinalStudentERP.repository;

import com.arka99.FinalStudentERP.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraineeRepository extends JpaRepository<Trainee,Long> {
    Trainee findTraineeByUsername(String username);
}
