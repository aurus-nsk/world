<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML>
<html>
     
<head>
	<jsp:include page="/WEB-INF/header.jsp">
    	<jsp:param name="title" value="World"/>
	</jsp:include>
</head>

<body>

	<div id="wrapper" class="wrapper">
	    2GIS World 
	</div>
	
	<jsp:include page="/WEB-INF/footer.jsp"/>

</body>
</html>