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
	 	<h1>Заполнение БД</h1>
	 	<b>Заполнение БД тестовыми данными с помощью API</b>
	 	<br/>
		   <b>1.Загрузить несколько городов в БД: </b>
		    <br/>
		   Москва, Новосибирск, Сочи
		    <br/>
		   <input type="button" value="Загрузить" onclick="loadCities()">
			 <br/>
		   <b>2.Загрузить несколько улиц в БД: </b>
		    <br/>
		    Титова, Красный проспект, Ленина
		     <br/>
 		   <input type="button" value="Загрузить" onclick="loadStreets()">
			<br/>
		   <b>3.Загрузить несколько организаций в БД:</b>
		    <br/>
		   Альфа-Банк, Цирюльник, Итальянская кухня, ...
		    <br/> 
 		   <input type="button" value="Загрузить" onclick="loadOrganizations()">
 		   <br/>
 		   <b>Формы для заполнения по отдельности:</b>
 		   	 <br/>
 		   	 Добавить город:
			 <a href="/city">/city</a>
			 <br/>
			 Добавить улицу:
			 <a href="/street">/street</a>
			 <br/>
			 Добавить организацию:
			 <a href="/organization">/organization</a>
			 <br/>
			 <h1>API</h1>
			 <b>Организации:</b>
			 <br/>
			 Все организации:
			 <a href="/organizations/">/organizations/</a>
			 <br/>
			 Найти организацию по имени:
			 <a href="/organizations/name?name=Альфа-Банк">/organizations/name?name=Альфа-Банк</a>
			 <br/>
			 Новые организации:
			 <a href="/organizations/from?from=2017-07-11T01:36:34.548">/organizations/from?from=2017-07-11T01:36:34.548</a>
			 <br/>
			 Организация с id=1:
			 <a href="/organizations/1">/organizations/1</a>
			 <br/>
			 <b>Города:</b>
			 <br/>
			 Все города:
			 <a href="/cities/">/cities/</a>
			 <br/>
			 Город с id=1:
			 <a href="/cities/1">/cities/</a>
			 <br/>
			 <b>Улицы:</b>
			 <br/>
			 Все улицы:
			 <a href="/streets/">/streets/</a>
			 <br/>
			 Улица с id=1:
			 <a href="/streets/1">/cities/</a>
			 <br/>
			 <h1>Клиентская библиотека:</h1>
			 Поиск Иннокентия
			 <a href="/search">/search</a>
			 <br/>
			 Анализ конкурентов
			 <a href="/analyze">/analyze</a>
			 <br/>
			 Поиск пешеходной улицы
			 <a href="/pedestrian">/pedestrian</a>
			 <br/>
		 </div>
		 
		 
		 
		 <div id="table">
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
			console.log(JSON.stringify(list));
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
                    $('#table').html(xhr.responseText);
                }
			});
		}
    	
    	function loadStreets() {
			var list = [];
			
			var obj0 = {name: 'Титова', extent: 4550};
			var obj1 = {name: 'Красный проспект', extent: 6700};
			var obj2 = {name: 'Ленина', extent: 900};
				
			list[0] = obj0;
			list[1] = obj1;
			list[2] = obj2;
			console.log(JSON.stringify(list));
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
                    $('#table').html(xhr.responseText);
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
			
			var obj0 = {name: 'Альфа-Банк', city: city2, street: street3, homeNumber: 10, scope: 'Банк', phone: phone0, website: 'alfabank.ru', keyWords: 'банкомат на ленина, альфабанк, банк в центре, банкомат'};
			var obj1 = {name: 'Цирюльник', city: city2,street: street2, homeNumber: 75, scope: 'Парикмахерская', phone: phone1, website: 'cirulnik.ru', keyWords: 'Постричься в Новосибирске барбершоп постричься в новосибирске'};
			var obj2 = {name: 'Ингосстрах', city: city2, street: street1, homeNumber: 1, scope: 'Страхование', phone: phone2, website: 'ingos.ru', keyWords: 'купить полис ОСАГО, страховка, застраховаться от клеща'};
			var obj3 = {name: 'Мексика для всех', city: city2, street: street3, homeNumber: 12, scope: 'Ресторан', phone: phone3, website: 'super-cafe.ru', keyWords: 'поесть на ленина ресторан в центре, мексиканская кухня Поесть на Ленина'};
			var obj4 = {name: 'Сочинская еда', city: city3, street: street3, homeNumber: 9, scope: 'Ресторан', phone: phone4, website: 'sochi.ru', keyWords: 'поесть в сочи'};
			var obj5 = {name: 'Итальянская кухня', city: city2, street: street3, homeNumber: 12, scope: 'Ресторан', phone: phone5, website: 'cafe.ru', keyWords: 'поесть на ленина ресторан, итальянская кухня Поесть на Ленина'};
			
			list[0] = obj0;
			list[1] = obj1;
			list[2] = obj2;
			list[3] = obj3;
			list[4] = obj4;
			list[5] = obj5;
			console.log(JSON.stringify(list));
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
                    $('#table').html(xhr.responseText);
                }
			});
    	}
	</SCRIPT>
</body>
</html>