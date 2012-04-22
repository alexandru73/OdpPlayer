$(document).ready(function() {
	var register = new Register();
	register.init();
});

function Register() {
	this.validators = new Array();
	this.okMessage = 'OK';
	this.mandatoryMessage = 'This field is mandatory';
	this.usernameValid=false;
};

Register.prototype = {

	init : function() {
		this.initValidators();
		this.submitInit();
		this.checkUsernameInit();
	},

	submitInit : function() {
		var validators = this.validators;
		var that = this;
		$('#registerSubmit').click(function() {
			var okToSubmit = LiveValidation.massValidate(validators);
			if (okToSubmit && that.usernameValid) {
				that.submitForm();
			}
		});
	},

	checkUsernameInit : function() {
		var that = this;
		$('#username').blur(
				function() {
					var username=$('#username').val();
					if (username != null && username != '') {
						if (username.length > 2) {
							var url = "user/checkUsername?username="
									+ $('#username').val();
							ajaxJsonWithParamGet(url,that ,that.onUsernameCheckComplete);
						} else {
							var message="The username must contain more than 2 characters !";
							that.appendUsernameMessage(message,false );
						}
					}else{
						that.appendUsernameMessage(that.mandatoryMessage,false );
					}	
				});
	},

	onUsernameCheckComplete : function(response,that) {
		if (response.success) {
			that.appendUsernameMessage("OK",true);
		} else {
			that.appendUsernameMessage("The username already exists !",false);
		}
	},

	onSubmitComplete : function(response) {
		alert('success ' + response.success);
	},

	getFormData : function() {
		var registerData = $('#register').serializeObject();
		var data = new Object();
		data.name = registerData.name;
		data.email = registerData.email;
		data.username = registerData.username;
		data.password = registerData.password;
		return data;
	},

	submitForm : function() {
		var data = this.getFormData();
		ajaxJsonPost("user/register", data, this, this.onSubmitComplete);
	},

	appendUsernameMessage : function(message, status) {
		$('#usrSpan').remove();
		var klass = "LV_invalid";
		this.usernameValid=status;
		$('#username').addClass("LV_invalid_field");
		if (status) {
			klass = "LV_valid";
			$('#username').removeClass("LV_invalid_field");
			$('#username').addClass("LV_valid_field");
		}
		var spanz = "<span id='usrSpan'>" + message + "</span>";
		$('#username').parent().append(spanz);
		$('#usrSpan').addClass(klass);
	},

	initValidators : function() {
		var name = new LiveValidation("name", {
			validMessage : this.okMessage,
			wait : 500
		}).add(Validate.Length, {
			minimum : 6
		}).add(Validate.Presence, {
			failureMessage : this.mandatoryMessage
		});
		this.validators.push(name);
		var email = new LiveValidation("email", {
			validMessage : this.okMessage,
			wait : 500
		}).add(Validate.Presence, {
			failureMessage : this.mandatoryMessage
		}).add(Validate.Email);
		this.validators.push(email);
		var password = new LiveValidation("password", {
			validMessage : this.okMessage,
			wait : 500
		}).add(Validate.Length, {
			minimum : 6
		}).add(Validate.Presence, {
			failureMessage : this.mandatoryMessage
		});
		this.validators.push(password);
		var retype = new LiveValidation("retype-password", {
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
