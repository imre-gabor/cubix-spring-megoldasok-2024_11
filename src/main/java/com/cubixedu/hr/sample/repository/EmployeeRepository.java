package com.cubixedu.hr.sample.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cubixedu.hr.sample.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findBySalaryGreaterThan(Integer minSalary);
	
	List<Employee> findByJobTitle(String title);
	
	List<Employee> findByNameStartingWithIgnoreCase(String name);
	
	List<Employee> findByNameIgnoreCaseStartingWith(String name);
	
	List<Employee> findByDateOfStartWorkBetween(LocalDateTime start, LocalDateTime end);

}
