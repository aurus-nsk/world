﻿<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML>
<html>
     
<head>
	<jsp:include page="/WEB-INF/header.jsp">
    	<jsp:param name="title" value="Поиск"/>
	</jsp:include>
</head>

<body>
	<div id="wrapper" class="wrapper">
		
	 	<div class="container">
	 	<h1>Поиск Иннокентия</h1>
			<form id="search_inno_form" method="GET" modelAttribute="searchInnoForm">
			    	<div class="form-group">
		    			<label for="request">Поисковый запрос</label>
		    			<input id="request_id" name="request" value="поесть на ленина" type="text"  class="form-control inputfield"></input>
		  			</div>
		  			<button type="submit" class="btn btn-primary">Найти</button>
		  	</form>	
		  	
		  	<table id="table" class="table">
		  <thead>
		    <tr>
		      <th>#</th>
		      <th>название</th>
		      <th>сфера деятельности</th>
		      <th>город</th>
		      <th>улица</th>
		      <th>ключевые слова</th>
		    </tr>
		  </thead>
		  <tbody>
		  </tbody>
		</table>    
	  	</div>
		
		<div id="message">
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
    				dataType: 'json',
    				data: $('#request_id').val(), 
    			    success: function(data) {
    			    	$('#table tbody > tr').remove();
    			    	$.each(data, function(i, data){
    			    	     $("#table tbody").append(
    			                     "<tr>"
    			                      +"<td>"+data.id+"</td>"
    			                      +"<td>"+data.name+"</td>"
    			                      +"<td>"+data.scope+"</td>"
    			                      +"<td>"+data.city.name+"</td>"
    			                      +"<td>"+data.street.name+"</td>"
    			                      +"<td>"+data.keyWords+"</td>"
    			                    +"</tr>" );
    			    	})
    			    },
    			    error: function(xhr) {
                        $('#message').html(xhr.responseText);
                    }
    			});
    		}
    	});
	</SCRIPT>
</body>
</html>