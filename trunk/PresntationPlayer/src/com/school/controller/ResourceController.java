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

	@RequestMapping(method = RequestMethod.GET, value = "/js/{jsName:.+}")
	public @ResponseBody
	Resource getJsFile(@PathVariable("jsName") String jsName) {
		return resService.getResource(RESOURCE_JS_FOLDER + "/" + jsName);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/css/{cssName:.+}")
	public @ResponseBody
	Resource getJsCss(@PathVariable("cssName") String cssName) {
		return resService.getResource(RESOURCE_CSS_FOLDER + "/" + cssName);
	}

	@RequestMapping(method = RequestMethod.GET, value = "*/images/{imgName:.+}")
	public ResponseEntity<Resource> getCssImageResource(@PathVariable("imgName") String imgName) {
		Resource res = resService.getResource(RESOURCE_IMAGE_FOLDER + "/" + imgName );
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

	@RequestMapping(method = RequestMethod.GET, value = "/repo/{imgName:.+}")
	public ResponseEntity<Resource> getSvgFromRepo(@PathVariable("imgName") String imgName) {
		Resource res = resService.getResource(RESOURCE_SOURCE_DISK + RESOURCE_REPO_FOLDER + "/" + imgName);
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType(SVG_MEDIA_TYPE, SVG_MEDIA_SUBTYPE);
		headers.setContentType(mediaType);
		return new ResponseEntity<>(res, headers, HttpStatus.OK);
	}

	private String RESOURCE_CSS_FOLDER = ConfigurationLoader.getConfig().getString("resources.default.theme.folder"),
			RESOURCE_JS_FOLDER = ConfigurationLoader.getConfig().getString("resources.default.js.folder"),
			RESOURCE_IMAGE_FOLDER = ConfigurationLoader.getConfig().getString("resources.default.images.folder"),
			RESOURCE_REPO_FOLDER = ConfigurationLoader.getConfig().getString("local.repository.upload.path"),
			SVG_MEDIA_TYPE = "image", SVG_MEDIA_SUBTYPE = "svg+xml", RESOURCE_SOURCE_DISK = "file:";
}
