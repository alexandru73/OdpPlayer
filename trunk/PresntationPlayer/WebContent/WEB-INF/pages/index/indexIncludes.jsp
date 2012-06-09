<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="resources/js/app/ajax.util.js"></script>
<script type="text/javascript" src="resources/js/app/Utils.js"></script>
<script type="text/javascript" src="resources/js/lib/jquery.dateFormat-1.0.js"></script>
<script type="text/javascript" src="resources/js/app/Search.js"></script>
<sec:authorize access="isAuthenticated()">
<script type="text/javascript" src="resources/js/app/DeleteP.js"></script>
</sec:authorize>