<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 16.01.2022
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Ваша страница</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-grid.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet">
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-sm">
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Фото профиля</h5>
                </div>
                <div class="card-body">
                    <div class="text-center">
                        <img src="data:image/jpg;base64, ${accountAvatar}"
                             class="rounded-circle img-responsive mt-2" width="128" height="128"
                             alt="Responsive image" onerror="this.src='resources/img/noPhotoAvailable.jpg'">
                    </div>
                </div>
            </div>
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Ваш профиль</h5>
                </div>
                <div class="card-body">
                    Имя: ${account.accountDetails.name}
                    <br>Фамилия: ${account.accountDetails.surname}
                    <br>Отчество: ${account.accountDetails.lastName}
                    <br>Дата рождения: ${account.accountDetails.date}
                    <br>icq: ${account.accountDetails.icq}
                    <c:forEach var="phone" items="${account.phones}">
                        <c:if test="${phone.phoneType == 0}">
                            <br>домашний телефон:
                        </c:if>
                        <c:if test="${phone.phoneType == 1}">
                            <br>рабочий телефон:
                        </c:if>
                        <c:if test="${phone.phoneType == 2}">
                            <br>личный телефон:
                        </c:if>
                        ${phone.phoneNumber}
                    </c:forEach>
                    <br>Домашний адрес: ${account.accountDetails.addressHome}
                    <br>Рабочий адрес: ${account.accountDetails.addressJob}
                    <br>email: ${account.accountDetails.email}
                    <br>Обо мне: ${account.accountDetails.aboutMe}
                </div>
            </div>
            <c:if test="${account.role == 1}">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Панель администратора социальной сети</h5>
                    </div>
                    <div class="card-body">
                        <form:form action="admin/admin-panel" class="modal-start" method="GET">
                            <button class="btn btn-success px-4 py-1" type="submit">Панель администратора</button>
                        </form:form>
                    </div>
                </div>
            </c:if>
        </div>
        <div class="col-sm">
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Написать сообщение на стену</h5>
                </div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/sendWallMessage">
                        <div class="post-editor">
                            <textarea name="NewWallMessage" id="post-field" class="post-field"
                                      placeholder="Написать сообщение"></textarea>
                            <div class="d-flex">
                                <button type="submit" class="btn btn-success px-4 py-1">Отправить</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Стена</h5>
                </div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/sendWallMessage">
                        <div id="wall-messages">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footers.jsp" %>
<div hidden="hidden">
    <fmt:formatDate var="fmtDate" value="${account.accountDetails.date}" pattern="yyyy-MM-dd"/>
    <input id="dateB" type="text" name="bean.dateProperty" value="${fmtDate}"/>
</div>
</body>
<script>
    var currentScrollHeight = 0;
    var count = 0;
    var record_per_page = 6;
    var page = 0;
    var url = window.location;
    var string = url.toString();
    var newUrl = string.substring(0, string.lastIndexOf("/"));
    var size = 1;

    async function callData(url) {
        try {
            return await $.ajax({
                type: "GET",
                url: newUrl + url,
                dataType: "json",
            });
        } catch (error) {
            console.log(error);
        }
    }

    async function callDataPerPage(url, page) {
        try {
            return await $.ajax({
                type: "GET",
                url: newUrl + url + page,
                dataType: "json",
            });
        } catch (error) {
            console.log(error);
        }
    }

    function createWallMessages(sizeRecords, messages, avatars, accounts) {
        for (let i = 0; i < messages.length; i++) {
            fillWallMessage(accounts[i], avatars[i], messages[i]);
        }
    }

    jQuery(document).ready(getData(0));

    async function getData(page) {
        var requests = [];
        requests.push('/get-size-wall-messages');
        requests.push('/get-wall-messages/');
        requests.push('/get-senders-avatar/');
        requests.push('/get-sender-account/');
        size = await callData(requests[0]);
        let messages = await callDataPerPage(requests[1], page);
        let avatars = await callDataPerPage(requests[2], page);
        let accounts = await callDataPerPage(requests[3], page);
        createWallMessages(size, messages, avatars, accounts);
    }

    $(window).on("scroll", function () {
        const scrollHeight = $(document).height();
        const scrollPos = Math.floor($(window).height() + $(window).scrollTop());
        const isBottom = scrollHeight - 100 < scrollPos;
        if (isBottom && currentScrollHeight < scrollHeight && count < size) {
            page++;
            getData(page);
            count += record_per_page;
            currentScrollHeight = scrollHeight;
        }
    });

    function fillWallMessage(account, avatar, message) {
        let wall = document.createElement('div');
        wall.className = "stream-post"
        let div = document.createElement('div');
        div.className = "sp-author";
        let a = document.createElement("a");
        a.setAttribute('href', "#");
        a.textContent = account.username;
        let username = document.createElement('h6');
        username.className = "sp-author-name";
        username.appendChild(a);
        let button = document.createElement("button");
        button.className = "btn btn-secondary btn-sm";
        button.name = "deleteText";
        button.value = message.id;
        button.innerHTML += "удалить";
        let photo = document.createElement('img');
        if (avatar !== "") {
            photo.src = "data:image/jpg;base64," + avatar;
        } else {
            photo.src = "resources/img/noPhotoAvailable.jpg";
        }
        photo.width = "128";
        photo.height = "128";
        let avatarAuthor = document.createElement('a');
        avatarAuthor.className = "sp-author-avatar";
        avatarAuthor.appendChild(photo);
        avatarAuthor.appendChild(button)
        div.appendChild(avatarAuthor);
        div.appendChild(username);
        let wallMessage = document.createElement('p');
        wallMessage.innerHTML += message.message;
        let dateMessage = document.createElement('p');
        dateMessage.className = "sp-info";
        dateMessage.innerHTML += convertData(message.publicationDate);
        let divMessage = document.createElement('div');
        divMessage.className = "sp-content";
        divMessage.appendChild(dateMessage);
        divMessage.appendChild(wallMessage);
        wall.appendChild(div);
        wall.appendChild(divMessage);
        $("#wall-messages").before(wall);
    }

    function convertData(date) {
        let convertDate = new Date(date);
        return convertDate.toUTCString();
    }

</script>
</html>