package com.school.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.controller.dto.ChangePasswordDTO;
import com.school.dao.BaseDao;
import com.school.job.Job;
import com.school.job.sender.IJobSender;
import com.school.model.Email;
import com.school.model.User;
import com.school.model.UserAuthority;
import com.school.util.ConfigurationLoader;
import com.school.util.JsonUtils;
import com.school.util.OtherUtils;

@Controller
@RequestMapping(value = "/user")
@Scope("request")
public class UserController extends AbstractController {
	@Resource(name = "baseDaoImpl")
	BaseDao baseDao;
	@Resource(name = "messageSource")
	ResourceBundleMessageSource messages;
	@Resource(name = "jobSenderImpl")
	IJobSender queue;

	@RolesAllowed(value={"ROLE_ADMIN"})
	@RequestMapping(method = RequestMethod.DELETE, value = "/{uniqueId}", produces = "application/json")
	@ResponseBody
	public Map<String, Object> deletePresentation(@PathVariable("uniqueId") Long userId) {
		Map<String, Object> result = JsonUtils.failureJson("You must provide a User id");
		if (userId != null) {
			baseDao.deleteUser(userId);
			result = JsonUtils.successWithParameter(JsonUtils.PARAM_MESSAGE, "The user has been deleted successfully.");
		}
		return result;
	}
	
	@RolesAllowed(value={"ROLE_ADMIN"})
	@RequestMapping(method = RequestMethod.PUT, value = "/{uniqueId}", produces = "application/json")
	@ResponseBody
	public Map<String, Object> resetPasswod(@PathVariable("uniqueId") Long userId) {
		Map<String, Object> result = JsonUtils.failureJson("You must provide a User id");
		if (userId != null) {
			User user = baseDao.getEntity(userId, User.class);
			String subject = "New Password";
			String passw=UUID.randomUUID().toString();
			user.setPassword(passw);
			baseDao.update(user);
			String content = "Your new Password is:\"" + passw + "\"";
			Email email = new Email(subject, user.getEmail(), content);
			Long id = baseDao.save(email);
			String notifQueue = ConfigurationLoader.getConfig().getString("active.mq.queue.notification");
			Job job = new Job(id);
			queue.send(job, notifQueue);
			result = JsonUtils.successWithParameter(JsonUtils.PARAM_MESSAGE, "The user has been deleted successfully.");
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/checkUsername", produces = "application/json")
	@ResponseBody
	public Map<String, Object> checkUsername(@RequestParam String username) {
		Map<String, Object> result = JsonUtils.failureJson(messages.getMessage("register.username.exists", null, null));
		if (username != null) {
			Object[][] params = { { "username", username } };
			List<User> userList = baseDao.getEntitiesWithConditions(User.class, params, null);
			if (userList.isEmpty()) {
				result = JsonUtils.successJson();
			}
		}
		return result;
	}
	@RolesAllowed(value={"ROLE_ADMIN"})
	@RequestMapping(method = RequestMethod.GET, value = "/allUsers", produces = "application/json")
	@ResponseBody
	public Map<String, Object> getAllUsers() {
		Map<String, Object> result = new TreeMap<>();
		List<User> userList = baseDao.getAllEntities(User.class);
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (User user : userList) {
			Map<String, Object> userIn = new TreeMap<>();
			userIn.put("0", user.getUsername());
			userIn.put("1", user.getName());
			userIn.put("2", user.getEmail());
			userIn.put("DT_RowId", user.getId());
			resultList.add(userIn);
		}
		result.put(DATA, resultList);
		return result;
	}
	
	@RolesAllowed(value={"ROLE_USER"})
	@RequestMapping(method = RequestMethod.POST, value = "/changePassword", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Map<String, Object> changePassword(@RequestBody ChangePasswordDTO pass) {
		Map<String, Object> result = null;
		User user = getCurrentUser();
		if (user != null) {
			if (StringUtils.isNotEmpty(pass.getOldPassword())
					&& OtherUtils.hashPassword(pass.getOldPassword()).equals(user.getPassword())) {
				if (StringUtils.isNotEmpty(pass.getNewPassword())
						&& StringUtils.isNotEmpty(pass.getRetypedNewPassword())
						&& pass.getNewPassword().equals(pass.getRetypedNewPassword())) {
					user.setPassword(pass.getNewPassword());
					baseDao.update(user);
					result = JsonUtils.successWithParameter(JsonUtils.PARAM_MESSAGE,
							"The password was changed successfully !");
				} else {
					result = JsonUtils.failureJson("New passwords do not match !");
				}
			} else {
				result = JsonUtils.failureJson("The old password is incorect !");
			}
		} else {
			result = JsonUtils.failureJson("You must be logged in in order to perform this operation !");
		}
		return result;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/register", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Map<String, Object> registerUser(@RequestBody User user) {
		Map<String, Object> result = null;
		if (user.isValidData()) {
			Long id = baseDao.save(user);
			user.setId(id);
			user.setActive(true);
			if (id != null) {
				UserAuthority auth = new UserAuthority();
				auth.setAuthority(OtherUtils.UserRole.ROLE_USER.name());
				auth.setUser(user);
				baseDao.save(auth);
			}
			result = JsonUtils.successWithParameter(JsonUtils.PARAM_MESSAGE,
					messages.getMessage("register.success", null, null));
		} else {
			result = JsonUtils.failureJson(messages.getMessage("register.failure", null, null));
		}
		return result;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void setMessages(ResourceBundleMessageSource messages) {
		this.messages = messages;
	}

	public void setQueue(IJobSender queue) {
		this.queue = queue;
	}

	private static final String DATA = "aaData";

}
