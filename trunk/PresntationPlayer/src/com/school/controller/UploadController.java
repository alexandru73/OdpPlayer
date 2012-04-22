package com.school.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
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
import com.school.job.Job;
import com.school.job.JobSenderImpl;
import com.school.model.Cathegory;
import com.school.model.Presentation;
import com.school.util.ConfigurationLoader;
import com.school.util.JsonUtils;

@Controller
@RequestMapping(value = "/upload")
@Scope(value = "request")
public class UploadController extends AbstractController {
	@Resource(name = "messageSource")
	ResourceBundleMessageSource messages;
	@Resource(name = "jobSenderImpl")
	JobSenderImpl queue;
	@Resource(name = "baseDaoImpl")
	BaseDao baseDao;

	@RequestMapping(method = RequestMethod.POST, value = "/uploadMeta", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Map<String, Object> saveUploadMetadata(HttpServletRequest request, @RequestBody Presentation data) {
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

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "application/json")
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

	@RequestMapping(value = "/cathegories", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getCathegories() {
		List<Cathegory> cathegories = baseDao.getAllEntities(Cathegory.class);
		return JsonUtils.successWithParameter(CATHEGORIES, cathegories);
	}

	@RequestMapping(value = "/completeUpload", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> completeUpload(HttpServletRequest req) {
		Map<String, Object> result = null;
		HttpSession session = req.getSession();
		Presentation data = (Presentation) session.getAttribute(UPLOAD_METADATA);
		if (data != null) {
			Long id = baseDao.save(data);
			Job job = new Job(id);
			queue.send(job, UPLOAD_QUEUE);
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
		Presentation data = (Presentation) session.getAttribute(UPLOAD_METADATA);
		if (data != null) {
			String fileExtension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
			if (isCorectFileExtension(fileExtension)) {
				data.setRepositoryName(UUID.randomUUID().toString());
				data.setRepositoryPath(REPO_UPLOAD_LOCATION);
				data.setOriginalExtension(fileExtension);
				File file = new File(REPO_HOME + "/" + REPO_UPLOAD_LOCATION + "/" + data.getRepositoryName() + "."
						+ fileExtension);
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
		return true;
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
			"local.repository.upload.path"), REPO_HOME = ConfigurationLoader.getConfig().getString(
			"local.repository.home.path"), UPLOAD_QUEUE = ConfigurationLoader.getConfig().getString(
			"active.mq.queue.convert.presentation"), UPLOAD_METADATA = "metadata", UPLOAD_COMPLETE = "complete",
			CATHEGORIES = "cathegories";
}
