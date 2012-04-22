$(document).ready(function() {
	var register = new Register();
	register.init();
});

function Register() {
	this.validators = new Array();
	this.okMessage="✓";
	this.mandatoryMessage = 'This field is mandatory';
	this.usernameValid=false;
	this.usrSpan="usrSpan";
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
			that.validateUsername();
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
					that.validateUsername();
				});
	},
	
	validateUsername:function(){
		var userN=$('#username');
		var username=userN.val();
		if (username != null && username != '') {
			if (username.length > 2) {
				var url = "user/checkUsername?username="
						+ $('#username').val();
				ajaxJsonWithParamGet(url,this ,this.onUsernameCheckComplete);
			} else {
				var message="The username must contain more than 2 characters !";
				appendUsernameMessage(message,false,this.usrSpan,userN,userN.parent());
			}
		}else{
			appendUsernameMessage(this.mandatoryMessage,false,this.usrSpan,userN,userN.parent());
		}	
	},
	
	onUsernameCheckComplete : function(response,that) {
		this.usernameValid=response.success;
		var msg=response.errorMessage;
		var userN=$('#username');
		if (response.success==true) {
			msg=that.okMessage;
		} 
		appendUsernameMessage(msg,response.success,that.usrSpan,userN,userN.parent());
	},

	onSubmitComplete : function(response) {
		var id="result-register";
		var element=$('#register-div');
		if(response.success==true){
			$('#register').clearForm();
			appendSuccessMessageDivTo(element, response, id);
		}else{
			appendFailureMessageDivTo(element, response, id);
		}		
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
