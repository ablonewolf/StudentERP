package com.arka99.FinalStudentERP.repository;

import com.arka99.FinalStudentERP.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByUsername(String username);
    Employee findByName(String name);
    Employee findByEmail(String email);
    Optional<Employee> findEmployeeByUsername(String username);
}
