package com.school.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.dao.BaseDao;
import com.school.job.Job;
import com.school.job.JobSenderImpl;
import com.school.model.DetailedPresentation;
import com.school.util.ConfigurationLoader;
import com.school.util.JsonUtils;

@Controller
@RequestMapping(value = "/presentation")
@Scope(value = "request")
public class PresentationController {
	@Resource(name = "baseDaoImpl")
	BaseDao baseDao;
	@Resource(name = "jobSenderImpl")
	JobSenderImpl queue;
	@Resource(name = "messageSource")
	ResourceBundleMessageSource messages;

	@RequestMapping(method = RequestMethod.DELETE, value = "/{uniqueName}", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deletePresentation(@PathVariable("uniqueName") String name) {
		Map<String, Object> result = null;
		Object[][] conditions = { { "repositoryName", name } };
		List<DetailedPresentation> presentationList = baseDao.getEntitiesWithConditions(DetailedPresentation.class,
				conditions, null);
		if (CollectionUtils.isNotEmpty(presentationList)) {
			DetailedPresentation presentation = presentationList.get(0);
			queue.send(new Job(presentation.getId()), DELETE_QUEUE);
			String successMessage = messages.getMessage("operation.success.wait.for.confirmation", null, null);
			result = JsonUtils.successWithParameter(JsonUtils.PARAM_MESSAGE, successMessage);
		} else {
			String failureMessage = messages.getMessage("presentation.does.not.exist", null, null);
			result = JsonUtils.failureJson(failureMessage);
		}
		return result;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void setQueue(JobSenderImpl queue) {
		this.queue = queue;
	}

	public void setMessages(ResourceBundleMessageSource messages) {
		this.messages = messages;
	}

	private final String DELETE_QUEUE = ConfigurationLoader.getConfig()
			.getString("active.mq.queue.delete.presentation");
}
