function fnGetSelected(oTableLocal) {
	return oTableLocal.$('tr.row-selected');
}

function getSelectedID(oTableLocal) {
	return oTableLocal.$('tr.row-selected').attr("id");
}

function addMessage(response) {
	var id = "result-register";
	var element = $('#step0-div');
	if (response.success == true) {
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
}
function deleteWhereID(id) {
	var callbackSuccess = function(response) {
		addMessage(response);
	};
	ajaxJsonDelete("user/" + id, callbackSuccess);
}

function resetPasswordFor(id) {
	var callbackSuccess = function(response) {
		addMessage(response);
	};
	ajaxJsonPut("user/" + id, callbackSuccess);
}

var dataTable;

$(document).ready(function() {
	dataTable = $('#userTable').dataTable({
		"sPaginationType" : "full_numbers",
		"sAjaxSource" : 'user/allUsers',
		"bProcessing" : true,
		"fnCreatedRow" : function(nRow, aData, iDataIndex) {
			// Bold the grade for all 'A' grade browsers
			nRow.onclick = function() {
				if ($(this).hasClass('row-selected')) {
					$(this).removeClass('row-selected');
					$('#user-act').addClass('hidden-st');
				} else {
					dataTable.$('tr.row-selected').removeClass('row-selected');
					$(this).addClass('row-selected');
					$('#user-act').removeClass('hidden-st');
				}
			};
		}
	});

	$("#deleteRow").click(function() {
		var anSelected = fnGetSelected(dataTable);
		if (anSelected.length !== 0) {
			$('#dialog-confirm').dialog('open');
		}
	});

	$("#resetPassw").click(function() {
		var anSelected = fnGetSelected(dataTable);
		if (anSelected.length !== 0) {
			$('#dialog-reset').dialog('open');
		}
	});
});

$(function() {
	$("#dialog-confirm").dialog({
		resizable : false,
		height : 150,
		modal : true,
		autoOpen : false,
		show : {
			effect : 'drop',
			direction : "up"
		},
		buttons : {
			"Delete item" : function() {
				var anSelected = fnGetSelected(dataTable);
				if (anSelected.length !== 0) {
					deleteWhereID(getSelectedID(dataTable));
					dataTable.fnDeleteRow(anSelected[0]);
				}
				$(this).dialog("close");
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});

	$("#dialog-reset").dialog({
		resizable : false,
		height : 150,
		modal : true,
		autoOpen : false,
		show : {
			effect : 'drop',
			direction : "up"
		},
		buttons : {
			"Reset password" : function() {
				var anSelected = fnGetSelected(dataTable);
				if (anSelected.length !== 0) {
					resetPasswordFor(getSelectedID(dataTable));
				}
				$(this).dialog("close");
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});
});