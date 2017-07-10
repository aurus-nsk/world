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
		<div class="container">
			1. Поиск Иннокентия
			<form id="search_inno_form" method="GET" modelAttribute="searchInnoForm">
			    	<div class="form-group">
		    			<label for="request">Поисковый запрос</label>
		    			<input id="request_id" name="request" type="text"  class="form-control inputfield"></input>
		  			</div>
		  			<button type="submit" class="btn btn-primary">Найти</button>
		  	</form>	    
	  	</div>
	  	
	  	2. Анализ городов, компании конкуренты
	  	3. Выбор пешеходной улицы
	  	
	  	
	  	<div id="table">
		</div>
	</div>
	<jsp:include page="/WEB-INF/footer.jsp"/>
	<SCRIPT type="text/javascript">
    	$( document ).ready(function() {
        	
    		$("#search_inno_form").submit(function(event) {
    			event.preventDefault();
    			submitViaAjax();
    		});

    		function submitViaAjax() {

    			$.ajax({
    				type : "POST",
    				contentType : "application/json;charset=UTF-8",
    				url : "/organizations/search",
    				data: $('#request_id').val(), 
    			    success: function(data) {
    			    	$('#table').html(data);
    			    },
    			    error: function(xhr) {
                        console.log(xhr);
                        $('#table').html(xhr);
                    }
    			});
    		}
    	});
	</SCRIPT>
</body>
</html>