$(function() {
	$("#progressbar").progressbar({
		value : 0,
		disabled : true
	});
	
	$("input:button,input:submit,#complete-link").button();
	$( "#dialog-message" ).dialog({
		modal: true,
		autoOpen: false,
		show: { effect: 'drop', direction: "up" },
		buttons: {
			Ok: function() {
				$(this).dialog("close");
			}
		}
	});
});


var dialogTitle="Error";
$(document).ready(function() {
	var upload = new Upload();
	upload.init();
});

_uacct = "UA-850242-2";
urchinTracker();

function ajaxUploadReady() {
	var progressBar = $('#progressbar');
	$('form').ajaxForm({
		beforeSend : function() {
			progressBar.progressbar("disabled", false);
			progressBar.progressbar("value", 0);

		},
		
		uploadProgress : function(event, position, total, percentComplete) {
			progressBar.progressbar("value", percentComplete);
		},
		complete : function(response) {
			var resp=JSON.parse(response.responseText);
			if (resp.success) {
				$("#fileUploadForm :input").attr("disabled", true);
				$("#completeUpload :input").attr("disabled", false);
				addDisabledClass('#step2-div');
				removeDisabledClass('#step3-div');
				$('input:button , input:submit').removeClass('ui-state-hover');
			}else{
				addDialogToBody("dialog-message", dialogTitle, resp.errorMessage);
				$('#dialog-message').dialog('open');
			}
		}
	});
};

function Upload() {
	this.okMessage = "✓";
	this.mandatory = "This field is mandatory";
	this.okCathegoryCombo = false;
	this.metaValidators = new Array();
	this.agreeToLicenceValidator=new Array();
};

Upload.prototype = {

	init : function() {
		getCathegories();
		this.initMetaForm();
		this.initUI();
		this.initUploadForm();
		this.initCompleteForm();
		this.completeUploadInit();
	},
	
	removeButtonFocus:function(){
		$('input:button , input:submit').removeClass('ui-state-hover');
	},
	
	initUI:function(){
		this.removeButtonFocus();
		$('input , textarea').removeClass('LV_valid_field');
		$('.LV_valid').remove();
		$('step0-div').empty();
		$('#combobox').empty();
		$("#metaForm :input").attr("disabled", false);
		removeDisabledClass('#step1-div');
		$("#fileUploadForm :input").attr("disabled", true);
		addDisabledClass('#step2-div');
		$("#completeUpload :input").attr("disabled", true);
		addDisabledClass('#step3-div');
	},
	
	initMetaForm : function() {
		var title=new LiveValidation( "title", { validMessage: this.okMessage , wait: 500 } )
			.add( Validate.Presence, {failureMessage : this.mandatory } )
			.add( Validate.Length, { minimum: 6 } );
		this.metaValidators.push(title);
		var description= new LiveValidation( "description", { validMessage: this.okMessage , wait: 500 } )
		 	.add( Validate.Length, { minimum: 6 } )
		 	.add( Validate.Presence, {failureMessage : this.mandatory } );
		this.metaValidators.push(description);
		var duration=new LiveValidation( "slideDuration", { validMessage: this.okMessage , wait: 500 } )
			.add( Validate.Presence, {failureMessage : this.mandatory } )
			.add(Validate.Numericality, { maximum: 1000 , onlyInteger: true });
		this.metaValidators.push(duration);	
		
		var that=this;
		$('#comboInput').blur(function() {
			that.validateCombo();
			that.removeButtonFocus();
		});
		
		$('#metaSubmit').click(function() {
			that.validateCombo();
			var okToSubmit = LiveValidation.massValidate(that.metaValidators);
			if (okToSubmit==true && that.okCathegoryCombo==true) {
				that.submitMetaData();
				that.removeButtonFocus();
			};
		});
	},
	
	
	validateCombo : function() {
		var element = $('#combobox');
		var combo = element.val();
		if (combo != null && combo != "") {
			this.okCathegoryCombo = true;
			appendUsernameMessage(this.okMessage, true, "comboSpan",
					$("#comboInput"), element.parent());
		} else {
			this.okCathegoryCombo = false;
			appendUsernameMessage(this.mandatory, false, "comboSpan",
					$("#comboInput"), element.parent());
		}
	},

		
	submitMetaData : function() {
		var callBack = function(response) {
			if (response.success) {
				$("#metaForm :input").attr("disabled", true);
				addDisabledClass('#step1-div');
				removeDisabledClass('#step2-div');
			}else{
				addDialogToBody("dialog-message", dialogTitle, response.errorMessage);
				$('#dialog-message').dialog('open');
			}
		};
		var formMetadata = $('#metaForm').serializeObject();
		$("#fileUploadForm :input").attr("disabled", false);
		ajaxJsonPost("upload/uploadMeta", formMetadata, this, callBack);
		
		return false;
	},
	
	initUploadForm : function(){
		new LiveValidation( "fileData", { validMessage: this.okMessage , wait: 500 } )
			.add( Validate.Presence, {failureMessage : this.mandatory } );	
		$("#previousStep1").click(function(){
			$("#fileUploadForm :input").attr("disabled", true);
			$("#metaForm :input").attr("disabled", false);
			addDisabledClass('#step2-div');
			removeDisabledClass('#step1-div');
			that.removeButtonFocus();
		});
		$("#submitUpload").click(function(){
			$("#submitUpload").attr("disabled", false);
		});
	},
	
	initCompleteForm:function(){
		var agreeToLicence=new LiveValidation( "agreeToLicence", { validMessage: this.okMessage , wait: 500 } )
			.add( Validate.Acceptance );
		this.agreeToLicenceValidator.push(agreeToLicence);
		$("#previousStep2").click(function(){
			$("#fileUploadForm :input").attr("disabled", false);
			$("#completeUpload :input").attr("disabled", true);
			removeDisabledClass('#step2-div');
			addDisabledClass('#step3-div');
			that.removeButtonFocus();
		});

	},
	createCallbackCompleteUpload:function(){
		var that=this;
		var callback=function(response){
			var id="result-complete";
			var element=$('#step0-div');
			$('body').animate({scrollTop: $('body').position().top}, 'fast');
			element.show(500);
		    setTimeout(function() {
		    	element.hide(700);
		    }, 5000);
			if(response.success){
				that.initUI();
				$('#step1-div').clearForm();
				$('#step2-div').clearForm();
				$('#step3-div').clearForm();
				appendSuccessMessageDivTo(element, response, id);
			}else{
				appendFailureMessageDivTo(element, response, id);
			}		
		};
		return callback;
	},
	completeUploadInit:function(){
		var that=this;
		$('#completeSubmit').click(function() {
			var callback=that.createCallbackCompleteUpload();	
			var okToSubmit = LiveValidation.massValidate(that.agreeToLicenceValidator);
			if (okToSubmit) {
				$("#completeUpload :input").attr("disabled", true);
				ajaxJsonGet("upload/completeUpload", callback);
				that.removeButtonFocus();
			}
		});
	}
};
