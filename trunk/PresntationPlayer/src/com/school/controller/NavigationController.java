package com.school.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope(value = "request")
public class NavigationController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/upload")
	public String getPresentationPage() {
		return "upload/uploadPage";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String getLoginPage() {
		return "login/loginPage";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String getIndexPage() {
		return "index/indexPage";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/register")
	public String getRegistrationPage() {
		return "register/registerPage";
	}
}
