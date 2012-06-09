<%@taglib prefix="sp" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div  id="step0-div" class="form-content mainPage form-panel message-div-hidden">
</div>
<div>
	<div id="cathegories" style="margin-right:10px;margin-top:10px;float:right">
		<span><b>Filter by cathegories : </b></span>
		<select  id="combobox" name="cathegory" class="text-input margin-5" style="height: 34px;width: 250px;"  >
			<option value="-1">All</option>
		</select>
	</div>
	<div style="margin:10px 20px 0px 10px;float:right">
		<span><b>Results per page: </b></span>
		<select  id="resultsPerPage" name="resultsPerPage" class="text-input margin-5" style="height: 34px;width: 50px;"  >
			<option>10</option>
			<option>20</option>
			<option>30</option> 
		</select>
	</div>
	<sec:authorize access="isAuthenticated()">
		<div style="margin: 16px 20px 0px 0px; float: right;">
			<span><b>My presentations only : </b></span>
		    <input id="myPresentations" type="checkbox">
		</div>
	</sec:authorize>
	<div id="cathegories" style="clear:both"></div>
	<div class="form-content mainPage form-panel">
		<div id="pag-controls">
			<div id="results" style="width: 100%; height: 100%;">
				<h2 class="form-content">Latest presentations</h2>
				<span id="errorMes" class="hidden-st"></span>
			</div> 
			<div class="clear-y"></div>
			<div id="go-to-page" class="fl-left padd-top-10 page-nav text-a-left">
				<span>Go to page :</span> 
				<input id="page-no" style="padding: 4px; width: 30px; text-align: center; height: 15px; border: none;"
					class="ui-corner-all">
				 of 
				<span id="of"></span> 
				<button id="go">GO</button>
			</div>
			<div id="next-prev" class="fl-right padd-top-10 page-nav text-a-right hidden-st ">
				<button id="prev-page" class="button-cs">Previous Page</button>
				<button id="next-page" class="button-cs">Next Page</button>
			</div>
			<div class="clear-y"></div>
		</div>
	</div>
</div>