package com.school.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.config.ConfigurationLoader;
import com.school.upload.queue.QueueSender;

@Controller
public class UserController {
	@Resource(name = "configLoader")
	ConfigurationLoader resources;
	@Resource(name="queueSender")
	QueueSender queue;

	@RequestMapping(method = RequestMethod.GET, value = "/int", headers = "Accept=application/json")
	public @ResponseBody
	String getAllEmp() {
		System.out.println("zuuz" + resources.getConfig().getString("local.repository.upload.path"));
		for (long i = 200; i < 250; i++) {
			queue.send(i);
		}
		
		return "god";
	}

}
