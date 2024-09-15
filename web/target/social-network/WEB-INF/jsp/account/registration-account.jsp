<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 13.01.2022
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создание аккаунта</title>
    <link href="${pageContext.request.contextPath}/resources/css/registration-account.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="p-3 mb-2 bg-primary text-dark">
<section class="vh-100">
    <div class="container py-5 h-100">
        <div class="row justify-content-center align-items-center h-100">
            <div class="col-8 col-lg-6 col-xl-6">
                <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                    <div class="card-body p-4 p-md-5">
                        <h3 class="mb-4 pb-2 pb-md-0 mb-md-5">Регистрация нового пользователя</h3>
                        <form enctype="multipart/form-data" modelAttribute="account"
                              action="${pageContext.request.contextPath}/registration-account" method="POST">
                            <div class="row">
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <label class="form-label" for="username">Имя пользователя</label>
                                        <input type="text" name="username" id="username" placeholder="Введите Ваше имя"
                                               class="form-control form-control-lg" required>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4">
                                </div>
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <label class="form-label" for="password">Пароль</label>
                                        <input type="password" name="password" id="password"
                                               class="form-control form-control-lg"
                                               pattern="^\S{6,}$"
                                               onchange="this.setCustomValidity(this.validity.patternMismatch ?
                                               'Пароль должен состоять из 6 символов' : ''); if(this.checkValidity())
                                                   form.password_two.pattern = this.value;"
                                               placeholder="Пароль" required>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <label class="form-label" for="password-confirm">Подтверждение пароль</label>
                                        <input type="password" id="password-confirm"
                                               class="form-control form-control-lg"
                                               pattern="^\S{6,}$"
                                               onchange="this.setCustomValidity(this.validity.patternMismatch ?
                                               'Пожалуйста повторите пароль' : '');"
                                               placeholder="Подтверждение пароля" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <label class="form-label" for="accountDetails.name">Имя</label>
                                        <input type="text" name="accountDetails.name" id="accountDetails.name"
                                               class="form-control form-control-lg"
                                               required>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <label class="form-label" for="accountDetails.lastName">Отчество</label>
                                        <input type="text" name="accountDetails.lastName" id="accountDetails.lastName"
                                               class="form-control form-control-lg" required>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <label class="form-label" for="accountDetails.surname">Фамилия</label>
                                        <input type="text" name="accountDetails.surname" id="accountDetails.surname"
                                               class="form-control form-control-lg" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 mb-4 d-flex align-items-center">
                                    <div class="form-outline datepicker w-100">
                                        <label for="accountDetails.date" class="form-label">Дата рождения</label>
                                        <input type="date" name="accountDetails.date"
                                               class="form-control form-control-lg"
                                               id="accountDetails.date" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 mb-4 pb-2">
                                    <div class="form-outline">
                                        <label class="form-label" for="accountDetails.email">Email</label>
                                        <input type="email" name="accountDetails.email" id="accountDetails.email"
                                               placeholder="Введите Ваш email"
                                               class="form-control form-control-lg" required>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4 pb-2">
                                    <div class="form-outline">
                                        <label class="form-label" for="accountDetails.icq">icq</label>
                                        <input type="text" name="accountDetails.icq" id="accountDetails.icq"
                                               placeholder="Введите Ваш icq"
                                               class="form-control form-control-lg" required>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4 pb-2">
                                    <div class="form-outline">
                                        <label class="form-label" for="accountDetails.addressHome">Введите Ваш домашний
                                            адрес</label>
                                        <input type="text" name="accountDetails.addressHome"
                                               id="accountDetails.addressHome"
                                               placeholder="Введите Ваш домашний адрес"
                                               class="form-control form-control-lg" required>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4 pb-2">
                                    <div class="form-outline">
                                        <label class="form-label" for="accountDetails.addressJob">Введите Ваш рабочий
                                            адрес</label>
                                        <input type="text" name="accountDetails.addressJob"
                                               id="accountDetails.addressJob"
                                               placeholder="Введите Ваш рабочий адрес"
                                               class="form-control form-control-lg" required>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 mb-4 pb-2">
                                    <label for="typePhone" class="form-label">Тип телефона</label>
                                    <select name="typePhone" class="form-select" id="typePhone" required="">
                                        <option value="">Выберите...</option>
                                        <option value="HOME">Домашний</option>
                                        <option value="WORK">Рабочий</option>
                                        <option value="ADDITIONAL">Личный</option>
                                    </select>
                                    <div class="invalid-feedback">
                                        Пожалуйста, выберите тип телефоне
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4 pb-2">
                                    <div class="form-outline">
                                        <label class="form-label" for="phone">Номер телефона</label>
                                        <input type="text" name="phone" id="phone"
                                               class="form-control form-control-lg" required>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 mb-4 pb-2">
                                <div class="form-file">
                                    <input type="file" name="file" class="form-file-input" id="customFile" required>
                                    <label class="form-file-label" for="customFile">
                                        <span class="form-file-text">Выберите аватар...</span>
                                        <span class="form-file-button">Выбрать</span>
                                    </label>
                                    </input>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="accountDetails.aboutMe">Обо мне</label>
                                    <textarea class="form-control" name="accountDetails.aboutMe"
                                              id="accountDetails.aboutMe" rows="4"
                                              required></textarea>
                                </div>
                            </div>
                            <div class="mt-4 pt-2">
                                <input class="btn btn-primary btn-lg" type="submit" value="Зарегистрироваться"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="${pageContext.request.contextPath}/resources/js/edit-account-settings.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.maskedinput.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
</body>
</html>
