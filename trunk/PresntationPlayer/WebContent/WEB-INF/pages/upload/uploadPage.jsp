<%@ taglib uri='/WEB-INF/tlds/template.tld' prefix='template'%>
<template:insert template='/WEB-INF/pages/template/template.jsp'>
	<template:put name='basicIncludes' content='/WEB-INF/pages/template/basicIncludes.jsp' />
	<template:put name='header' content='/WEB-INF/pages/template/header.jsp' />
	<template:put name='footer' content='/WEB-INF/pages/template/footer.jsp' />
	
	<template:put name='extraIncludes' content='/WEB-INF/pages/upload/uploadIncludes.jsp' />
	<template:put name='content' content='/WEB-INF/pages/upload/uploadContent.jsp' />
</template:insert>