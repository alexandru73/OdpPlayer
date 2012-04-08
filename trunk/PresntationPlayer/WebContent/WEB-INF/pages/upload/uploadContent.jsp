<%@taglib prefix="sp" uri="http://www.springframework.org/tags"%>

<div class="uploadContent">
	<button class="uiButton">0A button element</button>
	<br />
	<form id="metaForm" method="POST" action="upload/uploadEx">
		<fieldset>
			<table>
				<tr>
					<td><label for="title"><sp:message code="upload.form.title" /></label></td>
					<td><input id="title" name="title" type="text" /></td>
				</tr>
				<tr>
					<td><label for="description"><sp:message code="upload.form.description" /></label></td>
					<td><input id="description" name="description" type="text" /></td>
				</tr>
				<tr>
					<td><label for="category"><sp:message code="upload.form.category" /></label></td>
					<td><input id="category" name="category" type="text" /></td>
				</tr>
				<tr>
					<td><input class="uiButton" id="submitBut" type="submit"></td>
				</tr>
			</table>
		</fieldset>
	</form>
	<form method="post" enctype="multipart/form-data" action="upload/upload2">
		<label for="fileData"><sp:message code="upload.form.upload.file" /></label>
		 <input id="fileData"name="fileData" type="file">
		<div id="progressbar" style="height: 10px; width: 155px"></div>
		<input id="submitBut" type="submit">
	</form>
	<form action=":"></form>
	<div id="playerImg" style="width: 500px; height: 500px">
		<object type="image/svg+xml" style="border: none; width: 100%; height: 100%; background: white;"
			data="resources/repo/alex.svg"
		></object>
	</div>
</div>