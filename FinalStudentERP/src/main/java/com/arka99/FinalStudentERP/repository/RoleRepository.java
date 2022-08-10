package com.arka99.FinalStudentERP.repository;

import com.arka99.FinalStudentERP.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
