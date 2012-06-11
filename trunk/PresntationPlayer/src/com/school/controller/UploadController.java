package com.school.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
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

import com.school.controller.dto.PresentationMetaDTO;
import com.school.dao.BaseDao;
import com.school.exceptions.IncorectFileException;
import com.school.exceptions.PresentationException;
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

	@RequestMapping(method = RequestMethod.POST, value = "/uploadMeta", produces = "application/json")
	@ResponseBody
	public Map<String, Object> saveUploadMetadata(HttpServletRequest request,
			@RequestBody PresentationMetaDTO presentationData) {
		Map<String, Object> result = null;
		Presentation data = new Presentation(presentationData.getTitle(), presentationData.getDescription(),
				presentationData.getSlideDuration());
		try {
			if (data.isValidData()) {
				Cathegory cat = getCathegoryByName(presentationData.getCathegory());
				data.setUser(getCurrentUser());
				data.setCathegory(cat);
				HttpSession session = request.getSession();
				session.setAttribute(UPLOAD_METADATA, data);
				result = JsonUtils.successJson();
			} else {
				result = JsonUtils.failureJson(messages.getMessage("validation.upload.data.is.incorect", null, null));
			}
		} catch (PresentationException e) {
			result = JsonUtils.failureJson(messages.getMessage("validation.upload.data.is.incorect", null, null));
		}
		return result;
	}

	private Cathegory getCathegoryByName(Long catId) throws PresentationException {
		Cathegory cathegory = baseDao.getEntity(catId, Cathegory.class);
		if (cathegory == null) {
			throw new PresentationException();
		}
		return cathegory;
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
					Presentation data = uploadFileToRepo(uploadedFile, session);
					if (data != null) {
						Long id = baseDao.save(data);
						Job job = new Job(id);
						queue.send(job, UPLOAD_QUEUE);
						result = JsonUtils.successWithParameter(JsonUtils.PARAM_MESSAGE,
								messages.getMessage("upload.success", null, null));
					} else {
						String errorMessage = messages.getMessage("validation.upload.first.set.metadata", null, null);
						result = JsonUtils.failureJson(errorMessage);
					}
					session.removeAttribute(UPLOAD_METADATA);
				}
			} catch (UploadedDataNotFoundException e) {
				String errorMessage = messages.getMessage("validation.upload.first.set.metadata", null, null);
				result = JsonUtils.failureJson(errorMessage);
			} catch (IllegalStateException | IOException e) {
				String errorMessage = messages.getMessage("validation.upload.failed", null, null);
				result = JsonUtils.failureJson(errorMessage);
			} catch (IncorectFileException e) {
				String errorMessage = messages.getMessage("validation.upload.incorect.file.extension", null, null);
				String acceptedEx = getExtensions(acceptedExtensions);
				result = JsonUtils.failureJson(errorMessage + acceptedEx);
			}
		}
		return result;
	}

	private String getExtensions(String[] acceptedExtensions) {
		StringBuilder extensions = new StringBuilder();
		for (int i = 0; i < acceptedExtensions.length; i++) {
			extensions.append(acceptedExtensions[i] + ",");
		}
		return extensions.toString();
	}

	private Presentation uploadFileToRepo(CommonsMultipartFile uploadedFile, HttpSession session) throws IOException,
			UploadedDataNotFoundException, IncorectFileException {
		Presentation data = (Presentation) session.getAttribute(UPLOAD_METADATA);
		if (data != null) {
			String fileExtension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
			if (isCorectFileExtension(fileExtension)) {
				checkPreviousUploads(data);
				data.setRepositoryName(UUID.randomUUID().toString());
				data.setRepositoryPath(REPO_UPLOAD_LOCATION);
				data.setOriginalExtension(fileExtension);
				File file = createFile(data, fileExtension);
				uploadedFile.transferTo(file);
				return data;
			} else {
				throw new IncorectFileException();
			}
		} else {
			throw new UploadedDataNotFoundException();
		}
	}

	private File createFile(Presentation data, String fileExtension) {
		File file = new File(REPO_HOME + "/" + REPO_UPLOAD_LOCATION + "/" + data.getRepositoryName() + "."
				+ fileExtension);
		return file;
	}

	private void checkPreviousUploads(Presentation data) {
		File file = createFile(data, data.getOriginalExtension());
		FileUtils.deleteQuietly(file);
	}

	private boolean isCorectFileExtension(String fileExtension) {
		return Arrays.asList(acceptedExtensions).contains(fileExtension);
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
			"active.mq.queue.convert.presentation"), UPLOAD_METADATA = "metadata";
	private static final String[] acceptedExtensions = { "ppt", "odp", "pptx" };
}
