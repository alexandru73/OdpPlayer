$(document).ready(function() {
	var changePassword = new ChangePassword();
	changePassword.init();
});

function ChangePassword() {
	this.validators = new Array();
	this.okMessage = "✓";
	this.mandatoryMessage = 'This field is mandatory';
	this.usernameValid = false;
	this.usrSpan = "usrSpan";
};

ChangePassword.prototype = {

	init : function() {
		this.initValidators();
		this.submitInit();
	},

	submitInit : function() {
		var validators = this.validators;
		var that = this;
		$('#changeSubmit').click(function() {
			var okToSubmit = LiveValidation.massValidate(validators);
			if (okToSubmit) {
				that.submitForm();
			}
		});
	},

	onSubmitComplete : function(response) {
		var id = "result-register";
		var element = $('#step0-div');
		if (response.success == true) {
			$('#register').clearForm();
			$('input , textarea').removeClass('LV_valid_field');
			$('.LV_valid').remove();
			$('body').animate({
				scrollTop : $('body').position().top
			}, 'fast');
			element.show(500);
			element.empty();
			setTimeout(function() {
				element.hide(700);
			}, 5000);
			appendSuccessMessageDivTo(element, response, id);
		} else {
			appendFailureMessageDivTo(element, response, id);
		}
	},

	submitForm : function() {
		var data = $('#changePassword').serializeObject();
		ajaxJsonPost("user/changePassword", data, this, this.onSubmitComplete);
	},

	initValidators : function() {
		var oldPassw = new LiveValidation("oldPassword", {
			validMessage : this.okMessage,
			wait : 500
		}).add(Validate.Length, {
			minimum : 6
		}).add(Validate.Presence, {
			failureMessage : this.mandatoryMessage
		});
		this.validators.push(oldPassw);
		var password = new LiveValidation("newPassword", {
			validMessage : this.okMessage,
			wait : 500
		}).add(Validate.Length, {
			minimum : 6
		}).add(Validate.Presence, {
			failureMessage : this.mandatoryMessage
		});
		this.validators.push(password);
		var retype = new LiveValidation("retypedNewPassword", {
			validMessage : this.okMessage,
			wait : 500
		}).add(Validate.Length, {
			minimum : 6
		}).add(Validate.Presence, {
			failureMessage : this.mandatoryMessage
		}).add(Validate.Confirmation, {
			match : 'password',
			failureMessage : "Passwords do not match !"
		});
		this.validators.push(retype);
	}
};
