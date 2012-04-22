<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tlds/template.tld' prefix='template'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Presentation Player</title>
<base href="${pageContext.request.contextPath}/" />
<template:get name="basicIncludes" />
<template:get name="extraIncludes" />
</head>
<body bgcolor="white" topmargin="0">
	<div id="htmlBody">
		<div id="header">
			<template:get name="header" />
		</div>
		<div style="widht:100%;height:2px;background-color: grey;">
		</div>
		<div id="mainPanel" class="mainPage">
			<template:get name="content" />
		</div>
		<div style="widht:100%;height:2px;background-color: grey;">
		</div>
		<div id="footer">
			<template:get name="footer" />
		</div>
	</div>

</body>
</html>