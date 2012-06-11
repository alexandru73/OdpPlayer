package com.school.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.school.controller.dto.ChangePasswordDTO;
import com.school.dao.BaseDao;
import com.school.model.User;
import com.school.model.UserAuthority;
import com.school.util.ConfigurationLoader;
import com.school.util.JsonUtils;
import com.school.util.OtherUtils;

@Controller
@RequestMapping(value = "/user")
@Scope("")
public class UserController extends AbstractController {
	@Resource(name = "baseDaoImpl")
	BaseDao baseDao;
	@Resource(name = "messageSource")
	ResourceBundleMessageSource messages;

	@RequestMapping(method = RequestMethod.GET, value = "/int", headers = "Accept=application/json")
	public @ResponseBody
	String getAllEmp() {
		System.out.println("zuuz" + ConfigurationLoader.getConfig().getString("local.repository.upload.path"));
		for (long i = 2; i < 10; i++) {
			// queue.send(new Job(i));
		}

		return "god";
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
					result = JsonUtils.successWithParameter(JsonUtils.PARAM_MESSAGE,"The password was changed successfully !");
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

}
