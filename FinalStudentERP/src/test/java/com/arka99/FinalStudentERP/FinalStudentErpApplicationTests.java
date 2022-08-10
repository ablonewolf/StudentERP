package com.arka99.FinalStudentERP;
import com.arka99.FinalStudentERP.model.Employee;
import com.arka99.FinalStudentERP.model.Role;
import com.arka99.FinalStudentERP.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = FinalStudentErpApplication.class)
class FinalStudentErpApplicationTests {
	@MockBean
	private EmployeeRepository employeeRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void saveEmployeeTest() {
		Employee employee = new Employee();
		employee.setName("Arghya Bhuiyan");
		employee.setUsername("arghya.bhuiyan");
		employee.setEmail("arghya.bhuiyan@bjitgroup.com");
		employee.setId(115100L);
		employee.setPassword(passwordEncoder.encode("1234"));
		Collection<Role> roles = new ArrayList<>();
		employee.setRoles(roles);
		employeeRepository.save(employee);
		verify(employeeRepository,times(1)).save(employee);
	}

	@Test
	void getEmployeeTestByUsername() {
		Employee employee = new Employee(115100L,"Arghya Bhuiyan","arghya.bhuiyan@bjitgroup.com","arghya.bhuiyan");
		when(employeeRepository.findByUsername("arghya.bhuiyan")).thenReturn(employee);
		assertEquals(null,employeeRepository.findByUsername("arghya.bhuiyan"));
	}

	@Test
	void getEmployeeTestByName () {
		Employee employee = new Employee(115100L,"Samir Bhuiyan","samir.bhuiyan@bjitgroup.com","samir.bhuiyan");
		when(employeeRepository.findByName("Samir Bhuiyan")).thenReturn(employee);
		assertEquals(employee,employeeRepository.findByName("Samir Bhuiyan"));
	}

}
