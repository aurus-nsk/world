<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML>
<html>
     
<head>
	<jsp:include page="/WEB-INF/header.jsp">
    	<jsp:param name="title" value="Пешеходная улица"/>
	</jsp:include>
</head>

<body>
	<div id="wrapper" class="wrapper">
	 	<div class="container">
	 		<h1>Пешеходная улица</h1>
	 		<form id="pedestrian_form" method="GET" modelAttribute="pedestrianForm">
		    	
		    	<div class="form-group">
	    			<label for="from">Протяженность не менее(м)</label>
	    			<input id="from_id" name="from" type="text" value="500" class="form-control inputfield"></input>
	  			</div>
		   		<div class="form-group">
	    			<label for="to">Протяженность не более(м)</label>
					<input id="to_id" name="to" type="text" value="1000" class="form-control inputfield"></input>
				</div>
				<div class="form-group">
	    			<label for="city">Город</label>
					<input id="city_id" name="city" type="text" value="Новосибирск" class="form-control inputfield"></input>
				</div>
				<div class="form-group">
	    			<label for="scope">Тип организаций на улице</label>
					<input id="scope_id" name="scope" type="text" value="Ресторан Банк" class="form-control inputfield"></input>
				</div>
				<button type="submit" class="btn btn-primary">Найти</button>
		    </form>
		    
		    <table id="table" class="table">
		  <thead>
		    <tr>
		      <th>Название</th>
		      <th>Протяженность</th>
		      <th>Организации</th>
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
    	
		$("#pedestrian_form").submit(function(event) {
			event.preventDefault();
			submitViaAjax();
		});

		function submitViaAjax() {
			var param = {};
			param["from"] = $("#from_id").val();
			param["to"] = $("#to_id").val();
			param["city"] = $("#city_id").val();
			param["scope"] = $("#scope_id").val();
			
			$.ajax({
				type : "GET",
				contentType : "application/json;charset=UTF-8",
				cache: false,
				dataType: 'json',
				url : "/streets/findstreet",
				data : param,
			    success: function(data) {
			    	$('#table tbody > tr').remove();
			    	$.each(data, function(i, data){
			    	     $("#table tbody").append(
			                     "<tr>"
			                      +"<td>"+data.name+"</td>"
			                      +"<td>"+data.extent+"</td>"
			                      +"<td>"+data.orgs+"</td>"
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