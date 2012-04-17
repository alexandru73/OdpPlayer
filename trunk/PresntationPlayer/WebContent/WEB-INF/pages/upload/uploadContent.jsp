<%@taglib prefix="sp" uri="http://www.springframework.org/tags"%>
<div class="uploadContent">
	<br />
	<form id="metaForm" method="POST" action="upload/uploadMeta">
		<fieldset>
			<table>
				<tr>
					<td><label class="paragraph-style" for="title"><sp:message code="upload.form.title" /></label></td>
					<td><input id="title" name="title" type="text" /></td>
				</tr>
				<tr>
					<td><label class="paragraph-style" for="description"><sp:message code="upload.form.description" /></label></td>
					<td><input id="description" name="description" type="text" /></td>
				</tr>
				<tr>
					<td><label class="paragraph-style" for="slideDuration"><sp:message code="upload.form.slide.duration" /></label></td>
					<td><input id="slideDuration" name="slideDuration" type="text" /></td>
				</tr>
				<tr>
					<td><label class="paragraph-style" for="category"><sp:message code="upload.form.category" /></label></td>
					<td><input id="cathegory" name="cathegory" type="text" /></td>
				</tr>
				<tr>
					<td><input type="button" class="button-cs" onclick="submitMetaData()" value="<sp:message code="upload.form.next" />"></td>
				</tr>
			</table>
		</fieldset>
	</form>
	<form id="fileUploadForm" method="POST" enctype="multipart/form-data" action="upload/uploadFile">
		<label for="fileData"><sp:message code="upload.form.upload.file" /></label> 
		<input id="fileData"name="fileData" type="file">
		<div id="progressbar" style="height: 10px; width: 155px"></div>
		<input id="submitBut" class="button-cs"  type="submit" value="<sp:message code="upload.form.previous"/>">
		<input id="submitBut" class="button-cs" type="submit" value="<sp:message code="upload.form.next"/>">
	</form>
	<a id="complete-link" href="upload/completeUpload"><sp:message code="upload.form.complete"/></a>
	<a id="complete-link" href="javascript:deletePresentation()"><sp:message code="upload.form.delete"/></a>
	<script type="text/javascript">
		ajaxUploadReady();
	</script>
</div>