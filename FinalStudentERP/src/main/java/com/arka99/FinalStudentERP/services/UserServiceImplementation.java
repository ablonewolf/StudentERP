package com.arka99.FinalStudentERP.services;

import com.arka99.FinalStudentERP.exceptions.EmployeeAlreadyAddedException;
import com.arka99.FinalStudentERP.exceptions.InvalidEmployeeException;
import com.arka99.FinalStudentERP.model.*;
import com.arka99.FinalStudentERP.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Employee saveEmployee(Employee employee) {
        log.info("Saving new Employee {} to the database", employee.getName());

        Employee temp = employeeRepository.findByUsername(employee.getUsername());
        if (temp != null && temp.equals(employee)) {
            System.out.println("Already added. Cannot add it.");
            throw new EmployeeAlreadyAddedException("Employee with username : " +temp.getUsername() + " exists");
        }
        else if(employee.getId()==null) {
            throw new HttpMessageNotReadableException("Some non-null fields are empty");
        }
        else if(employee.getName()==null || employee.getUsername()==null || employee.getEmail()==null || employee.getId()==null || employee.getPassword()==null) {
            throw new InvalidEmployeeException("Some fields are empty");
        }
        else if(employee.getName().equals("") || employee.getUsername().equals("")  || employee.getEmail().equals("") || employee.getPassword().equals("") || employee.getId().equals("")){
            throw new InvalidEmployeeException("Invalid employee data");
        }

        else{
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            return employeeRepository.save(employee); }
    }


    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        Role temp = roleRepository.findByName(role.getName());
        if (temp != null && temp.equals(role)) {
            System.out.println("Already added. cannot add it");
            return null;
        } else
            return roleRepository.save(role);
    }

    @Override
    public void addRoletoEmployee(String userName, String roleName) {
        log.info("Adding role name {} to the user {}", roleName, userName);
        Employee employee = employeeRepository.findByUsername(userName);
        Role role = roleRepository.findByName(roleName);
        System.out.println(role.getName());

        if (employee!= null && employee.getRoles().contains(role)) {
            System.out.println("Already added. Cannot add it.");
            return;
        } else {
            if(employee!=null)
                employee.getRoles().add(role);
        }
    }

    @Override
    public void addRoletoNewEmployee(Employee employee, String roleName) {
        Role role = roleRepository.findByName(roleName);
        System.out.println(role + "found");
        Collection<Role> roles = employee.getRoles();
        roles.add(role);
        employee.setRoles(roles);
        System.out.println(employee);
    }



    @Override
    public Employee getEmployee(String userName) {
        return employeeRepository.findByUsername(userName);
    }


    @Override
    public List<Employee> getEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeByID(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(null);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        Long id = employee.getId();
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee updateEmployee(Employee employee) {

//		return userRepo.save(user);
        String username = employee.getUsername();
       Employee tempEmployee =  employeeRepository.findByUsername(username);
       if(employee.getId()!= null) {
           tempEmployee.setId(employee.getId());
       }
       if(employee.getName()!=null) {
           tempEmployee.setName(employee.getName());
       }
       if(employee.getEmail()!=null) {
           tempEmployee.setEmail(employee.getEmail());
       }
//        System.out.println(employee.getRoles().isEmpty());
       if(!employee.getRoles().isEmpty()) {
           tempEmployee.setRoles(employee.getRoles());
       }
       if(employee.getPassword()!=null) {
           tempEmployee.setPassword(passwordEncoder.encode(employee.getPassword()));
       }
       else {
           throw new InvalidEmployeeException("Some fields are empty");
       }
       return employeeRepository.save(tempEmployee);
    }

    @Override
    public Employee updateCurrentEmployee(String currentUserName, UpdateEmployeeRequest currentEmployee) {
        Employee employee = employeeRepository.findByUsername(currentUserName);
        if(currentEmployee.getEmail() == null || currentEmployee.getName() == null) {
            throw new InvalidEmployeeException("All the required values were not entered. Maybe Some were left blank");
        }
        else if(currentEmployee.getName().equals("") || currentEmployee.getEmail().equals("")) {
            throw new InvalidEmployeeException("Some fields are left blank.");
        }
         {
        employee.setName(currentEmployee.getName()); }
         {
        employee.setEmail(currentEmployee.getEmail()); }
        return employeeRepository.save(employee);
    }



    @Override
    public void saveTrainee( String userName) {
        Trainee trainee = new Trainee();
        Employee employee = employeeRepository.findByUsername(userName);
        List<Role> roles = (List<Role>) employee.getRoles();
        roles.forEach(role -> {
            if(role.getName().equals("Trainee")) {
                System.out.println("Role found.");
                Trainee temp = traineeRepository.findTraineeByUsername(userName);
                System.out.println(temp);
                if(temp==null) {
                    trainee.setName(employee.getName());
                    trainee.setUsername(employee.getUsername());
                    trainee.setId(employee.getId());
                    trainee.setEmail(employee.getEmail());
                    System.out.println(trainee);
                    traineeRepository.save(trainee);
                }
                else {
                    System.out.println("The trainee has already been added.");
                    return;
                }
            }
        });
        System.out.println("A trainee has been added with username : " + trainee.getUsername());
    }

    @Override
    public void saveTrainer(String userName) {
        Trainer trainer = new Trainer();
        Employee employee = employeeRepository.findByUsername(userName);
        List<Role> roles = (List<Role>) employee.getRoles();
        roles.forEach(role -> {
            if(role.getName().equals("Trainer")) {
                Trainer temp = trainerRepository.findTrainerByUsername(userName);
//                System.out.println(temp);
                if(temp==null) {
                    trainer.setEmail(employee.getEmail());
                    trainer.setName(employee.getName());
                    trainer.setId(employee.getId());
                    trainer.setUsername(employee.getUsername());
                    trainerRepository.save(trainer);
                }
                else {
                    System.out.println("The trainer has already been added.");
                }
            }
        });
    }

    @Override
    public void saveAdmin(String userName) {
        Admin admin = new Admin();
       Employee employee = employeeRepository.findByUsername(userName);
       List<Role> roles = (List<Role>) employee.getRoles();
       roles.forEach(role -> {
           if(role.getName().equals("Admin")) {
               Admin temp = adminRepository.findAdminByUsername(userName);
               System.out.println(temp);
               if(temp==null) {
                   admin.setUsername(employee.getUsername());
                   admin.setEmail(employee.getEmail());
                   admin.setId(employee.getId());
                   admin.setName(employee.getName());
                   adminRepository.save(admin);
               }
               else {
                   System.out.println("The admin has already been added");
               }
           }
       });
    }

    @Override
    public List<Trainee> getTrainee() {
        return traineeRepository.findAll();
    }

    @Override
    public List<Admin> getAdmin() {
        return adminRepository.findAll();
    }

    @Override
    public List<Trainer> getTrainer() {
        return trainerRepository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username);
        if (employee == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("Could not found the user with the username " + username);
        } else {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            {

                employee.getRoles().forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role.getName()));
                });
                return new User(employee.getUsername(), employee.getPassword(), authorities);
            }

        }
    }

    @Override
    public void removeTrainee(String userName) {
        Trainee trainee = traineeRepository.findTraineeByUsername(userName);
        if(trainee!=null) {
            traineeRepository.delete(trainee);
        }
        return;
    }

    @Override
    public void removeAdmin(String userName) {
        Admin admin = adminRepository.findAdminByUsername(userName);
        if(admin!=null) {
            adminRepository.delete(admin);
        }
        return;
    }

    @Override
    public void removeTrainer(String userName) {
        Trainer trainer = trainerRepository.findTrainerByUsername(userName);
        if(trainer!=null) {
            trainerRepository.delete(trainer);
        }
        return;
    }
}
