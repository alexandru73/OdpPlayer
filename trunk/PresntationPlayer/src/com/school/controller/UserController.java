package com.school.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.school.util.ConfigurationLoader;

@Controller
@RequestMapping(value="/uploada")
@Scope("")
public class UserController {
	// @Resource(name = "jobSenderImpl")
	// JobSenderImpl queue;

	@RequestMapping(method = RequestMethod.GET, value = "/int", headers = "Accept=application/json")
	public @ResponseBody
	String getAllEmp() {
		System.out.println("zuuz" + ConfigurationLoader.getConfig().getString("local.repository.upload.path"));
		for (long i = 2; i < 10; i++) {
			// queue.send(new Job(i));
		}

		return "god";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/alex")
	public String getAllEmp2() {
		return "uploadPresentation";
	}

	
	
	@RequestMapping(value="/upload2", method = RequestMethod.POST)
	public @ResponseBody
	String upload(@RequestParam("fileData") CommonsMultipartFile f) {
	if (f == null) {
		return "fail";
	}
		try {
			f.transferTo(new File(ConfigurationLoader.getConfig().getString("local.repository.upload.path")+"/asd"));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return "success";
	}

}
