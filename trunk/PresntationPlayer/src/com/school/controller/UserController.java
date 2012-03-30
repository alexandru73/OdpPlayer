package com.school.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.config.ConfigurationLoader;
import com.school.upload.job.Job;
import com.school.upload.job.JobSenderImpl;

@Controller
public class UserController {
//	@Resource(name = "jobSenderImpl")
//	JobSenderImpl queue;

	@RequestMapping(method = RequestMethod.GET, value = "/int", headers = "Accept=application/json")
	public @ResponseBody
	String getAllEmp() {
		System.out.println("zuuz" + ConfigurationLoader.getConfig().getString("local.repository.upload.path"));
		for (long i = 2; i < 10; i++) {
//			queue.send(new Job(i));
		}

		return "god";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "alex")
	public String getAllEmp2() {
		return "xx";
	}

}
