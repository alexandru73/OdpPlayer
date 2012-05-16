package com.school.controller;

import java.io.IOException;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.service.ResourceService;
import com.school.util.ConfigurationLoader;

@Controller
@RequestMapping(value = "/resources")
public class ResourceController {
	@javax.annotation.Resource(name = "resourceService")
	ResourceService resService;

	@RequestMapping(method = RequestMethod.GET, value = "/js/{folder}/{jsName:.+}")
	public @ResponseBody
	Resource getJsFileInSubfolder(@PathVariable("jsName") String jsName, @PathVariable("folder") String folder) {
		return resService.getResource(RESOURCE_JS_FOLDER + "/" + folder + "/" + jsName);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/js/{jsName:.+}")
	public @ResponseBody
	Resource getJsFileNotInSubfolder(@PathVariable("jsName") String jsName) {
		return resService.getResource(RESOURCE_JS_FOLDER + "/" + jsName);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/css/local/{cssName:.+}")
	@ResponseBody
	public Resource getCssNotInSubfolder(@PathVariable("cssName") String cssName) {
		return resService.getResource(RESOURCE_CSS_LOCAL_FOLDER + "/" + cssName);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/css/local/img/{imgName:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getLocalCssImgSubfolder(@PathVariable("imgName") String imgName) {
		Resource res = resService.getResource(RESOURCE_CSS_LOCAL_IMG_FOLDER + "/" + imgName);
		return setImageResponseMediaType(res);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/css/*/{cssName:.+}")
	public @ResponseBody
	Resource getJsCssInSubfolder(@PathVariable("cssName") String cssName) {
		return resService.getResource(RESOURCE_CSS_THEME_FOLDER + "/" + cssName);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/css/*/images/{imgName:.+}")
	public ResponseEntity<Resource> getCssImageResource(@PathVariable("imgName") String imgName) {
		Resource res = resService.getResource(RESOURCE_CSS_IMAGE_FOLDER + "/" + imgName);
		return setImageResponseMediaType(res);
	}

	private ResponseEntity<Resource> setImageResponseMediaType(Resource res) {
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

	@RequestMapping(method = RequestMethod.GET, value = "/repo/svg/{nameInRepo:.+}/{imgNo}")
	public ResponseEntity<Resource> getSvgFromRepo(@PathVariable("nameInRepo") String nameInRepo,@PathVariable("imgNo")String slideNo) {
		Resource res = resService.getResource(RESOURCE_SOURCE_DISK + RESOURCE_REPO_FOLDER + "/" + nameInRepo+"/svg-folder/"+nameInRepo+slideNo);
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType(SVG_MEDIA_TYPE, SVG_MEDIA_SUBTYPE);
		headers.setContentType(mediaType);
		return new ResponseEntity<>(res, headers, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/repo/ext/{nameInRepo:.+}")
	public ResponseEntity<Resource> getExternalPresentation(@PathVariable("nameInRepo") String nameInRepo) {
		String[] presentationName =nameInRepo.split("\\.");
		Resource  res = resService.getResource(RESOURCE_SOURCE_DISK + RESOURCE_REPO_FOLDER + "/"+presentationName[0]+"/"+nameInRepo);		
		return setImageResponseMediaType(res);
	}

	private final String RESOURCE_CSS_LOCAL_FOLDER = ConfigurationLoader.getConfig().getString(
			"resources.default.css.local.folder"), RESOURCE_CSS_THEME_FOLDER = ConfigurationLoader.getConfig()
			.getString("resources.default.css.theme.folder"), RESOURCE_JS_FOLDER = ConfigurationLoader.getConfig()
			.getString("resources.default.js.folder"), RESOURCE_CSS_IMAGE_FOLDER = ConfigurationLoader.getConfig()
			.getString("resources.default.css.images.folder"), RESOURCE_REPO_FOLDER = ConfigurationLoader.getConfig()
			.getString("local.full.repo.path"), RESOURCE_CSS_LOCAL_IMG_FOLDER = ConfigurationLoader.getConfig()
			.getString("resources.default.css.local.image.folder"), SVG_MEDIA_TYPE = "image",
			SVG_MEDIA_SUBTYPE = "svg+xml", RESOURCE_SOURCE_DISK = "file:";
}
