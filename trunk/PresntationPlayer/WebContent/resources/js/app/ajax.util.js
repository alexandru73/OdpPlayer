$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

function ajaxJsonPost(url, objData, that, callbackSuccess,failureCallback) {
	$.ajax({
		url : url,
		type:'POST',
		dataType : 'json',
		contentType: "application/json; charset=utf-8",
		data : JSON.stringify(objData),
		success : callbackSuccess
	});
};

function ajaxJsonGet(url, objData, that, callbackSuccess) {
	$.ajax({
		url : url,
		type:'GET',
		dataType : 'json',
		contentType: "application/json; charset=utf-8",
		data : JSON.stringify(objData),
		success : callbackSuccess
	});
};
