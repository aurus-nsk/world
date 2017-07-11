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
	 	<h1>Инициализация БД с помощью API</h1>
		   Загрузить несколько городов в БД: 
		   <input type="button" value="Загрузить" onclick="loadCities()">
			 <br/>
		   Загрузить несколько улиц в БД: 
 		   <input type="button" value="Загрузить" onclick="loadStreets()">
			<br/>
		   Загрузить несколько организаций в БД: 
 		   <input type="button" value="Загрузить" onclick="loadOrganizations()">
		 </div>

	</div>
	<jsp:include page="/WEB-INF/footer.jsp"/>
	<SCRIPT type="text/javascript">
    	function loadCities() {
			var list = [];
			
			var city0 = {name: 'Москва', square: 2561.5, population: 12380664};
			var city1 = {name: 'Новосибирск', square: 505.62, population: 1602915};
			var city2 = {name: 'Сочи', square: 176.77, population: 401291};
				
			list[0] = city0;
			list[1] = city1;
			list[2] = city2;
			
			$.ajax({
				type : "POST",
				contentType : "application/json;charset=UTF-8",
				url : "/cities/batch",
				dataType: 'json',
				data: JSON.stringify(list), 
			    success: function(data) {
			    	$('#table').html(data);
			    },
			    error: function(xhr) {
                    $('#table').html(xhr);
                }
			});
		}
    	
    	function loadStreets() {
			var list = [];
			
			var obj0 = {name: 'Титова', extent: 4550};
			var obj1 = {name: 'Красный проспект', extent: 6700};
			var obj2 = {name: 'Ленина', extent: 2400};
				
			list[0] = obj0;
			list[1] = obj1;
			list[2] = obj2;
			
			$.ajax({
				type : "POST",
				contentType : "application/json;charset=UTF-8",
				url : "/streets/batch",
				dataType: 'json',
				data: JSON.stringify(list), 
			    success: function(data) {
			    	$('#table').html(data);
			    },
			    error: function(xhr) {
                    $('#table').html(xhr);
                }
			});
		}
    	
    	function loadOrganizations() {
			var list = [];
			
			var city1 = {}; city1["id"] = 1; var city2 = {}; city2["id"] = 2; var city3 = {}; city3["id"] = 3;
			var street1 = {}; street1["id"] = 1; var street2 = {}; street2["id"] = 2;  var street3 = {}; street3["id"] = 3;  
			var phone0 = [{number: "89239213456"}, {number: "89239211111"}]; 
			var phone1 = [{number: "21114398"}, {number: "3339966"}];
			var phone2 = [{number: "3332211"}];
			var phone3 = [{number: "4445566"}];
			var phone4 = [{number: "2001100"}];
			var phone5 = [{number: "88855533"}];
			
			var obj0 = {name: 'Альфа-Банк', city: city2, street: street3, homeNumber: 10, scope: 'Банк', phone: phone0, website: 'alfabank.ru', keyWords: 'банкомат на Ленина, альфабанк, банк в центре, банкомат'};
			var obj1 = {name: 'Цирюльник', city: city2,street: street2, homeNumber: 75, scope: 'Парикмахерская', phone: phone1, website: 'cirulnik.ru', keyWords: 'Постричься в Новосибирске, барбершоп, причёски'};
			var obj2 = {name: 'Ингосстрах', city: city2, street: street1, homeNumber: 1, scope: 'Страхование', phone: phone2, website: 'ingos.ru', keyWords: 'купить полис ОСАГО, страховка, застраховаться от клеща'};
			var obj3 = {name: 'Мексика для всех', city: city2, street: street3, homeNumber: 12, scope: 'Ресторан', phone: phone3, website: 'super-cafe.ru', keyWords: 'Поесть на Ленина, кафе на ленина, ресторан в центре, мексиканская кухня поесть на ленина'};
			var obj4 = {name: 'Сочинская еда', city: city3, street: street3, homeNumber: 9, scope: 'Ресторан', phone: phone4, website: 'sochi.ru', keyWords: 'Поесть в Сочи'};
			var obj5 = {name: 'Итальянская кухня', city: city2, street: street3, homeNumber: 12, scope: 'Ресторан', phone: phone5, website: 'cafe.ru', keyWords: 'Поесть на Ленина, ресторан, итальянская кухня поесть на ленина'};
			
			list[0] = obj0;
			list[1] = obj1;
			list[2] = obj2;
			list[3] = obj3;
			list[4] = obj4;
			list[5] = obj5;
			
			$.ajax({
				type : "POST",
				contentType : "application/json;charset=UTF-8",
				url : "/organizations/batch",
				dataType: 'json',
				data: JSON.stringify(list), 
			    success: function(data) {
			    	$('#table').html(data);
			    },
			    error: function(xhr) {
                    $('#table').html(xhr);
                }
			});
    	}
	</SCRIPT>
</body>
</html>