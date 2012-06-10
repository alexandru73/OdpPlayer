function Player() {
	this.isPlaying = false;
	this.isFullscreen = false;
	this.outlineList = new Array();
	this.repoPath = "resources/repo/";
	this.presentation = 'un-D';
	this.currentSlide = 1;
	this.svgExt = ".svg";
	this.template = "<object id='svg-player' type='image/svg+xml' data='&content' >\
					</object>";
	this.downloadTemplate = "<div id='down-link' class='download-link'><a href='#link'>#linkLabel</a>#comment<div>";
	this.outlineTemplate = "<div onclick='changeSlide(#slide)' id='outline_#idNo' class='outline-link'>\
							<table>\
								<tr class='table-col'>\
									<td>#no</td>\
									<td valign='bottom'>#content</td>\
								</tr>\
							</table><div>";
	this.aspectRatio = 1.34;
};

Player.prototype = {
	init : function() {
		this.startCounter();
		var onLoadPresentationComplete = function(response, that) {
			if (response.success) {
				that.presentation = response.presentation;
				$("#total-slides").html(that.presentation.noOfSlides);
				$("#slider-range-min").slider("option", "max",
						that.presentation.noOfSlides);
				that.initUI();
				that.updateValues();
				that.initTime();
			}else{
				var parent=$("#playerSupport").parent();
				$("#playerSupport").remove();
				parent.prepend("<h2 style='text-align:center'>The presentation that you are looking for is no longer available !</h2>");
			}
		};
		var repoName = getUrlParam("p");
		ajaxJsonWithParamGet("presentation/watch?p=" + repoName, this,
				onLoadPresentationComplete);
		this.play();
	},

	initTime : function() {
		var date = new Date();
		date.setSeconds(date.getSeconds() + this.presentation.slideDuration);
		$('#hiddenTime').countdown('change', {
			until : date
		});
	},

	startCounter : function() {
		var date = null;
		var that = this;
		$("#hiddenTime").countdown({
			until : date,
			format : 'YOWDHMS',
			significant : 2,
			onExpiry : that.timeExpired,
			onTick : that.onTick
		});
	},

	onTick : function(periods) {
		$('#time-left').text("-" + periods[5] + ':' + periods[6]);
	},

	timeExpired : function() {
		if (player.currentSlide < player.presentation.noOfSlides) {
			player.currentSlide++;
			player.updateValues();
		}
	},

	play : function() {
		this.isPlaying = !this.isPlaying;
		if (this.isPlaying) {
			$('#hiddenTime').countdown('resume');
		} else {
			$('#hiddenTime').countdown('pause');
		}
	},

	stop : function() {
		this.isPlaying = false;
		if (this.currentSlide == 1) {
			this.initTime();
		} else {
			this.currentSlide = 1;
			this.updateValues();
		}
		this.uiChangesWhenStop();
		$('#hiddenTime').countdown('pause');
	},

	uiChangesWhenStop : function() {
		$("#play").button("option", {
			label : "Play",
			icons : {
				primary : "ui-icon-play"
			}
		});
	},

	next : function() {
		if (this.currentSlide < this.presentation.noOfSlides) {
			this.currentSlide++;
			this.updateValues();
		}
	},

	first : function() {
		if (this.currentSlide != 1) {
			this.currentSlide = 1;
			this.updateValues();
		}
	},

	last : function() {
		if (this.currentSlide != this.presentation.noOfSlides) {
			this.currentSlide = this.presentation.noOfSlides;
			this.updateValues();
		}
	},

	previous : function() {
		if (this.currentSlide > 1) {
			this.currentSlide--;
			this.updateValues();
		}
	},

	switchToFullscreen : function() {
		this.isFullscreen = !this.isFullscreen;
		if (this.isFullscreen) {
			if (window.fullScreenApi.supportsFullScreen) {
				var fsEl = document.getElementById('player');
				window.fullScreenApi.requestFullScreen(fsEl);
				this.setFullscreen();
			}
		} else {
			if (window.fullScreenApi.supportsFullScreen) {
				var fsEl = document.getElementById('player');
				window.fullScreenApi.cancelFullScreen(fsEl);
				this.onExitFullscreenEvent();
			}
		}
		$('button').removeClass('ui-state-hover');
	},

	onExitFullscreenEvent : function() {
		if (window.fullScreenApi.supportsFullScreen
				&& !window.fullScreenApi.isFullScreen()) {
			$('#player').addClass("fl-left");
			this.setPresetationPosition(690, 515, 100, 3, "black");
			$('#player').removeClass("black-backg");
			$('#toolbar').removeClass("alfa-white");
			var options = player.createToggleClickOptions(false,
					"Switch to FullScreen", "ui-icon-arrow-4-diag",
					"Switch to window view", "ui-icon-newwin");
			$("#fullscreen").button("option", options);
			this.isFullscreen = false;
			$('button').removeClass('ui-state-hover');
		}

	},

	setFullscreen : function() {
		var width = this.aspectRatio * screen.height;
		if (width > screen.width) {
			width = screen.width;
		}
		var left = Math.abs(width - screen.width) / 2;
		this.setPresetationPosition(width, screen.height, left, 0, "white");
		$('#player').addClass("black-backg");
		$('#player').removeClass("fl-left");
		$('#toolbar').addClass("alfa-white");
	},

	setPresetationPosition : function(width, height, left, padding, color) {
		$('#player').css("width", width);
		$('#playerImg').css("height", height - 30);
		$('#player').css("height", height);
		$('#player').css("left", left);
		$('#playerImg').css("padding", padding);
		$('#toolbar').css("padding", padding);
		$('#player').css("color", color);
	},

	inputSlideNoReceived : function() {
		var val = $("#ctrl-slide-input").val();
		if (isInt(val) && val <= this.presentation.noOfSlides && val > 0
				&& this.currentSlide != val) {
			this.currentSlide = val;
			this.updateValues();
		} else {
			$("#ctrl-slide-input").val(this.currentSlide);
		}
	},

	updateValues : function() {
		$("#slider-range-min").slider("option", "value", this.currentSlide);
		$("#ctrl-slide-input").val(this.currentSlide);
		this.changeSlide(this.currentSlide);
		this.initTime();

	},

	setCurrentSlide : function(slideNo) {
		this.currentSlide = slideNo;
	},

	changeSlide : function(currentSlide) {
		$('#playerImg').empty();
		var url = this.repoPath + "svg/" + this.presentation.repositoryName
				+ "/" + currentSlide + this.svgExt;
		var object = this.template.replace("&content", url);
		$('#playerImg').prepend(object);
		$('.outline-link-selected').removeClass('outline-link-selected');
		$("#outline_" + currentSlide).addClass("outline-link-selected");
		if (this.checkIfElemInView("outline_" + currentSlide, "tabs-outline")) {
			$("#tabs-outline").scrollTo("#outline_" + currentSlide);
		}
	},

	checkIfElemInView : function(elem, container) {
		var ok = true;
		var divHeight = $("#" + container).height();
		var positionInDiv = $("#" + elem).position().top;
		if ((Math.abs(positionInDiv) < divHeight) && positionInDiv >= 0) {
			ok = false;
		}
		return ok;
	},

	changeFromOutSide : function(slideNo) {
		this.currentSlide = slideNo;
		this.updateValues();
	},

	initUI : function() {
		// set presentation meta
		$("#pageTitle").html(this.presentation.title);
		$("#d-uploadedBy").html(this.presentation.user.name);
		var formatedDate = $.format.date(this.presentation.createadAt,
				"dd/MM/yyyy");
		$("#d-dateOfUpload").html(formatedDate);
		$("#d-noViews").html(this.presentation.noOfViews);
		$("#d-cathegory").html(this.presentation.cathegory.name);
		$("#d-description").html(this.presentation.description);
		// create download links
		var dLink = this.repoPath + "ext/" + this.presentation.repositoryName;
		var origFile = this.downloadTemplate.replace("#link",
				dLink + "." + this.presentation.originalExtension).replace(
				"#linkLabel", "Original File").replace("#comment",
				"&nbsp;(." + this.presentation.originalExtension + ")");
		var pdfFile = this.downloadTemplate.replace("#link", dLink + ".pdf")
				.replace("#linkLabel", "File as pdf").replace("#comment", "");
		$("#d-download").append(origFile).append(pdfFile);
		// create outline
		var bookmarks = this.presentation.bookmarks.split(",");
		for ( var int = 0; int < bookmarks.length; int++) {
			var index = int + 1;
			var cont = this.outlineTemplate.replace("#no", index + ".")
					.replace("#idNo", index)
					.replace("#content", bookmarks[int]).replace("#slide",
							index);
			$("#tabs-outline").append(cont);
		}
	},

	createToggleClickOptions : function(bolToggle, normalLabel, normalIcon,
			toggleLabel, toggleIcon) {
		var options;
		if (bolToggle) {
			options = {
				label : toggleLabel,
				icons : {
					primary : toggleIcon
				}
			};
		} else {
			options = {
				label : normalLabel,
				icons : {
					primary : normalIcon
				}
			};
		}
		return options;
	}
};