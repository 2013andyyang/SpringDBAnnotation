package com.springmvc.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springmvc.dao.EmployeeDAO;
import com.springmvc.model.Employee;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDAO dao;
	public Employee findById(int id) {
		return dao.findById(id);
	}
	public void saveEmployee(Employee emp) {
		dao.saveEmployee(emp);
	}
	/*
	 * * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with proper
	 * values within transaction. It will be updated in db once transaction ends.
	 */
	public void updateEmployee(Employee emp) {
		Employee entity = dao.findById(emp.getId());
		if (entity != null) {
			entity.setName(emp.getName());
			entity.setJoiningDate(emp.getJoiningDate());
			entity.setSalary(emp.getSalary());
			entity.setSsn(emp.getSsn());
		}
	}
	public void deleteEmployeeBySsn(String ssn) {
		dao.deleteEmployeeBySsn(ssn);
	}
	public List<Employee> findAllEmployees() {
		return dao.findAllEmployees();
	}
	public Employee findEmployeeBySsn(String ssn) {
		return dao.findEmployeeBySsn(ssn);
	}
	public boolean isEmployeeSsnUnique(Integer id, String ssn) {
		Employee emp = findEmployeeBySsn(ssn);
		return (emp == null || ((id != null) && (emp.getId() == id)));
	}
}