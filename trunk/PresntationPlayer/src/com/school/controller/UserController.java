package com.school.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@RequestMapping(method = RequestMethod.GET, value = "/int", headers = "Accept=application/json")
	public @ResponseBody
	String getAllEmp() {
		System.out.println("mess");
		return "god";
	}

}
