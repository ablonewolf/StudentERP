package com.arka99.FinalStudentERP;

import com.arka99.FinalStudentERP.model.*;
import com.arka99.FinalStudentERP.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class FinalStudentErpApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalStudentErpApplication.class, args);
	}


	@Bean
	CommandLineRunner runner(UserService userService) {
		return args -> {
//			Saving roles to the database
			try {
			userService.saveRole(new Role(null,"Employee")); }
			catch (Exception e) {
				System.out.println("This record is already present. Cannot add it.");
			}
			try {
				userService.saveRole(new Role(null, "Trainer"));
			}
			catch (Exception e) {
				System.out.println("This record is already present. Cannot add it.");
			}
			try {
				userService.saveRole(new Role(null, "Admin"));
			}
			catch (Exception e) {
				System.out.println("Cannot add it.");
			}
			try {
				userService.saveRole(new Role(null,"Trainee"));
			}
			catch (Exception e) {
				System.out.println("Cannot add this role. Already added.");
			}
//			Saving some data as trainees and trainers to the database
			String employeeRole = "Employee";
			String trainerRole = "Trainer";
			String adminRole = "Admin";
			String traineeRole = "Trainee";
			try{
			userService.saveEmployee(new Employee(11512L,"Arka Bhuiyan","arka.bhuiyan@bjigroup.com","arka.bhuiyan","1234",new ArrayList<>())); }
			catch (Exception e) {
				System.out.println("Already added.");
			}
			try{
			userService.saveEmployee(new Employee(11507L,"Akif Azwad","akif.azwad@bjigroup.com","akif.azwad","1234",new ArrayList<>()));}
			catch (Exception e) {
				System.out.println("Already added.");
			}
			try {
			userService.saveEmployee(new Employee(11514L,"Nipa Howladar","nipa.howladarn@bjigroup.com","nipa.howladar","1234",new ArrayList<>()));}
			catch (Exception e) {
				System.out.println("Already added.");
			}
			try {
			userService.saveEmployee(new Employee(11515L,"Farhan Zaman","farhan.zaman@bjigroup.com","farhan.zaman","1234",new ArrayList<>()));
			userService.saveEmployee(new Employee(11000L,"Nani Gopal","nani.gopal@bjigroup.com","nani.gopal","1234",new ArrayList<>()));
			userService.saveEmployee(new Employee(10000L,"Saleehin Mohammad","saleehin.mohammad@bjigroup.com","saleehin.mohammad","1234",new ArrayList<>()));
		}
			catch (Exception e) {
				System.out.println("Cannot add these records. Already added.");
			}
//			Adding roles to the users
			Employee tempEmployee;
			tempEmployee = userService.getEmployee("arka.bhuiyan");
			userService.addRoletoEmployee("arka.bhuiyan",employeeRole);
			userService.addRoletoEmployee("akif.azwad",employeeRole);
			userService.addRoletoEmployee("nipa.howladar",employeeRole);
			userService.addRoletoEmployee("farhan.zaman",employeeRole);
			userService.addRoletoEmployee("arka.bhuiyan",traineeRole);
			userService.addRoletoEmployee("akif.azwad",traineeRole);
			userService.addRoletoEmployee("nipa.howladar",traineeRole);
			userService.addRoletoEmployee("farhan.zaman",traineeRole);

			userService.addRoletoEmployee("nani.gopal",employeeRole);
			userService.addRoletoEmployee("nani.gopal",trainerRole);

			userService.addRoletoEmployee("saleehin.mohammad",employeeRole);
			userService.addRoletoEmployee("saleehin.mohammad",trainerRole);
			userService.addRoletoEmployee("saleehin.mohammad",adminRole);
			List<Employee> employees =  userService.getEmployee();
			employees.forEach(employee -> {
				System.out.println(employee.getName());
			});
			List<Trainee> trainees = userService.getTrainee();
			System.out.println("Printing all the trainees");
			trainees.forEach((trainee -> {
				System.out.println(trainee.getName());
			}));
			System.out.println("Printing all the trainers");
			List<Trainer> trainers = userService.getTrainer();
			trainers.forEach(trainer -> {
				System.out.println(trainer.getName());
			});
			List<Admin> admins = userService.getAdmin();
			System.out.println("Printing all the admins.");
			admins.forEach(admin -> {
				System.out.println(admin.getName());
			});
		};
	}

}
