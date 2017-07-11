<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML>
<html>
     
<head>
	<jsp:include page="/WEB-INF/header.jsp">
    	<jsp:param name="title" value="Организация"/>
	</jsp:include>
</head>

<body>

	<div id="wrapper" class="wrapper">
		 <div class="container">
		    Добавить организацию:
		    <form id="org_add_form" method="POST" modelAttribute="orgForm">
		    	
		    	<div class="form-group">
	    			<label for="name">Название</label>
	    			<input id="name_id" name="name" type="text" class="form-control inputfield"></input>
	  			</div>
		   		<div class="form-group">
		   			<label for="city_id">Город</label>
	    			<select id="city_id" type="text" name="city_id" class="form-control inputfield">
	                	<c:forEach items="${cities}" var="city">
							<option value="${city.id}">${city.name}</option>
						</c:forEach>
	                </select>
				</div>
				<div class="form-group">
					<label for="city_id">Улица</label>
	    			<select id="street_id" type="text" name="street_id" class="form-control inputfield">
	                	<c:forEach items="${streets}" var="street">
							<option value="${street.id}">${street.name}</option>
						</c:forEach>
	                </select>
				</div>
				<div class="form-group">
	    			<label for="name">Номер дома</label>
	    			<input id="homeNumber_id" name="homeNumber" type="text" class="form-control inputfield"></input>
	  			</div>
	  			<div class="form-group">
	    			<label for="name">Сфера деятельности</label>
	    			<input id="scope_id" name="scope" type="text" class="form-control inputfield"></input>
	  			</div>
	  			<div class="form-group">
	    			<label for="name">Телефон</label>
	    			<input id="phone_id" name="phone" type="text" class="form-control inputfield"></input>
	  			</div>
				<div class="form-group">
	    			<label for="name">Сайт</label>
	    			<input id="website_id" name="website" type="text" class="form-control inputfield"></input>
	  			</div>
	  			<div class="form-group">
	    			<label for="name">Ключевые слова для поиска</label>
	    			<input id="key_words_id" name="keyWords" type="text" class="form-control inputfield"></input>
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
    		$("#org_add_form").submit(function(event) {
    			event.preventDefault();
    			submitViaAjax();
    		});

    		function submitViaAjax() {
        		
				var organization = {};
				organization["name"] = $("#name_id").val();
				organization["homeNumber"] = $("#homeNumber_id").val();
				organization["scope"] = $("#scope_id").val();
				organization["website"] = $("#website_id").val();
					
				var phone = [];
				phone[0] = {};
				phone[0]["number"] = $("#phone_id").val();
				organization["phone"] = phone;
				
				var street = {};
    			street["id"] = $("#street_id").val();
    			organization["street"] = street;
    			
    			var city = {};
    			city["id"] = $("#city_id").val();
    			organization["city"] = city;

    			$.ajax({
    				type : "POST",
    				contentType : "application/json;charset=UTF-8",
    				cache: false,
    				url : "/organizations/",
    				data : JSON.stringify(organization),
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