package com.school.controller.dto;

public class ChangePasswordDTO {
	private String oldPassword;
	private String newPassword;
	private String retypedNewPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRetypedNewPassword() {
		return retypedNewPassword;
	}

	public void setRetypedNewPassword(String retypedNewPassword) {
		this.retypedNewPassword = retypedNewPassword;
	}

}
