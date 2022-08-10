package com.arka99.FinalStudentERP.repository;

import com.arka99.FinalStudentERP.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findAdminByUsername(String username);
}
