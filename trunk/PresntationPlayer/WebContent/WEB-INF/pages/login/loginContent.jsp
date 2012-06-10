<%@taglib prefix="sp" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2 class="center-align ">Log in</h2>
<div class="separator-h2"></div> 
<div id="login"> 
	<br />
	<form id="login-form" method="POST" action="j_spring_security_check" >
			<c:if test="${ not (empty param.error) and param.error==1}">
				<div id="login-error">
        				<sp:message code="menu.authentication.failed" />
        		</div>
    		</c:if>

			<table id="login-table">
				<tr>
					<td><label  for="j_username"><sp:message code="user.username" /></label></td>
				</tr>
				<tr>
					<td><input id="j_username" name="j_username" class="text-input" type="text" /></td>
				</tr>
				<tr>
					<td><label for="j_password"><sp:message code="user.password" /></label></td>
				</tr>
				<tr>
					<td><input id="j_password" name="j_password" class="text-input" type="password" /></td>
				</tr>

				<tr>
					<td><input type="submit" class="search-but" style="margin-top:10px;float:right;" value="<sp:message code="menu.login" />"></td>
				</tr>
			</table>
	</form>
</div>