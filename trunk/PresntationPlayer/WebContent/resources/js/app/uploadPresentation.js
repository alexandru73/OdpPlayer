

function deletePresentation() {
	var callbackSuccess = function(response) {
		alert(response);
	};
	var url = "presentation/d3b7c7d5-384a-483c-ad5a-b56b16878a9d";
	ajaxJsonDelete(url, callbackSuccess);
};


