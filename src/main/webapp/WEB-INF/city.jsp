<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML>
<html>
     
<head>
	<jsp:include page="/WEB-INF/header.jsp">
    	<jsp:param name="title" value="Город"/>
	</jsp:include>
</head>

<body>

	<div id="wrapper" class="wrapper">
		<div class="container">
		    Добавить город:
		    <form id="city_add_form" method="POST" modelAttribute="cityForm">
		    	
		    	<div class="form-group">
	    			<label for="name">Название</label>
	    			<input id="name_id" name="name" type="text"  class="form-control inputfield"></input>
	  			</div>
		   		<div class="form-group">
	    			<label for="square">Площадь(км2)</label>
					<input id="square_id" name="square" type="text" class="form-control inputfield"></input>
				</div>
				<div class="form-group">
	    			<label for="population">Население</label>
					<input id="population_id" name="population" type="text" class="form-control inputfield"></input>
				</div>
				
				<button type="submit" class="btn btn-primary">Добавить</button>
		    </form>
		 </div>
		 
		<div id="table">
		</div>
		
	</div>
	
	<jsp:include page="/WEB-INF/footer.jsp"/>
	
	<SCRIPT type="text/javascript">
    	$( document ).ready(function() {
        	
    		$("#city_add_form").submit(function(event) {
    			event.preventDefault();
    			submitViaAjax();
    		});

    		function submitViaAjax() {
				var city = {};
    			city["name"] = $("#name_id").val();
    			city["square"] = $("#square_id").val();
    			city["population"] = $("#population_id").val();
    			$.ajax({
    				type : "POST",
    				contentType : "application/json;charset=UTF-8",
    				cache: false,
    				url : "/cities/",
    				data : JSON.stringify(city),
    			    success: function(data) {
    			    	$('#table').html(data);
    			    },
    			    error: function(xhr) {
                        $('#table').html(xhr);
                    }
    			});
    		}
    	});
	</SCRIPT>
</body>
</html>