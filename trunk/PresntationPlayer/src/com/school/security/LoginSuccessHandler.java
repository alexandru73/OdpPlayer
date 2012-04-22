package com.school.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.school.model.User;

public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws java.io.IOException, javax.servlet.ServletException {
		HttpSession session = request.getSession();
		User user = (User) authentication.getPrincipal();
		session.setAttribute("name", user.getName());
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
