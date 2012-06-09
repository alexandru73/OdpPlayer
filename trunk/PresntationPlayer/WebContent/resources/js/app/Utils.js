var successMessageTemplate = "<div id='repl_id' class='repl_classes  ui-corner-all'>"
		+ "<p><span class='ui-icon repl_icon' style='float: left; margin-right: .3em;''></span>"
		+ "<strong>repl_status</strong>repl_message</p></div>";

var dialogTemplate = "<p>"
		+ "<span class='ui-icon ui-icon-circle-check' style='float:left; margin:0 7px 50px 0;'></span> "
		+ "repl_message </p>";

function appendMessage(element, message, id, status, icon, klasses) {
	var str = successMessageTemplate.replace("repl_id", id).replace(
			"repl_icon", icon).replace("repl_status", status).replace(
			"repl_message", message).replace("repl_classes", klasses);
	element.append(str);
};

function appendSuccessMessageDivTo(element, response, id) {
	appendMessage(element, response.message, id, "Success : ", "ui-icon-info",
			"ui-state-highlight");
};

function appendFailureMessageDivTo(element, response, id) {
	appendMessage(element, response.errorMessage, id, "Failure : ",
			"ui-icon-alert", "ui-state-error");
};

function appendUsernameMessage(message, status, id, field, appendTo) {
	$("#" + id).remove();
	var klass = "LV_invalid";
	field.addClass("LV_invalid_field");
	if (status == true) {
		klass = "LV_valid";
		field.removeClass("LV_invalid_field");
		field.addClass("LV_valid_field");
	}
	var spanz = "<span id='usrSpan' class='repl_class'>" + message + "</span>";
	appendTo.append(spanz.replace("usrSpan", id).replace("repl_class", klass));
};

function hasWhiteSpace(se) {
	reWhiteSpace = new RegExp(/\s/g);
	// Check for white space
	if (reWhiteSpace.test(se)) {
		return true;
	}
	return false;
}

function addDisabledClass(componentID) {
	$(componentID).addClass("step-panel");
	var h3 = componentID + " h3";
	$(h3).css("font-weight", "normal");
	$(h3).css("color", "red");
};

function removeDisabledClass(componentID) {
	$(componentID).removeClass("step-panel");
	var h3 = componentID + " h3";
	$(h3).css("font-weight", "bold");
	$(h3).css("color", "black");
};

function addDialogToBody(dialogId, title, message) {
	$('#' + dialogId).empty();
	$('#' + dialogId).attr("title", title);
	var str = dialogTemplate.replace("repl_title", title).replace(
			"repl_message", message);
	$('#' + dialogId).append(str);

};

function getUrlParam(name) {
	name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
	var regexS = "[\\?&]" + name + "=([^&#]*)";
	var regex = new RegExp(regexS);
	var results = regex.exec(window.location.href);
	if (results == null)
		return "";
	else
		return results[1];
}

function isInt(value) {
	if ((parseFloat(value) == parseInt(value)) && !isNaN(value)) {
		return true;
	} else {
		return false;
	}
}

function getCathegories() {
	var callblack = function(response) {
		if (response.success == true) {
			$.each(response.cathegories, function(key, value) {
				$('#combobox').append($('<option>', {
					value : value.id
				}).text(value.name));
			});
		} else {
			addDialogToBody("dialog-message", dialogTitle,
					response.errorMessage);
			$('#dialog-message').open();
		}
	};
	ajaxJsonGet("presentation/cathegories", callblack);
}
