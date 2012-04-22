<%@taglib prefix="sp" uri="http://www.springframework.org/tags"%>
<h2 class="center-align "><sp:message code="title.upload.presentation"/></h2>
<div  id="step1-div" class="form-content mainPage form-panel">
	<h3><sp:message  code="upload.form.step1"/></h3>
	<form id="metaForm" >
		<table>
			<tr>
				<td><label for="title"><sp:message code="upload.form.title" /></label></td>
				<td><input id="title" name="title" class="text-input margin-5" type="text" /></td>
			</tr>
			<tr>
				<td><label for="description"><sp:message code="upload.form.description" /></label></td>
				<td><textarea rows="4" cols="36" id="description" name="description"
						class="text-input margin-5" style="height:60px"></textarea>&nbsp;</td>
			</tr>
			<tr>
				<td><label for="slideDuration"><sp:message code="upload.form.slide.duration" /></label></td>
				<td><input id="slideDuration" name="slideDuration" class="text-input margin-5" type="text" /></td>
			</tr>
			<tr>
				<td><label for="category"><sp:message code="upload.form.category" /></label></td>
				<td><select  id="combobox" name="cathegory"  ><option></option></select>
			</tr>
			<tr>
				<td><input id="metaSubmit" type="button" class="button-cs" 
					value="<sp:message code="upload.form.next" />"></td>
			</tr>
		</table>
	</form>
</div>
<div id="step2-div" class="form-content mainPage form-panel">
	<h3><sp:message code="upload.form.step2"/></h3>
	<form id="fileUploadForm" method="POST" enctype="multipart/form-data" action="upload/uploadFile">
	<table>
		<tr>
			<td><label for="fileData"><sp:message code="upload.form.upload.file" /></label></td>
			<td><input id="fileData"name="fileData" type="file"	></td>
		</tr>
		<tr>
			<td/>
			<td><div id="progressbar" style="height: 20px; width: 155px"></div></td>
		</tr>
		<tr>
			<td><input id="previousStep1" class="button-cs" type="button"
				value="<sp:message code="upload.form.previous"/>"></td>
		 	<td><input id="submitUpload"  class="button-cs" type="submit"
				value="<sp:message code="upload.form.next"/>"></td>
		</tr>
	</table>
	</form>
</div>
<div id="step3-div" class="form-content mainPage form-panel">
	<h3><sp:message code="upload.form.step3"/></h3>
	<form id="completeUpload">
		<table>
			<tr>
				<td><label for="agreeToLicence"><sp:message code="upload.form.agree.to.licence" /></label></td>
				<td><input id="agreeToLicence" name="agreeToLicence" type="checkbox" /></td>
			</tr>
			<tr>
				<td><input id="previousStep2" class="button-cs" type="button"
				value="<sp:message code="upload.form.previous"/>">
			 	<input id="completeSubmit" class="button-cs" type="button"
						value="<sp:message code="upload.form.complete"/>"></td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	ajaxUploadReady();
</script>
