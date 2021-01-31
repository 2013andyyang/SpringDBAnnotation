package com.springmvc.dao;

import java.util.List;
import javax.persistence.TypedQuery;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.springmvc.model.Employee;

@Repository("employeeDao")
public class EmployeeDAOImpl extends AbstractDAO<Integer, Employee> implements EmployeeDAO {
	public Employee findById(int id) {
		return getByKey(id);
	}

	public void saveEmployee(Employee emp) {
		persist(emp);
	}

	public void deleteEmployeeBySsn(String ssn) {
		TypedQuery qry = getSession().createSQLQuery("delete from Employee where ssn = :ssn");
		qry.setParameter("ssn", ssn);
		qry.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Employee> findAllEmployees() {
		Criteria cr = createEntityCriteria();
		return (List<Employee>) cr.list();
	}

	public Employee findEmployeeBySsn(String ssn) {
		Criteria cr = createEntityCriteria();
		cr.add(Restrictions.eq("ssn", ssn));
		return (Employee) cr.uniqueResult();
	}
}