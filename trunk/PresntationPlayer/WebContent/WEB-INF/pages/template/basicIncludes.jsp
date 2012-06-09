<link type="text/css" href="resources/css/trontastic/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<link type="text/css" href="resources/css/local/layout.css" rel="stylesheet" />
<link type="text/css" href="resources/css/local/header.css" rel="stylesheet" />
<script type="text/javascript" src="resources/js/lib/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="resources/js/lib/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript">
	function searchInDB() {
		var paramVal = $("#search-input-pp").val();
		if (paramVal != null) {
			ok = false;
			for ( var i = 0; i < paramVal.length; i++) {
				if (paramVal[i] != " ") {
					ok = true;
				}
			}
			if (ok) {
				window.location = "search?search-query=" + paramVal;
			}else{
				$("#search-input-pp").val("");
			}
		}
	}

	$(function() {
		jQuery("input:submit,input:button,button ").button();
	});
	$(document).ready(function() {
		$("#search-in-pp").click(function() {
			searchInDB();
		});

		$("#search-input-pp").keypress(function(event) {
			if (event.which == 13) {
				searchInDB();
			}
		});

	});
</script>
