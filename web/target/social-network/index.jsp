<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 15.01.2022
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet"/>
    <title>Добро пожаловать</title>
</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary">
<main class="form-signin w-100 m-auto">
    <form action="${pageContext.request.contextPath}/index.jsp" method="POST">
        <c:if test="${param.error == true}">
            <label class="error">
                Ошибка при вводе логина или пароль
            </label>
        </c:if>
        <h1 class="h3 mb-3 fw-normal">Добро пожаловать</h1>
        <div class="form-floating">
            <input id="username" type="text" name="username" class="form-control" placeholder="name@example.com">
            <label for="username">Имя пользователя</label>
        </div>
        <div class="form-floating">
            <input id="password" type="password" name="password" class="form-control" placeholder="Password">
            <label for="password">Пароль</label>
        </div>

        <div class="form-check text-start my-3">
            <input class="form-check-input" type="checkbox" value="remember-me" id="flexCheckDefault">
            <label class="form-check-label" for="flexCheckDefault">
                Запомнить меня
            </label>
        </div>
        <button class="btn btn-secondary" type="submit">Войти</button>
    </form>
    <div>
        <p class="mb-0">У Вас нет аккаунта? <a onclick="location.href='registration-account'"
                                               class="text-black-50 fw-bold">Зарегистрироваться</a>
        </p>
    </div>
</main>
</body>
</html>