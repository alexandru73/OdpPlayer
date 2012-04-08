package com.school.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.school.dao.BaseDao;
import com.school.exceptions.UploadedDataNotFoundException;
import com.school.model.UploadedPresentationData;
import com.school.model.User;
import com.school.upload.job.Job;
import com.school.upload.job.JobSenderImpl;
import com.school.util.ConfigurationLoader;
import com.school.util.JsonUtils;

@Controller
@RequestMapping(value = "/upload")
@Scope(value = "request")
public class UploadController {
	@Resource(name = "messageSource")
	ResourceBundleMessageSource messages;
	@Resource(name = "jobSenderImpl")
	JobSenderImpl queue;
	@Resource(name = "baseDaoImpl")
	BaseDao baseDao;

	@RequestMapping(method = RequestMethod.GET, value = "/upload")
	public String getPresentationPage() {
		return "upload/uploadPage";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/test")
	public String getPresentationPagea() {
		return "test";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/uploadEx", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAllEmp2(@RequestBody UploadedPresentationData data) {
		System.out.println(data.toString());
		Map<String, Object> map = new TreeMap<>();
		map.put("success", true);
		return map;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/uploadEx", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAllEmp22() {
		Map<String, Object> map = new TreeMap<>();
		map.put("success", true);
		map.put("alex", new UploadedPresentationData("ads", "ASD", "dsf"));
		return map;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/uploadMex", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAllEmp3(@RequestBody UploadedPresentationData data) {
		System.out.println(data.toString());
		Map<String, Object> map = new TreeMap<>();
		map.put("success", false);
		map.put("errorMessage", "sdfijnif");
		return map;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/uploadMeta", headers = "Accept=application/json")
	@ResponseBody
	public Map<String, Object> saveUploadMetadata(HttpServletRequest request, @RequestBody UploadedPresentationData data) {
		Map<String, Object> result = null;
		if (data.isValidData()) {
			data.setUser(getCurrentUser());
			HttpSession session = request.getSession();
			session.setAttribute(UPLOAD_METADATA, data);
			result = JsonUtils.successJson();
		} else {
			result = JsonUtils.failureJson(messages.getMessage("validation.upload.data.is.incorect", null, null));
		}
		return result;
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadFile(HttpServletRequest req,
			@RequestParam("fileData") CommonsMultipartFile uploadedFile) {
		Map<String, Object> result = null;
		if (uploadedFile == null) {
			result = JsonUtils.failureJson();
		} else {
			try {
				if (uploadedFile.isEmpty()) {
					String errorMessage = messages.getMessage("validation.upload.file.is.empty", null, null);
					result = JsonUtils.failureJson(errorMessage);
				} else {
					HttpSession session = req.getSession();
					System.out.println(uploadedFile.getContentType());
					uploadFileToRepo(uploadedFile, session);
					String completeMessage = messages.getMessage("validation.upload.complete.message", null, null);
					result = JsonUtils.successWithParameter(UPLOAD_COMPLETE, completeMessage);
				}
			} catch (UploadedDataNotFoundException e) {
				String errorMessage = messages.getMessage("validation.upload.first.set.metadata", null, null);
				result = JsonUtils.failureJson(errorMessage);
			} catch (IllegalStateException | IOException e) {
				String errorMessage = messages.getMessage("validation.upload.failed", null, null);
				result = JsonUtils.failureJson(errorMessage);
			}
		}
		return result;
	}

	@RequestMapping(value = "/completeUpload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> completeUpload(HttpServletRequest req) {
		Map<String, Object> result = null;
		HttpSession session = req.getSession();
		UploadedPresentationData data = (UploadedPresentationData) session.getAttribute(UPLOAD_METADATA);
		if (data != null) {
			Long id = baseDao.save(data);
			Job job = new Job(id);
			queue.send(job);
			result = JsonUtils.successJson();
		} else {
			String errorMessage = messages.getMessage("validation.upload.first.set.metadata", null, null);
			result = JsonUtils.failureJson(errorMessage);
		}
		session.removeAttribute(UPLOAD_METADATA);
		return result;
	}

	private void uploadFileToRepo(CommonsMultipartFile uploadedFile, HttpSession session) throws IOException,
			UploadedDataNotFoundException {
		UploadedPresentationData data = (UploadedPresentationData) session.getAttribute(UPLOAD_METADATA);
		if (data != null) {
			String fileExtension = FilenameUtils.getExtension(uploadedFile.getName());
			if (isCorectFileExtension(fileExtension)) {
				data.setRepositoryName(UUID.randomUUID().toString());
				data.setRepositoryPath(REPO_UPLOAD_LOCATION);
				data.setCreateadAt(new Date());
				File file = new File(REPO_UPLOAD_LOCATION + "/" + data.getRepositoryName() + "." + fileExtension);
				uploadedFile.transferTo(file);
				session.setAttribute(UPLOAD_METADATA, data);
			} else {

			}
		} else {
			throw new UploadedDataNotFoundException();
		}
	}

	private boolean isCorectFileExtension(String fileExtension) {
		// TODO Auto-generated method stub
		return false;
	}

	private User getCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setMessages(ResourceBundleMessageSource messages) {
		this.messages = messages;
	}

	public JobSenderImpl getQueue() {
		return queue;
	}

	public void setQueue(JobSenderImpl queue) {
		this.queue = queue;
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	private static final String REPO_UPLOAD_LOCATION = ConfigurationLoader.getConfig().getString(
			"local.repository.upload.path"), UPLOAD_METADATA = "metadata", UPLOAD_COMPLETE = "complete";
}
