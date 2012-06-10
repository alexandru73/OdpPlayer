<%@taglib prefix="sp" uri="http://www.springframework.org/tags"%>
<h2 class="center-align "><sp:message code="menu.register"/></h2>
<div class="separator-h2"></div>
<div  id="step0-div" class="form-content mainPage form-panel message-div-hidden">
</div>
<div  id="register-div" class="form-content grey-border form-panel ui-corner-all ">
	<h3 ><sp:message code="title.register.form"/></h3>
	<form id="register" >
		<table>
			<tr> 
				<td><label for="username"><sp:message code="user.username" /></label></td>
				<td><input id="username" name="username" class="text-input margin-5" type="text" /></td>
			</tr>
			<tr>
				<td><label for="password"><sp:message code="user.password" /></label></td>
				<td><input id="password" name="password" class="text-input margin-5" type="password" /></td>
			</tr>
			<tr>
				<td><label for="retype-password"><sp:message code="user.retype.password" /></label></td>
				<td><input id="retype-password" name="retype-password" class="text-input margin-5" type="password" /></td>
			</tr>
			<tr>
				<td><label for="name"><sp:message code="user.name" /></label></td>
				<td><input id="name" name="name" class="text-input margin-5" type="text" /></td>
			</tr>
			<tr>
				<td><label for="email"><sp:message code="user.email" /></label></td>
				<td><input id="email" name="email" class="text-input margin-5" type="text" /></td>
			</tr>
			<tr>
				<td><input id="registerSubmit" type="button" class="button-cs" 
					value="<sp:message code="menu.register" />"></td>
			</tr>
		</table>
	</form>
</div>