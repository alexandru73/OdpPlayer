function Search() {
	this.searchTemplate = '<div id=result class="search-result ui-corner-all" onclick="goToPresentation(\'#ppt_name\')">\
				<img alt="Not Found" src="resources/repo/thumbnail/#pp_id/1.png" style="width:200px;height:150px;float:left">\
				<div id="result-detail" class="search-detail">\
					<p id="title" style="height:20px;" class="show-fixed-content"><b>#title</b></p>\
					<p class="show-fixed-content padding-s3"><i>Uploaded by: </i>&nbsp; #upBy </p>\
					<p class="show-fixed-content padding-s3"><i>Date added : </i>&nbsp; #upDate</p>\
					<p class="show-fixed-content padding-s3"><i>Views : </i>&nbsp; #views</p>\
					<p class="show-fixed-content padding-s3 " style="height:42px"><i>Description :</i>&nbsp; #descrpition </p>\
				</div>\
			</div>';
	this.currentPage = 1;
	this.min = false;
}

Search.prototype = {
	replaceShearch : function(presentation) {
		var response = "";
		if (presentation != null && presentation != ""
				&& presentation != "undefined") {
			var formatedDate = $.format.date(presentation.createadAt,
					"dd/MM/yyyy");
			response = this.searchTemplate.replace("#pp_id",presentation.repositoryName)
						.replace("#title",presentation.title)
						.replace("#upBy", presentation.user.name)
						.replace("#upDate", formatedDate)
						.replace("#views", presentation.noOfViews)
						.replace("#descrpition", presentation.description)
						.replace("#ppt_name",presentation.repositoryName);
		}
		return response;
	},

	search : function() {
		var sq = this.getResetParam("search-query");
		$("#errorMes").addClass("hidden-st");
		var callback = function(response, that) {
			if (response.success) {
				$(".search-result").remove();
				$("#search-input-pp").val(sq);
				$("#go-to-page").removeClass("hidden-st");
				$("#page-no").val(that.currentPage);
				that.populateWithResults(response.ppList,response.pageNo);
			} else {
				that.checkNext(response.pageNo);
				that.checkPrev();
				if(response.hasResults!=null && !response.hasResults){
					that.currentPage = 1;
					$(".search-result").remove();
					$("#errorMes").html(response.errorMessage);
					$("#errorMes").removeClass("hidden-st");
					$("#go-to-page").addClass("hidden-st");
				}
			}
		};
		
		var catId=$("#combobox").val();
		if(catId==-1){
			catId="";
		}
		var url = "presentation/search?searchQuery=" + sq + "&cathegory="
				+ catId + "&min=" + this.min + "&page="
				+ this.currentPage;
		ajaxJsonWithParamGet(url, this, callback);
	},

	populateWithResults : function(ppList,pageNo) {
		this.checkNext(pageNo);
		this.checkPrev();
		for ( var int = 0; int < ppList.length; int++) {
			var templ = this.replaceShearch(ppList[int]);
			$("#results").append(templ);
		}
	},

	getResetParam : function(paramName) {
		var param = getUrlParam(paramName);
		if (param == null || param == "undefined") {
			param = "";
		}
		return param;
	},

	goToPage : function(page) {
		if (isInt(page) && page > 0) {
			this.currentPage = page;
			this.search();
		}
		$("#page-no").val(this.currentPage)
	},

	checkPrev : function() {
		if (this.currentPage <= 1) {
			$("#prev-page").addClass("hidden-st");
		} else {
			$("#prev-page").removeClass("hidden-st");
		}
	},
	
	checkNext:function(pageNo){
		if (pageNo!=this.currentPage) {
			this.currentPage=pageNo;
			$("#page-no").val(pageNo);
			$("#next-page").addClass("hidden-st");
		} else {
			$("#next-page").removeClass("hidden-st");
		}
	},
	previous : function() {
		if (this.currentPage > 1) {
			this.currentPage--;
			this.search();
		}
	},

	next : function() {
		this.currentPage++;
		this.search();
	},
};

var docSh = new Search();

function goToPresentation(location){
	window.location.replace("presentation?p="+location);
}

function initUiIndex(){
	$("#combobox").change(function() {
		docSh.search();
	});
}

$(document).ready(function() {
	initUiIndex();
	getCathegories();
	docSh.search();
	$("#go").click(function() {
		docSh.goToPage($("#page-no").val());
	});
	$("#prev-page").click(function() {
		docSh.previous();
	});

	$("#next-page").click(function() {
		docSh.next();
	});
	
});



