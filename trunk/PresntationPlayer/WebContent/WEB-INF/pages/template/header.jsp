<%@taglib prefix="sp" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div id="logo">
	<img src="resources/css/local/img/logo.png" style="width: 120px; height: 45px;" alt="">
</div>
<div id="menu-container" style="margin-top: 10px;">
	<form>
		<input type="text" class="text-input" > <input type="button" class="search-but"
			value="Search"
		>
	</form>
</div>
<div id="menu-user-actions" class="round-bottom-corners">
	<div id="menu-info" class="menu-style-div">
		<sec:authorize access="isAuthenticated()">
			<ul id="menua" class="menu">
				<li>Welcome :<br> <a href="">${sessionScope.name}</a>
					<ul id="account" class="round-bottom-corners" style="border-top: none">
						<li><a href="upload"><sp:message code="menu.upload"/></a></li>
						<li><a href="changePassword"><sp:message code="menu.change.password"/></a></li>
						<li><a href="j_spring_security_logout"><sp:message code="menu.logout"/></a></li>
					</ul>
				</li>
			</ul>
		</sec:authorize>
		<sec:authorize access="! isAuthenticated()">
			<ul id="menu" class="menu">
				<li><a href="login"><sp:message code="menu.login"/></a>&nbsp;/&nbsp;
				<a href="register"><sp:message code="menu.register"/></a></li>
			</ul>
		</sec:authorize>
	</div>
</div>