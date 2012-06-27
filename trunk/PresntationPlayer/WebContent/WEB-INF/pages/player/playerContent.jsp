<%@taglib prefix="sp" uri="http://www.springframework.org/tags"%>
<div id="playerSupport" style="text-align: center;display: block" >
	<h2><span id="pageTitle"></span></h2>
	<div class="separator-h2"></div>
	<div id="player" class="fl-left player-margin-left">
		<div id="playerImg"	class="transparent-background ui-corner-top" >
			<object id="svg-player" type="image/svg+xml" >
			</object> 
		</div>
		<div id="toolbar" class="transparent-background ui-corner-bottom">
			
			<div id="controls" class="controls">
				<button id="fullscreen"><sp:message code="player.fullscreen" /></button>
				<button id="first"><sp:message code="player.first" /></button>
				<button id="previous"><sp:message code="player.previous" /></button>
				<button id="play"><sp:message code="player.pause" /></button>
				<button id="stop"><sp:message code="player.stop" /></button>
				<button id="next"><sp:message code="player.next" /></button>
				<button id="last"><sp:message code="player.last" /></button>
			</div>
			<div class="controls-slide">
				<span><sp:message code="player.slide" /></span>
					&nbsp;:&nbsp;
					<input id="ctrl-slide-input" type="text" class="ui-corner-all" >
					&nbsp;/&nbsp;
					<span id="total-slides">1</span>
			</div>
			<div id="slider-range-min"></div>
			<div class="time-slides">
				<span id="left" class="time-left "><sp:message code="player.left" />:</span>
				<span id="hiddenTime" style="display:none"></span>
				<span id="time-left" class="time-left" ></span>
			</div>
			
		</div>
	</div>
	
	<div  id="tabs">
		<ul>
			<li><a href="#tabs-outline"><sp:message code="player.outline" /></a></li>
		</ul>
		<div id="tabs-outline" class="inner-tabs" style="padding:8px;position: relative;">
		</div>
	</div>
 
	<div class="player-details">
		<div class="owner-views">
			<div class="small-detail left-pointer ">
				<span style="font-weight:bold">Uploaded by:</span>&nbsp;<span id="d-uploadedBy"></span>&nbsp; at &nbsp;<span
					id="d-dateOfUpload"></span>
			</div>
			<div class="small-detail right-pointer">
				<span style="font-weight:bold">Views:</span>&nbsp;<span id="d-noViews"></span>
			</div>
		</div>

		<div id="d-download" class=" grey-border ui-corner-all  form-panel" style="float: right; width: 249px; text-align: left;padding:3px">
			<span style="font-weight:bold">Download :</span>&nbsp;
		</div>

		<div class="small-detail left-pointer ">
			<span style="font-weight:bold">Cathegory:</span>&nbsp;<span id="d-cathegory"></span>
		</div>
		<div class="grey-border ui-corner-all d-description form-panel" >
			<span style="font-weight:bold">Description:</span>&nbsp;<br><span id="d-description" style="padding:2px"></span></div>
	</div>
	<div style="clear:both"></div>
</div>