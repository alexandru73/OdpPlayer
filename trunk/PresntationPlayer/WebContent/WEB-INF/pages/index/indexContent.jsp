<%@taglib prefix="sp" uri="http://www.springframework.org/tags"%>
<div>
	<div id="cathegories" style="padding-right:10px;padding-top:10px;float:right">
		<span><b>Filter by cathegories : </b></span>
		<select  id="combobox" name="cathegory" class="text-input margin-5" style="height: 34px;width: 250px;"  >
			<option value="-1">All</option>
		</select>
	</div>
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
				<button id="go">GO</button>
			</div>
			<div id="next-prev" class="fl-right padd-top-10 page-nav text-a-right ">
				<button id="prev-page" class="button-cs">Previous Page</button>
				<button id="next-page" class="button-cs">Next Page</button>
			</div>
			<div class="clear-y"></div>
		</div>
	</div>
</div>