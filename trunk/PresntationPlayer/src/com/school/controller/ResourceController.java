package com.school.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.service.ResourceService;
import com.school.util.ConfigurationLoader;

@Controller
@RequestMapping(value = "/resources")
public class ResourceController {
	@javax.annotation.Resource(name = "resourceService")
	ResourceService resService;

	@RequestMapping(method = RequestMethod.GET, value = "/js")
	public @ResponseBody
	Resource getJsFile(@RequestParam String name) {
		System.out.println(name);
		return resService.getResource(RESOURCE_JS_FOLDER + "/" + name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/css")
	public @ResponseBody
	Resource getJsCss(HttpServletRequest request, @RequestParam String name) {
		return resService.getResource(RESOURCE_CSS_FOLDER + "/" + name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/img")
	public @ResponseBody
	Resource getImgResource(@RequestParam String name) {
		return resService.getResource(name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "*/images/{imgName}.{imgExtension}")
	public ResponseEntity<Resource> getCssImageResource(@PathVariable("imgName") String imgName,
			@PathVariable("imgExtension") String imgExtension) {
		System.out.println(imgExtension + imgName);
		Resource res = resService.getResource(RESOURCE_IMAGE_FOLDER + "/" + imgName + "." + imgExtension);
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType;
		try {
			MagicMatch match = Magic.getMagicMatch(res.getFile(), true);
			String[] types = match.getMimeType().split("/");
			mediaType = new MediaType(types[0], types[1]);
		} catch (MagicParseException | MagicMatchNotFoundException | MagicException | IOException e) {
			mediaType = MediaType.APPLICATION_JSON;
		}
		headers.setContentType(mediaType);

		return new ResponseEntity<>(res, headers, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/repo/{imgName}.{imgExtension}")
	public ResponseEntity<Resource> getSvgFromRepo(@PathVariable("imgName") String imgName,
			@PathVariable("imgExtension") String imgExtension) {
		System.out.println(imgExtension + imgName);
		Resource res = resService.getResource("file:"+RESOURCE_REPO_FOLDER + "/" + imgName + "." + imgExtension);
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("image", "svg+xml");
		headers.setContentType(mediaType);
		return new ResponseEntity<>(res, headers, HttpStatus.OK);
	}

	private String RESOURCE_CSS_FOLDER = ConfigurationLoader.getConfig().getString("resources.default.theme.folder"),
			RESOURCE_JS_FOLDER = ConfigurationLoader.getConfig().getString("resources.default.js.folder"),
			RESOURCE_IMAGE_FOLDER = ConfigurationLoader.getConfig().getString("resources.default.images.folder"),
			RESOURCE_REPO_FOLDER = ConfigurationLoader.getConfig().getString("local.repository.upload.path");
}
