package com.arka99.FinalStudentERP.services;

import com.arka99.FinalStudentERP.model.*;

import java.util.List;

public interface UserService {
    Employee saveEmployee(Employee employee);
    Role saveRole(Role role);
    void addRoletoEmployee(String userName, String roleName);

    void addRoletoNewEmployee(Employee employee,String roleName);

    Employee getEmployee(String userName);

    List<Employee> getEmployee();
    Employee getEmployeeByID(Long id);

    void deleteEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    Employee updateCurrentEmployee(String currentUserName, UpdateEmployeeRequest currentEmployee);
    void saveTrainee(String userName);
   void saveTrainer(String userName);
    void saveAdmin(String userName);
    List<Trainee> getTrainee();
    List<Admin> getAdmin();
    List<Trainer> getTrainer();

    void removeTrainee(String userName);
    void removeAdmin(String userName);
    void removeTrainer(String userName);

}
