package com.school.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.school.model.User;

public abstract class AbstractController {
	public User getCurrentUser() {
		Object currentUser = ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
		if (currentUser instanceof User) {
			return (User)currentUser;
		}
		return null;
	}
}
