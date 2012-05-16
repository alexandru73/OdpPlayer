var player = new Player();

$(document).ready(function() {
	player.init();
});

function changeSlide(slideNo){
	player.changeFromOutSide(slideNo);
}

function playerActions() {
	$("#play").click(
			function() {
				player.play();
				var options = player.createToggleClickOptions(player.isPlaying,
						"Play", "ui-icon-play", "Pause", "ui-icon-pause");
				$(this).button("option", options);
			});

	$('#stop').click(function() {
		player.stop();
	});

	$("#fullscreen").click(
			function() {
				player.switchToFullscreen();
				var options = player.createToggleClickOptions(
						player.isFullscreen, "Switch to FullScreen",
						"ui-icon-arrow-4-diag", "Switch to window view",
						"ui-icon-newwin");
				$(this).button("option", options);
			});

	$("#next").click(function() {
		player.next();
	});

	$("#previous").click(function() {
		player.previous();
	});

	$("#first").click(function() {
		player.first();
	});

	$("#last").click(function() {
		player.last();
	});

	$("#ctrl-slide-input").keypress(function(event) {
		if (event.which == 13) {
			player.inputSlideNoReceived();
		}
	});

	$("#ctrl-slide-input").blur(function() {
		player.inputSlideNoReceived();
	});

	document.addEventListener("fullscreenchange", function() {
		player.onExitFullscreenEvent();
	}, false);

	document.addEventListener("mozfullscreenchange", function() {
		player.onExitFullscreenEvent();
	}, false);

	document.addEventListener("webkitfullscreenchange", function() {
		player.onExitFullscreenEvent();
	}, false);
	
	$( "#slider-range-min" ).bind( "slide", function(event, ui) {
		player.setCurrentSlide(ui.value);
		player.updateValues();
	});
};

$(function() {

	$("#tabs").tabs();

	$("#slider-range-min").slider({
		range : "min",
		value : 1,
		min : 1,
		max : 200,
		slide : function(event, ui) {
			$("#amount").val("$" + ui.value);
		}
	});
	
	$("#first").button({
		text : false,
		icons : {
			primary : "ui-icon-seek-start"
		}
	});

	$("#previous").button({
		text : false,
		icons : {
			primary : "ui-icon-seek-prev"
		}
	});

	$("#play").button({
		text : false,
		icons : {
			primary : "ui-icon-pause"
		}
	});

	$("#stop").button({
		text : false,
		icons : {
			primary : "ui-icon-stop"
		}
	});
	$("#next").button({
		text : false,
		icons : {
			primary : "ui-icon-seek-next"
		}
	});

	$("#last").button({
		text : false,
		icons : {
			primary : "ui-icon-seek-end"
		}
	});

	$("#fullscreen").button({
		text : false,
		icons : {
			primary : "ui-icon-arrow-4-diag"
		}
	});
	playerActions();
});
