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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.dao.BaseDao;
import com.school.job.Job;
import com.school.job.JobSenderImpl;
import com.school.model.Cathegory;
import com.school.model.DetailedPresentation;
import com.school.model.User;
import com.school.util.ConfigurationLoader;
import com.school.util.JsonUtils;

@Controller
@RequestMapping(value = "/presentation")
@Scope(value = "request")
public class PresentationController extends AbstractController {
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

	@RequestMapping(method = RequestMethod.GET, value = "/watch", produces = "application/json")
	@ResponseBody
	public Map<String, Object> watchPresentation(@RequestParam String p) {
		Map<String, Object> result = null;
		Object[][] conditions = { { "repositoryName", p } };
		List<DetailedPresentation> presentationList = baseDao.getEntitiesWithConditions(DetailedPresentation.class,
				conditions, null);
		if (CollectionUtils.isNotEmpty(presentationList)) {
			DetailedPresentation presentation = presentationList.get(0);
			Long noViews = presentation.getNoOfViews();
			presentation.setNoOfViews(++noViews);
			baseDao.update(presentation);
			presentation.getUser().setAuthorities(null);
			presentation.getUser().setPassword(null);
			result = JsonUtils.successWithParameter(JsonUtils.PARAM_PRESENTATION, presentation);
		} else {
			String failureMessage = messages.getMessage("presentation.does.not.exist", null, null);
			result = JsonUtils.failureJson(failureMessage);
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search", produces = "application/json")
	@ResponseBody
	public Map<String, Object> watchPresentation(@RequestParam Integer page, @RequestParam String searchQuery,
			@RequestParam Long cathegory, @RequestParam Boolean min) {
		Map<String, Object> result = JsonUtils.failureJson("Internal error");
		User user = null;
		if (min) {
			user = getCurrentUser();
		}
		int perPage = 2;
		Long nr = baseDao.countDetailedPresentations(user,searchQuery,cathegory);
		if (nr != null && nr != 0) {
			int noPages = (int) (nr / perPage);
			int remaining = (int) (nr % perPage);
			if ((page > noPages && remaining == 0)|| (page > (noPages + 1) && remaining != 0)) {
				page = (remaining != 0)?(noPages+1):noPages;
			}
			List<DetailedPresentation> presentationList = baseDao.getPaginatedElements(page, user, searchQuery,
					cathegory, perPage);
			if (CollectionUtils.isNotEmpty(presentationList)) {
				for (DetailedPresentation detailedPresentation : presentationList) {
					detailedPresentation.getUser().setAuthorities(null);
					detailedPresentation.getUser().setPassword(null);
				}
				result = JsonUtils.successWithParameter(JsonUtils.PARAM_PP_LIST, presentationList);
				result.put(JsonUtils.PARAM_PAGE_NO, page);
			}else{
				result=JsonUtils.failureJson();
				result.put(JsonUtils.PARAM_PAGE_NO, page);
			}
		}else{
			result = JsonUtils.failureJson("No presentations were found ");
			result.put(JsonUtils.PARAM_HAS_RESULTS, false);
		}
		return result;
	}
	
	@RequestMapping(value = "/cathegories", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getCathegories() {
		List<Cathegory> cathegories = baseDao.getAllEntities(Cathegory.class);
		return JsonUtils.successWithParameter(CATHEGORIES, cathegories);
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
	private final static String CATHEGORIES = "cathegories";
}
