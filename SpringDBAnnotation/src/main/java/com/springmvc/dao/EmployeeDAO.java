package com.springmvc.dao;

import java.util.List;
import com.springmvc.model.Employee;

public interface EmployeeDAO {
	Employee findById(int id);

	void saveEmployee(Employee emp);
	void deleteEmployeeBySsn(String ssn);

	List<Employee> findAllEmployees();
	Employee findEmployeeBySsn(String ssn);
}
