package com.cubixedu.hr.sample.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cubixedu.hr.sample.model.Employee;

@Controller
public class EmployeeTLController {

	private List<Employee> allEmployees = new ArrayList<>();
	

	@GetMapping("/employees")
	public String listEmployees(Map<String, Object> model) {
		model.put("employees", allEmployees);
		model.put("newEmployee", new Employee());
		return "employees";
	}

	@PostMapping("/employees")
	public String /*ResponseEntity<Void>*/ addEmployee(Employee employee) {
		allEmployees.add(employee);
		return "redirect:employees";
//		return ResponseEntity.status(307)
//		.header("Location", "employees")
//		.build();
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable long id) {
		allEmployees.removeIf(emp -> emp.getEmployeeId() == id);
		return "redirect:/employees";
	}
	
	@GetMapping("/employees/{id}")
	public String editEmployee(@PathVariable long id, Map<String, Object> model) {
		Employee employee = allEmployees.stream().filter(e -> e.getEmployeeId()== id).findFirst().get();
		model.put("employee", employee);
		return "editEmployee";
	}
	
	
	@PostMapping("/updateEmployee")
	public String modifyEmployee(Employee employee) {		
		for(int i=0; i<allEmployees.size(); i++) {
			if(allEmployees.get(i).getEmployeeId() == employee.getEmployeeId()) {
				allEmployees.set(i, employee);
				break;
			}
		}
		return "redirect:employees";
	}
}
