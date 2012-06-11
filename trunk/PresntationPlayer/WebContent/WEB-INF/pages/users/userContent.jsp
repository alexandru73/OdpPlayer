<%@taglib prefix="sp" uri="http://www.springframework.org/tags"%>
<h2 class="center-align ">
	All Users
</h2>
<div class="separator-h2"></div>
<div  id="step0-div" class="form-content mainPage form-panel message-div-hidden">
</div>
<div id="step0-div" class="form-content mainPage form-panel message-div-hidden"></div>
<div id="register-div" class="form-content grey-border form-panel ui-corner-all ">
	<div id="user-act" class="user-actions hidden-st">
		<a id="deleteRow" class="paginate_button">Delete User</a>
		<a id="resetPassw" class="paginate_button">Reset User Password</a>
	</div>
	<table id="userTable" style="margin-top: 15px;">
		<thead>
			<tr>
				<th>Username</th>
				<th>Full Name</th>
				<th>Email</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<div id="dialog-confirm" title="Empty the recycle bin?">
		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
			<span id="messageText">Are you sure you want to delete the selected user ?</span>
		</p>
	</div>
	<div id="dialog-reset" title="Empty the recycle bin?">
		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		<span id="messageText">Are you sure you want to reset the password of the selected user?</span></p>
	</div>
</div>