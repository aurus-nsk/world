<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
	 	<h1>Анализ городов, конкурирующие организации</h1>
			<form id="analyze_form" method="GET" modelAttribute="analyzeForm">
			    	<div class="form-group">
		    			<label for="scope">Сфера деятельности организации</label>
		    			<input id="scope_id" name="scope" type="text" value="Ресторан" class="form-control inputfield"></input>
		  			</div>
		  			<button type="submit" class="btn btn-primary">Найти конкурентов</button>
		  	</form>	 
		  	<table id="table" class="table">
		  <thead>
		    <tr>
		      <th>город</th>
		      <th>сфера деятельности</th>
		      <th>количество организаций</th>
		      <th>население города</th>
		      <th>кол-во организаций/население</th>
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
        	
    		$("#analyze_form").submit(function(event) {
    			event.preventDefault();
    			submitViaAjax();
    		});

    		function submitViaAjax() {
    			$.ajax({
    				type : "POST",
    				contentType : "application/json;charset=UTF-8",
    				url : "/organizations/analyze",
    				dataType: 'json',
    				data: $('#scope_id').val(), 
    			    success: function(data) {
    			    	$('#table tbody > tr').remove();
    			    	$.each(data, function(i, data){
    			    	     $("#table tbody").append(
    			                     "<tr>"
    			                      +"<td>"+data.cityName+"</td>"
    			                      +"<td>"+data.scope+"</td>"
    			                      +"<td>"+data.quantity+"</td>"
    			                      +"<td>"+data.cityPopulation+"</td>"
    			                      +"<td>"+Number(data.quantity)/Number(data.cityPopulation)+"</td>"
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