function deletePresentation(presentationName) {
	var callbackSuccess = function(response) {
		var element = $('#step0-div');
		element.empty();
		$('body').animate({
			scrollTop : $('body').position().top
		}, 'fast');

		setTimeout(function() {
			element.hide(700);
		}, 5000);
		if (response.success == true) {
			appendSuccessMessageDivTo(element, response, "message-success");
			$("#" + response.uniqueName).remove();
		} else {
			appendFailureMessageDivTo(element, response, "message-failure");
		}
		element.show(500);
	};
	var url = "presentation/" + presentationName;
	ajaxJsonDelete(url, callbackSuccess);
};

