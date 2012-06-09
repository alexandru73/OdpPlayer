package com.school.controller.dto;

import com.school.model.DetailedPresentation;

public class SearchResultDTO {
	private DetailedPresentation presentation;
	private boolean belongsToCurrentUser;

	public DetailedPresentation getPresentation() {
		return presentation;
	}

	public void setPresentation(DetailedPresentation presentation) {
		this.presentation = presentation;
	}

	public boolean isBelongsToCurrentUser() {
		return belongsToCurrentUser;
	}

	public void setBelongsToCurrentUser(boolean belongsToCurrentUser) {
		this.belongsToCurrentUser = belongsToCurrentUser;
	}

}
