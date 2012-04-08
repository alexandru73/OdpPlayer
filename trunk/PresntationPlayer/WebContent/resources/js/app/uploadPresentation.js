function clickButton() {
	var callBack = function(response) {
		alert("here");
	};
	var obj = new UploadMetadata("titlu", "descrp", "cath");
	ajaxJsonPost("upload/uploadEx", obj, this, callBack);
};

function ajaxUploadReady() {
	var progressBar = $('#progressbar');
	$('form').ajaxForm({
		beforeSend : function() {
			progressBar.progressbar("value", 0);
		},
		uploadProgress : function(event, position, total, percentComplete) {
			progressBar.progressbar("value", percentComplete);
		},
		complete : function(xhr) {
		}
	});
};

function initProgressBar() {
	$(function() {
		$("#progressbar").progressbar({
			value : 56
		});
	});
};
initProgressBar();