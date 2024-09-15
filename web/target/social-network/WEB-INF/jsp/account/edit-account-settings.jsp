<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 30.01.2022
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Изменение данных пользователя</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet">

</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container p-0">
    <div class="tab-content">
        <div class="tab-pane fade show active">
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Фото профиля</h5>
                </div>
                <div class="card-body">
                    <form method="POST" enctype="multipart/form-data" action="update-avatar">
                        <div class="text-center">
                            <img src="data:image/jpg;base64, ${accountAvatar}"
                                 class="rounded-circle img-responsive mt-2" width="128" height="128"
                                 alt="Responsive image" onerror="this.src='resources/img/noPhotoAvailable.jpg'">
                            <div class="mt-2">
                                <label class="btn btn-primary" for="my-file-selector">
                                    <input id="my-file-selector" type="file" class="d-none" name="file">
                                    Выбрать файл
                                </label>
                            </div>
                            <small>Для лучшего результата, используйте фото размером 128px на 128px в формате
                                *.jpg</small>
                        </div>
                        <button type="submit" name="file-name" class="btn btn-primary">Сохранить</button>
                    </form>
                </div>
            </div>

            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Ваш профиль</h5>
                </div>
                <div class="card-body">
                    <form method="POST" enctype="multipart/form-data" id="form-save" modelAttribute="account"
                          action="edit-account-settings"
                          onsubmit="return sendPost();">
                        <input type="hidden" name="photoFileName" value="${account.accountPhoto.photoFileName}"/>
                        <input type="hidden" name="photo" value="${accountAvatar}">
                        <div class="form-floating">
                            <input name="accountDetails.name" id="accountDetails.name" class="form-control"
                                   title='укажите имя'
                                   value="${account.accountDetails.name}"/>
                            <label for="accountDetails.name">Имя:</label>
                        </div>
                        <div class="form-floating">
                            <input name="accountDetails.surname" id="accountDetails.surname" class="form-control"
                                   title='укажите фамилию'
                                   value="${account.accountDetails.surname}"/>
                            <label for="accountDetails.surname">Фамилия:</label>
                        </div>
                        <div class="form-floating">
                            <input name="accountDetails.lastName" id="accountDetails.lastName" class="form-control"
                                   value="${account.accountDetails.lastName}"/>
                            <label for="accountDetails.lastName">Отчество:</label>
                        </div>
                        <div class="form-floating">
                            <input name="accountDetails.date" class="form-control" id="accountDetails.date" type="date"
                                   value="${account.accountDetails.date}"/>
                            <label for="accountDetails.date">Дата рождения:</label>
                        </div>
                        <div class="form-floating">
                            <input name="accountDetails.icq" id="accountDetails.icq" title='укажите icq'
                                   class="form-control"
                                   value="${account.accountDetails.icq}"/>
                            <label for="accountDetails.icq">icq:</label>
                        </div>
                        <div class="form-floating">
                            <span class="check-address" hidden="hidden">Обязательно для заполнения</span>
                            <input name="accountDetails.addressHome" id="accountDetails.addressHome"
                                   class="form-control"
                                   value="${account.accountDetails.addressHome}"/>
                            <label for="accountDetails.addressHome">Домашний адресс:</label>
                        </div>
                        <div class="form-floating">
                            <input name="accountDetails.addressJob" id="accountDetails.addressJob" class="form-control"
                                   value="${account.accountDetails.addressJob}"/>
                            <label for="accountDetails.addressJob">Рабочий адресс:</label>
                        </div>
                        <div class="form-floating">
                            <input class="form-control" name="accountDetails.email" id="accountDetails.email"
                                   value="${account.accountDetails.email}"/>
                            <label for="accountDetails.email">email:</label>
                        </div>
                        <div class="form-floating">
                            <input class="form-control" name="accountDetails.aboutMe" id="accountDetails.aboutMe"
                                   value="${account.accountDetails.aboutMe}"/>
                            <label for="accountDetails.aboutMe">Обо мне:</label>
                        </div>
                        <div class="form-floating">
                            <c:if test="${account.role == 0}">
                                <select class="form-select" id="typeRole" name="typeRole">
                                    <option value="ROLE_USER" selected>Пользователь</option>
                                </select>
                            </c:if>
                            <c:if test="${account.role == 1}">
                                <select class="form-select" id="typeRole" name="typeRole">
                                    <option value="ROLE_USER">Пользователь</option>
                                    <option value="ROLE_ADMIN" selected>Администратор</option>
                                </select>
                            </c:if>
                            <label for="typeRole">Роль:</label>
                        </div>
                        <c:forEach var="phone" items="${account.phones}">
                            <c:if test="${phone.phoneType == 0}">
                                <div id='phoneDiv'>
                                    <div class="input-group mb-3">
                                        <input type="hidden" name="phoneId" value="${phone.id}"/>
                                        <input class="form-control" name="phone" value="${phone.phoneNumber}"/>
                                        <select class="form-select" name="typePhone">
                                            <option value="HOME" selected>Домашний</option>
                                            <option value="WORK">Рабочий</option>
                                            <option value="ADDITIONAL">Личный</option>
                                        </select>
                                        <button type='button' name="button-delete" class="btn btn-secondary">
                                            удалить телефон
                                        </button>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${phone.phoneType == 1}">
                                <div id='phoneDiv'>
                                    <div class="input-group mb-3">
                                        <input type="hidden" name="phoneId" value="${phone.id}"/>
                                        <input class="form-control" name="phone" value="${phone.phoneNumber}"/>
                                        <select class="form-select" name="typePhone">
                                            <option value="HOME">Домашний</option>
                                            <option value="WORK" selected>Рабочий</option>
                                            <option value="ADDITIONAL">Личный</option>
                                        </select>
                                        <button type='button' name="button-delete" class="btn btn-secondary">
                                            удалить телефон
                                        </button>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${phone.phoneType == 2}">
                                <div id='phoneDiv'>
                                    <div class="input-group mb-3">
                                        <input type="hidden" name="phoneId" value="${phone.id}"/>
                                        <input class="form-control" name="phone" value="${phone.phoneNumber}"/>
                                        <select class="form-select" name="typePhone">
                                            <option value="HOME">Домашний</option>
                                            <option value="WORK">Рабочий</option>
                                            <option value="ADDITIONAL" selected>Личный</option>
                                        </select>
                                        <button type='button' name="button-delete" class="btn btn-secondary">
                                            удалить телефон
                                        </button>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                        <button type='button' name="button" id='button-add-phone' class="btn btn-secondary"
                                onclick="createNewPhone(); addMask();">добавить телефон
                        </button>
                        <input class="btn btn-secondary" type="submit" value="сохранить"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div hidden="hidden">
    <fmt:formatDate var="fmtDate" value="${account.accountDetails.date}" pattern="yyyy-MM-dd"/>
    <input id="dateB" type="text" name="bean.dateProperty" value="${fmtDate}"/>
</div>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.maskedinput.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/edit-account-settings.js"></script>
<%@ include file="../footers.jsp" %>
<script>
    window.addEventListener('load',
        function (e) {
            var date = document.getElementById("dateB");
            document.getElementById("accountDetails.date").value = date.value;
        }, false);
</script>
</body>
</html>