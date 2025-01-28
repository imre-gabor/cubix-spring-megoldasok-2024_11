package com.cubixedu.hr.sample.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubixedu.hr.sample.model.Company;
import com.cubixedu.hr.sample.model.Employee;
import com.cubixedu.hr.sample.repository.CompanyRepository;

import jakarta.transaction.Transactional;

@Service
public class CompanyService {
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	EmployeeService employeeService;

	public Company save(Company company) {		
		return companyRepository.save(company);
	}

	public Company update(Company company) {
		if(!companyRepository.existsById(company.getId()))
			return null;
		return companyRepository.save(company);
	}

	public List<Company> findAll(boolean full) {
		if(full) {
			return companyRepository.findAllWithEmployees();
		} else {
			return companyRepository.findAll();
		}
	}

	public Optional<Company> findById(long id) {
		return companyRepository.findById(id);
	}

	public void delete(long id) {
		companyRepository.deleteById(id);
	}
	
	@Transactional
	public Company addEmployee(long id, Employee employee) {
		Company company = companyRepository.findByIdWithEmployees(id).get();
		Employee savedEmployee = employeeService.save(employee);
		company.addEmployee(savedEmployee);
		return company;
	}
	
	@Transactional
	public Company deleteEmployee(long id, long employeeId) {
		Company company = companyRepository.findByIdWithEmployees(id).get();
		Employee employee = employeeService.findById(employeeId).get();
		employee.setCompany(null);
		company.getEmployees().remove(employee);
		//employeeService.save(employee); --> felesleges, ha @Transactional a metódus
		return company;
	}
	
	@Transactional
	public Company replaceEmployees(long id, List<Employee> employees) {
		Company company = companyRepository.findByIdWithEmployees(id).get();
		company.getEmployees().forEach(emp -> emp.setCompany(null));
		company.getEmployees().clear();
		
		employees.forEach(emp -> {
			company.addEmployee(employeeService.save(emp));			
		});
		return company;
	}
	
}
