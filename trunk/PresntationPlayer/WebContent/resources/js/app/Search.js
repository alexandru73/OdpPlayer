function Search() {
	this.searchTemplate = '<div id="#ppt_name" class="search-result ui-corner-all" >\
				<img id="imgS" alt="Not Found" src="resources/repo/thumbnail/#ppt_name/1.png" style="width:200px;height:150px;float:left" onclick="goToPresentation(\'#ppt_name\')">\
				<div id="result-detail" class="search-detail" onclick="goToPresentation(\'#ppt_name\')">\
					<p id="title" style="height:20px;" class="show-fixed-content"><b>#title</b></p>\
					<p class="show-fixed-content padding-s3"><i>Uploaded by: </i>&nbsp; #upBy </p>\
					<p class="show-fixed-content padding-s3"><i>Date added : </i>&nbsp; #upDate</p>\
					<p class="show-fixed-content padding-s3"><i>Views : </i>&nbsp; #views</p>\
					<p class="show-fixed-content padding-s3"><i>Cathegory: </i>&nbsp; #cath</p>\
					<p class="show-fixed-content padding-s3 " style="height:22px;"><i>Description :</i>&nbsp; #descrpition </p>\
				</div>\
				#deleteHere\
			</div>';
	this.currentPage = 1;
	this.mine= false;
	this.slidesPerPage=10;
}

Search.prototype = {
	replaceShearch : function(result) {
		var response = "";
		if (result != null && result != ""
				&& result != "undefined") {
			var formatedDate = $.format.date(result.presentation.createadAt,"dd/MM/yyyy");
			response = this.searchTemplate.replace(/#ppt_name/g,result.presentation.repositoryName)
						.replace("#title",result.presentation.title)
						.replace("#upBy", result.presentation.user.name)
						.replace("#upDate", formatedDate)
						.replace("#views", result.presentation.noOfViews)
						.replace("#descrpition", result.presentation.description)
						.replace("#cath", result.presentation.cathegory.name);
			if(result.belongsToCurrentUser){
				var del='<button class="delete-presentation" style="width:20px;" id="deletePresentation" onclick="deletePresentation(\''+result.presentation.repositoryName+'\')">Delete presentation</button>';
				response=response.replace("#deleteHere", del);
			}else{
				response=response.replace("#deleteHere", "");
			}
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
		this.slidesPerPage=$("#resultsPerPage").val();
		this.mine=$("#myPresentations").is(':checked');
		var url = "presentation/search?searchQuery=" + sq + "&cathegory="
				+ catId + "&mine=" + this.mine + "&page="
				+ this.currentPage+"&slidesPerPage=" + this.slidesPerPage;
		ajaxJsonWithParamGet(url, this, callback);
	},

	populateWithResults : function(ppList,pageNo) {
		this.checkNext(pageNo);
		this.checkPrev();
		for ( var int = 0; int < ppList.length; int++) {
			var templ = this.replaceShearch(ppList[int]);
			$("#results").append(templ);
		}
		$( ".delete-presentation:button" ).button({
		    icons: {
		        primary: "ui-icon-trash"
		    },
		    text: false
		});
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
		$("#page-no").val(this.currentPage);
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
	
	$("#resultsPerPage").change(function() {

		docSh.search();
	});
	$("#myPresentations").change(function() {
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



