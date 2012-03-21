package com.school.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.converter.Converter;
import com.school.converter.ConverterContext;

@Controller
public class UserController {
	
	@Resource(name="presentationConverter")
	Converter converter;
	
	@RequestMapping(method = RequestMethod.GET, value = "/int", headers = "Accept=application/json")
	public @ResponseBody
	String getAllEmp() {
		converter.convert(new ConverterContext());
		return "god";
	}

}
