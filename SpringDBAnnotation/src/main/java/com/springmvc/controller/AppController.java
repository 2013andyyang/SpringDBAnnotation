package com.springmvc.controller;

import java.util.*;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.springmvc.model.Employee;
import com.springmvc.service.EmployeeService;

@Controller
@RequestMapping("/")
public class AppController {
	@Autowired
	EmployeeService service;
	@Autowired
	MessageSource messageSource;

	//This method will list all existing employees.
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listEmployees(ModelMap model) {
		List<Employee> emp = service.findAllEmployees();
		model.addAttribute("employees", emp);
		return "AllEmployees";
	}

	//This method will provide the medium to add a new employee.
	@RequestMapping(value = {"/new" }, method = RequestMethod.GET)
	public String newEmployee(ModelMap model) {
		Employee emp = new Employee();
		model.addAttribute("employee", emp);
		model.addAttribute("edit", false);
		return "Registration";
	}

	/* This method will be called on form submission, handling POST request for
	for saving employee in database. It also validates the user input */
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String saveEmployee(@Valid Employee emp, BindingResult res, ModelMap model) {
		if (res.hasErrors()) {
			return "Registration";
		}
		/* Preferred way to achieve uniqueness of field [ssn] should be implementing custom
		 * @Unique annotation and applying it on field [ssn] of Model class [Employee]. Below
		 * mentioned peace of code [if block] is to demonstrate that you can fill custom errors
		 * outside the validation framework as well while still using internationalized messages.*/
		if (!service.isEmployeeSsnUnique(emp.getId(), emp.getSsn())) {
			FieldError ssnError = new FieldError("employee", "ssn", messageSource.getMessage("non.unique.ssn",
					new String[] { emp.getSsn() }, Locale.getDefault()));
			res.addError(ssnError);
			return "Registration";
		}
		service.saveEmployee(emp);
		model.addAttribute("success", "Employee " + emp.getName() + " registered successfully");
		return "Success";
	}
 
	//This method will provide the medium to update an existing employee.
	@RequestMapping(value = { "/edit-{ssn}-employee" }, method = RequestMethod.GET)
	public String editEmployee(@PathVariable String ssn, ModelMap model) {
		Employee emp = service.findEmployeeBySsn(ssn);
		model.addAttribute("employee", emp);
		model.addAttribute("edit", true);
		return "Registration";
	}

	/* This method will be called on form submission, handling POST request for
	 * updating employee in database. It also validates the user input */ 
	@RequestMapping(value = { "/edit-{ssn}-employee" }, method = RequestMethod.POST)
	public String updateEmployee(@Valid Employee emp, BindingResult res, ModelMap model, @PathVariable String ssn) {
		if (res.hasErrors()) {
			return "Registration";
		}
		if (!service.isEmployeeSsnUnique(emp.getId(), emp.getSsn())) {
			FieldError ssnError = new FieldError("employee", "ssn", messageSource.getMessage("non.unique.ssn",
					new String[] { emp.getSsn() }, Locale.getDefault()));
			res.addError(ssnError);
			return "Registration";
		}
		service.updateEmployee(emp);
		model.addAttribute("success", "Employee " + emp.getName() + " updated successfully");
		return "Success";
	}

	//This method will delete an employee by it's SSN value.
	@RequestMapping(value = { "/delete-{ssn}-employee" }, method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable String ssn) {
		service.deleteEmployeeBySsn(ssn);
		return "redirect:/list";
	}
}