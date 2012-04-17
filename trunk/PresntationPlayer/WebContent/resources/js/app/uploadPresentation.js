function submitMetaData() {
	var callBack = function(response) {
		alert("here");
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
		$("input:button,input:submit, button,#complete-link").button();
	});
};

initUploadStuff();

_uacct = "UA-850242-2";
urchinTracker();