<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 16.01.2022
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Странница ${account.accountDetails.name}</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="position-relative">
    <div class="position-absolute top-0 start-0">
        <form method="GET" action="${pageContext.request.contextPath}/edit-account-settings">
            <button class="btn btn-secondary"
                    onclick="location.href='${pageContext.request.contextPath}/edit-account-settings'"> Изменить
                данные
                пользователя
            </button>
        </form>
        <form method="GET" action="save-account-settings">
            <button class="btn btn-secondary">Сохранить данные пользователя</button>
        </form>
        <div class="mb-3">
            <form method="POST" enctype="multipart/form-data" action="update-account-settings">
                <label for="formFile" class="form-label">Выберите файл для загрузки профиля:</label>
                <input class="form-control" type="file" id="formFile" name="uploadXml">
                <input class="btn btn-secondary" type="submit" value="Загрузить данные пользователя"/>
            </form>
        </div>
    </div>
    <div class="card-body">
        <div class="text-center">
            <img src="data:image/jpg;base64, ${accountAvatar}"
                 class="rounded-circle img-responsive mt-2" width="128" height="128"
                 alt="Responsive image" onerror="this.src='resources/img/noPhotoAvailable.jpg'">
        </div>
    </div>
    <div class="container d-flex align-items-center justify-content-center">
        <form>
            Имя: ${account.accountDetails.name}
            <br>Фамилия: ${account.accountDetails.surname}
            <br>Отчество: ${account.accountDetails.lastName}
            <br>Дата: ${account.accountDetails.date}
            <br>icq: ${account.accountDetails.icq}
            <c:forEach var="phone" items="${account.phones}">
                <c:if test="${phone.phoneType == 0}">
                    <br>домашний телефон -
                </c:if>
                <c:if test="${phone.phoneType == 1}">
                    <br>рабочий телефон -
                </c:if>
                <c:if test="${phone.phoneType == 2}">
                    <br>личный телефон -
                </c:if>
                ${phone.phoneNumber}
            </c:forEach>
            <br>Домашний адрес: ${account.accountDetails.addressHome}
            <br>Рабочий адрес: ${account.accountDetails.addressJob}
            <br>email: ${account.accountDetails.email}
            <br>Обо мне: ${account.accountDetails.aboutMe}
            <br/>
        </form>
    </div>
</div>
<%@ include file="../footers.jsp" %>
</body>
</html>
