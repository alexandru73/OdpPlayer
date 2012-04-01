<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="sp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><sp:message code="general.PageTitle" /></title>
<base>
<link type="text/css" href="${pageContext.request.contextPath}/resources/css?name=jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js?name=jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js?name=jquery-ui-1.8.18.custom.min.js"></script>
<style>
	.ui-progressbar-value { background-image: url(images/pbar-ani.gif); }
</style>
	<script>
	$(function() {
		$("#progressbar").progressbar({
			value : 20
		});
	});
	</script>
</head>
<body>
	<form method="post" enctype="multipart/form-data" action="upload2">
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
				<div id="progressbar" style="height:20px;width:30px"></div></td>
			</tr>	
			<tr>
				<td><input id="submitBut" type="submit"></td>
			</tr>
			</table>
		</fieldset>
		
	</form>
</body>
</html>