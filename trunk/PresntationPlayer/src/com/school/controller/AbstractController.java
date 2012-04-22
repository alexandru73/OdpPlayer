package com.school.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.school.model.User;

public abstract class AbstractController {
	public User getCurrentUser() {
		User currentUser = (User) ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication()
				.getPrincipal();
		return currentUser;
	}
}
