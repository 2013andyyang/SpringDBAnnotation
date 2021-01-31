package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class TestController {
	@RequestMapping(value = { "/user" }, method = RequestMethod.GET)
	public String listEmp(ModelMap model) {
		model.addAttribute("emp", "Annie Wang");
		return "User";
	}
}