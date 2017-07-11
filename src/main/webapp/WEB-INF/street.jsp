<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML>
<html>
     
<head>
	<jsp:include page="/WEB-INF/header.jsp">
    	<jsp:param name="title" value="Улица"/>
	</jsp:include>
</head>

<body>

	<div id="wrapper" class="wrapper">
		 <div class="container">
		    Добавить улицу:
		    <form id="street_add_form" method="POST" modelAttribute="streetForm">
		    	
		    	<div class="form-group">
	    			<label for="name">Название</label>
	    			<input id="name_id" name="name" type="text"  class="form-control inputfield"></input>
	  			</div>
		   		<div class="form-group">
	    			<label for="extent">Протяженность(м)</label>
					<input id="square_id" name="square" type="text" class="form-control inputfield"></input>
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
    		$("#street_add_form").submit(function(event) {
    			event.preventDefault();
    			submitViaAjax();
    		});

    		function submitViaAjax() {
				var street = {};
    			street["name"] = $("#name_id").val();
    			street["extent"] = $("#extent_id").val();
    			$.ajax({
    				type : "POST",
    				contentType : "application/json;charset=UTF-8",
    				cache: false,
    				url : "/streets/",
    				data : JSON.stringify(street),
    			    success: function(data) {
    			    	$('#table').html(data);
    			    },
    			    error: function(xhr) {
                        $('#table').html(xhr.responseText);
                    }
    			});
    		}
    	});
	</SCRIPT>
</body>
</html>