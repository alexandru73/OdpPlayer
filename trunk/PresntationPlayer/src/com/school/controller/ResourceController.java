package com.school.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.service.ResourceService;

@Controller
@RequestMapping(value = "/resources")
public class ResourceController {
	@javax.annotation.Resource(name = "resourceService")
	ResourceService resService;

	@RequestMapping(method = RequestMethod.GET, value = "/js")
	public @ResponseBody
	Resource getJsFile(@RequestParam String name) {
		System.out.println(name);
		return resService.getResource("resources/js/" + name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/css/*")
	public @ResponseBody
	Resource getJsCss(HttpRequest request,@RequestParam String name) {
		System.out.println(request.getURI());
		return resService.getResource(name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/img")
	public @ResponseBody
	Resource getImgResource(@RequestParam String name) {
		return resService.getResource(name);
	}
}
