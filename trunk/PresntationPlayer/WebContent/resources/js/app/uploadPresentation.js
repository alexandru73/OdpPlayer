function submitMetaData() {
	var callBack = function(response) {
		$("#fileUploadForm :input").attr("disabled", false);
		$("#metaForm :input").attr("disabled", true);
		addDisabledClass('#step1-div');
		removeDisabledClass('#step2-div');
	};
	var formMetadata = $('#metaForm').serializeObject();
	ajaxJsonPost("upload/uploadMeta", formMetadata, this, callBack);
	return false;
};

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
		complete : function(xhr) {
			$("#fileUploadForm :input").attr("disabled", true);
			$("#completeUpload :input").attr("disabled", false);
			addDisabledClass('#step2-div');
			removeDisabledClass('#step3-div');
		}
	});
};

function deletePresentation() {
	var callbackSuccess = function(response) {
		alert(response);
	};
	var url = "presentation/d3b7c7d5-384a-483c-ad5a-b56b16878a9d";
	ajaxJsonDelete(url, callbackSuccess);
};

function initUploadStuff() {
	$(function() {
		$("#progressbar").progressbar({
			value : 0,
			disabled : true
		});
		$("input:button,input:submit,#complete-link").button();
		$("#combobox").combobox(1);
	});
};
initUploadStuff();

function getCathegories() {
	var callblack = function(response) {
		if (response.success == true) {
			$.each(response.cathegories, function(key, value) {
				$('#combobox').append($('<option>', {
					value : key
				}).text(value.name));
			});
		}
	};
	ajaxJsonGet("upload/cathegories", callblack);
}



var okMessage="OK";
var mandatory="This field is mandatory";

function initMetaForm(){

	var validators=new Array(); 
	var title=new LiveValidation( "title", { validMessage: okMessage , wait: 500 } )
	.add( Validate.Presence, {failureMessage : mandatory } )
	.add( Validate.Length, { minimum: 6 } );
	validators.push(title);
	var description= new LiveValidation( "description", { validMessage: okMessage , wait: 500 } )
	 .add( Validate.Length, { minimum: 6 } )
	 .add( Validate.Presence, {failureMessage : mandatory } );
	validators.push(description);
	var duration=new LiveValidation( "slideDuration", { validMessage: okMessage , wait: 500 } )
	.add( Validate.Presence, {failureMessage : mandatory } )
	.add(Validate.Numericality, { maximum: 1000 , onlyInteger: true });
	validators.push(duration);
	
	$('#metaSubmit').click(function() {
		var okToSubmit = LiveValidation.massValidate(validators);
		if (okToSubmit) {
			submitMetaData();
		}
	});
	
}
function initUploadForm(){
	new LiveValidation( "fileData", { validMessage: okMessage , wait: 500 } )
	.add( Validate.Presence, {failureMessage : mandatory } );
	$("#fileUploadForm :input").attr("disabled", true);
	addDisabledClass('#step2-div');
	$("#previousStep1").click(function(){
		$("#fileUploadForm :input").attr("disabled", true);
		$("#metaForm :input").attr("disabled", false);
		addDisabledClass('#step2-div');
		removeDisabledClass('#step1-div');
	});
}



function initCompleteForm(){
	var agreeToLicence=new LiveValidation( "agreeToLicence", { validMessage: okMessage , wait: 500 } )
	.add( Validate.Acceptance );
	var agreeToLicenceValidator=new Array();
	agreeToLicenceValidator.push(agreeToLicence);
	$("#previousStep2").click(function(){
		$("#fileUploadForm :input").attr("disabled", false);
		$("#completeUpload :input").attr("disabled", true);
		removeDisabledClass('#step2-div');
		addDisabledClass('#step3-div');
	});
	$("#completeUpload :input").attr("disabled", true);
	addDisabledClass('#step3-div');
	$('#completeSubmit').click(function() {
		var callback=function(){
			alert('done');
		};
		var okToSubmit = LiveValidation.massValidate(agreeToLicenceValidator);
		if (okToSubmit) {
			ajaxJsonGet("upload/completeUpload", callback);
		}
	});
}

function addDisabledClass(component){
	$(component).addClass( "step-panel");
	var h3=component+" h3";
	$(h3).css("font-weight","normal");
	$(h3).css("color","red");
}

function removeDisabledClass(component){
	$(component).removeClass( "step-panel");
	var h3=component+" h3";
	$(h3).css("font-weight","bold");
	$(h3).css("color","black");
}

$(document).ready(function() {
	getCathegories();
	initMetaForm();
	initUploadForm();
	initCompleteForm();
	
});

_uacct = "UA-850242-2";
urchinTracker();