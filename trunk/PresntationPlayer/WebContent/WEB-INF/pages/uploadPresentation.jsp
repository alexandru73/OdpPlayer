<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="sp" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><sp:message code="general.PageTitle" /></title>
<base href="${pageContext.request.contextPath}/" /> 
<link type="text/css" href="resources/css/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script type="text/javascript" src="resources/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="resources/js/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="resources/js/urchin.js"></script>
<script type="text/javascript" src="resources/js/jquery.form.js"></script>
	<script>
	jQuery(function() {
		jQuery("#progressbar").progressbar({
			value : 21
		});
	});
	</script>

</head>
<body>
<script>
(function() {
    
var bar = $('#progressbar');
$('form').ajaxForm({
    beforeSend: function() {
    	jQuery("#progressbar").progressbar("value",0);
    },
    uploadProgress: function(event, position, total, percentComplete) {
    	jQuery("#progressbar").progressbar("value",percentComplete);
    },
	complete: function(xhr) {
	}
}); 

})();       
</script>
	<form method="post" enctype="multipart/form-data" action="upload/upload2">
		<fieldset>
			<table>
			<tr>
				<td><label for="title"><sp:message code="upload.form.title" /></label></td>
				<td><input id="title" name="title" type="text"/> </td>
			</tr>
			<tr>
				<td><label for="description"><sp:message code="upload.form.description" /></label></td>
				<td><input id="description" name="description" type="text"/> </td>
			</tr>
			<tr>
				<td><label for="category"><sp:message code="upload.form.category" /></label></td>
				<td><input id="category" name="category" type="text"/></td>
			</tr>	 
			<tr>
				<td><label for="fileData"><sp:message code="upload.form.upload.file" /></label></td>
				<td><input id="fileData" name="fileData" type="file"> 
				<div id="progressbar" style="height:10px;width:155px"/></td>
			</tr>	
			<tr>
				<td><input id="submitBut" type="submit"></td>
			</tr>
			</table>
		</fieldset>
	
	</form>
	<form action=":"></form>
	<div id="playerImg" style="width:500px;height:500px">
	<object type="image/svg+xml" style="border:none; width:100%; height:100%; background: white;" 
		data="resources/repo/alex.svg"></object>
		</div>
<script type="text/javascript">
_uacct = "UA-850242-2";
urchinTracker();
</script>
</body>
</html>