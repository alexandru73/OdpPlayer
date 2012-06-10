<%@taglib prefix="sp" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div id="templatemo_menu" class="fl-left">
	<ul>
		<sec:authorize access="isAuthenticated()">
			<li><a href="search">Latest presentations</a></li>
			<li><a href="upload"><sp:message code="menu.upload" /></a></li>
		</sec:authorize>
		<sec:authorize access="! isAuthenticated()">
			<li><a href="search">List presentations</a></li> 
			<li><a href="login"><sp:message code="menu.login" /></a></li>
			<li><a href="register"><sp:message code="menu.register" /></a></li>
		</sec:authorize>
	</ul>
</div>
<div id="menu-container" class="fl-left" style="margin-top: 10px;">
	<input id="search-input-pp" type="text" class="text-input"> <input type="button"
		id="search-in-pp" class="search-but" value="Search"	>
</div>
<sec:authorize access="isAuthenticated()">
<div id="menu-user-actions" class="round-bottom-corners">  
	<div id="menu-info" class="menu-style-div ">  
			<ul id="menua" class="menu">
				<li><div class="show-fixed-content fl-left" style="width: 100%; height: 32px">Welcome :&nbsp;<a style="color:#0395CC;">${sessionScope.name}</a></div>
					<ul id="account" class="ui-round-bottom-corners fl-left" style="border-top: none">
						<li><a href="changePassword"><sp:message code="menu.change.password" /></a></li>
						<li><a href="j_spring_security_logout"><sp:message code="menu.logout"/></a></li>
					</ul>
				</li>
			</ul>
	</div>
</div>
</sec:authorize>
