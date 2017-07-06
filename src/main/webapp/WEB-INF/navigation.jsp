<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-light bg-faded justify-content-between flex-nowrap flex-row" style="background-color: #e3f2fd;">
	<a class="navbar-brand" href="#">Krasota</a>
    <div class="container">
        <ul class="nav navbar-nav flex-row float-right">
            <li class="nav-item"><a class="nav-link active" href="/">Новости</a></li>
            <li class="nav-item"><a class="nav-link active" href="/orders">Заказы</a></li>
            <li class="nav-item"><a class="nav-link active" href="/profile">Личная информация</a></li>
            
            <security:authorize access="hasRole('ROLE_ADMIN')">
				<li class="nav-item"><a class="nav-link active" href="/orders/add">Новый заказ</a></li>
				<li class="nav-item"><a class="nav-link active" href="/settings">Настройки</a></li>
			</security:authorize>
			
			<security:authorize access="isAnonymous()">
				<li class="nav-item pull-xs-right"><a class="nav-link active" href="/login">Войти</a></li>
				<li class="nav-item pull-xs-right"><a class="nav-link active" href="/registration">Регистрация</a></li>
			</security:authorize>
			
			<security:authorize access="isAuthenticated()">
				<li class="nav-item pull-xs-right"><a class="nav-link active" href="javascript:document.getElementById('logout').submit()">Выйти</a></li>
			</security:authorize>
        </ul>
    </div>
</nav>
<form id="logout" action="/logout" method="post" >
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
</br>