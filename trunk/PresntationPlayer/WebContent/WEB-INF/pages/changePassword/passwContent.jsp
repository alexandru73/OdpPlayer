<%@taglib prefix="sp" uri="http://www.springframework.org/tags"%>
<h2 class="center-align ">Change Password</h2>
<div class="separator-h2"></div>
<div  id="step0-div" class="form-content mainPage form-panel message-div-hidden">
</div>
<div  id="register-div" class="form-content grey-border form-panel ui-corner-all ">
	<form id="changePassword" >
		<table style="margin-top:15px">
			<tr>
				<td><label for="oldPassword">Old Password:</label></td>
				<td><input id="oldPassword" name="oldPassword" class="text-input margin-5" type="password" /></td>
			</tr>
			<tr>
				<td><label for="newPassword">New Password</label></td>
				<td><input id="newPassword" name="newPassword" class="text-input margin-5" type="password" /></td>
			</tr>
			<tr>
				<td><label for="retypedNewPassword"><sp:message code="user.retype.password" /></label></td>
				<td><input id="retypedNewPassword" name="retypedNewPassword" class="text-input margin-5" type="password" /></td>
			</tr>
			<tr>
				<td><input id="changeSubmit" type="button" class="button-cs" 
					value="Submit changes"></td>
			</tr>
		</table>
	</form>
</div>