package com.arka99.FinalStudentERP.controller;

import com.arka99.FinalStudentERP.exceptions.EmployeeAlreadyAddedException;
import com.arka99.FinalStudentERP.exceptions.InvalidEmployeeException;
import com.arka99.FinalStudentERP.model.*;
import com.arka99.FinalStudentERP.repository.AdminRepository;
import com.arka99.FinalStudentERP.repository.EmployeeRepository;
import com.arka99.FinalStudentERP.repository.TraineeRepository;
import com.arka99.FinalStudentERP.repository.TrainerRepository;
import com.arka99.FinalStudentERP.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController @RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/home")
public class UserController {

    @Autowired
    private  UserService userService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TraineeRepository traineeRepository;
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private AdminRepository adminRepository;
    @PostMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginwithExceptionHandler() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getTrainees() {
        return ResponseEntity.ok().body(userService.getEmployee());
    }

    @GetMapping("/employee/{username}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("username") String userName) {
        return ResponseEntity.ok().body(userService.getEmployee(userName));
    }
    @PutMapping ("/employee/{username}/update")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("username") String userName, @RequestBody UpdateEmployeeRequest currentEmployee) {
//        System.out.println(currentEmployee);

        if(currentEmployee.getEmail() == null || currentEmployee.getName() == null) {
            throw new InvalidEmployeeException("All the required values were not entered. Maybe Some were left blank");
        }
        else if(currentEmployee.getName().equals("") || currentEmployee.getName().equals("")) {
            throw new InvalidEmployeeException("Some fields are left blank.");
        }
        else {
            Employee employee = userService.updateCurrentEmployee(userName, currentEmployee);
            return ResponseEntity.ok().body(employee);
        }
    }

    @PostMapping("/employees/save")
    public ResponseEntity<Employee> saveTrainee(@RequestBody Employee employee) {
        Employee temp = userService.getEmployee(employee.getUsername());
        if(employee.getId()==null) {
            throw new HttpMessageNotReadableException("Some non-null fields are blank");
        }
        else if(employee.getName()==null || employee.getUsername()==null || employee.getEmail()==null  || employee.getPassword()==null) {
            throw new InvalidEmployeeException("Some fields are empty");
        }
        else if(employee.getName().equals("") || employee.getName().equals("")|| employee.getUsername().equals("")  || employee.getEmail().equals("") || employee.getPassword()==null || employee.getId()==null) {
            throw new InvalidEmployeeException("Some fields are empty");
        }
        else if(temp!=null) {
            throw new EmployeeAlreadyAddedException("Employee with username : " +temp.getUsername() + " exists");
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/home/employees/save").toString());
        userService.addRoletoNewEmployee(employee, "Employee");
        return ResponseEntity.created(uri).body(userService.saveEmployee(employee));
    }

    @DeleteMapping("/employees/delete")
    public ResponseEntity<?> deleteEmployee(@RequestBody Employee user) {
        String userName = user.getUsername();
        Trainee temp = traineeRepository.findTraineeByUsername(userName);
        if(temp!=null) {
            userService.removeTrainee(userName);
        }
        Trainer trainer = trainerRepository.findTrainerByUsername(userName);
        if(trainer!=null) {
            userService.removeTrainer(userName);
        }
        Admin admin = adminRepository.findAdminByUsername(userName);
        if(admin!=null) {
            userService.removeAdmin(userName);
        }
        URI uri = URI
                .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/home/employees/delete").toUriString());
        userService.deleteEmployee(user);
        return ResponseEntity.created(uri).body("User deleted");
    }

    @PutMapping("/employees/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        if(employee.getName().equals("")|| employee.getUsername().equals("") || employee.getEmail().equals("") || employee.getPassword()==null || employee.getId()==null) {
            throw new InvalidEmployeeException("Some fields are empty");
        }
        URI uri = URI
                .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/home/employees/update").toUriString());

        return ResponseEntity.created(uri).body(userService.updateEmployee(employee));
    }
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/home/role/save").toString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }
    @PutMapping("/role/addtoEmployee")
    public ResponseEntity<?>addRoleToTrainee(@RequestBody RoleToUserForm roleToUserForm) {
        String userName = roleToUserForm.getUserName();
        String roleName = roleToUserForm.getRoleName();
        userService.addRoletoEmployee(userName, roleName);
        if(roleName.equals("Admin")) {
            userService.saveAdmin(userName);
        }
        if(roleName.equals("Trainee")) {
            userService.saveTrainee(userName);
        }  if (roleName.equals("Trainer")) {
            userService.saveTrainer(userName);
        }

        return ResponseEntity.ok().build();
    }


}
@Data
class RoleToUserForm {
    private String userName;
    private String roleName;
}
