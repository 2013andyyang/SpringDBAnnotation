package com.springmvc.service;

import java.util.List;
import com.springmvc.model.Employee;

public interface EmployeeService {
	Employee findById(int id);
	void saveEmployee(Employee emp);
	void updateEmployee(Employee emp);
	void deleteEmployeeBySsn(String ssn);
	List<Employee> findAllEmployees();
	Employee findEmployeeBySsn(String ssn);
	boolean isEmployeeSsnUnique(Integer id, String ssn);
}