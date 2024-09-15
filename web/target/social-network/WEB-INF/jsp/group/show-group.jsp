<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 03.03.2022
  Time: 22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Группа ${group.groupName}</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/datatables.min.css"/>
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div>
    <div class="row justify-content-md-center">
        <div class="col-md-auto">
            <c:if test="${group.idGroupCreator == account.id}">
                <form:form action="group-admin-panel" class="modal-start" method="GET">
                    <button class="btn btn-success px-4 py-1" type="submit">Настройка ролей в группе</button>
                </form:form>
            </c:if>
        </div>
        <div class="col-md-auto">
            <c:if test="${group.idGroupCreator == account.id}">
                <form:form action="group-settings" class="modal-start" method="GET">
                    <button class="btn btn-success px-4 py-1" type="submit">Настройка группы</button>
                </form:form>
            </c:if>
        </div>
        <div class="col-md-auto">
            <c:if test="${group.idGroupCreator == account.id}">
                <form:form action="delete-group" method="post">
                    <button class="btn btn-success px-4 py-1" type="submit" value="${members.get(0).group.groupId}"
                            name="groupId">
                        Удалить группу
                    </button>
                </form:form>
            </c:if>
        </div>
        <div class="col-md-auto">
            <form:form action="delete-members" method="post">
                <button class="btn btn-success px-4 py-1" type="submit" value="${members.get(0).group.groupId}"
                        name="groupId">
                    Выйти из
                    группы
                </button>
            </form:form>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col">
            <c:if test="${groupFlag >= 2}">
                <form:form action="group-add-members" method="post">
                    <button class="btn btn-secondary" type="submit"> Подать заявку на добавление в группу</button>
                </form:form>
                <c:if test="${groupFlag == 2}">
                    Вам отказано в просмотре страницы, пользователь отклонил Вашу заявку
                </c:if>
            </c:if>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-sm">
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Фото группы</h5>
                </div>
                <div class="card-body">
                    <div class="text-center">
                        <img src="data:image/jpg;base64, ${groupLogo}"
                             class="rounded-circle img-responsive mt-2" width="128" height="128"
                             alt="Responsive image" onerror="this.src='resources/img/noPhotoAvailable.jpg'">
                    </div>
                </div>
            </div>
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Описание группы</h5>
                </div>
                <div class="card-body">
                    ${group.info}
                </div>
            </div>
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Участники группы</h5>
                </div>
                <div class="card-body">
                    <table id="account-table" class="table table-striped">
                        <thead>
                        <tr>
                            <th>Имя</th>
                            <th>Фамилия</th>
                            <th>Должность</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="member" items="${members}">
                            <tr>
                                <td><c:out value="${member.member.accountDetails.name}"/></td>
                                <td><c:out value="${member.member.accountDetails.lastName}"/></td>
                                <c:if test="${member.groupRole == 'MEMBER'}">
                                    <td>Участник</td>
                                </c:if>
                                <c:if test="${member.groupRole == 'ADMIN'}">
                                    <td>Администратор</td>
                                </c:if>
                                <c:if test="${member.groupRole == 'MODER'}">
                                    <td>Модератор</td>
                                </c:if>
                                <c:if test="${member.groupRole == 'SUBSCRIBER'}">
                                    <td>Подписавщийся</td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-sm">
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Написать сообщение в группу</h5>
                </div>
                <c:if test="${groupFlag == 0}">
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/send-group-new-message">
                        <div class="post-editor">
                            <textarea name="NewWallMessage" id="post-field" class="post-field"
                                      placeholder="Написать сообщение"></textarea>
                            <div class="d-flex">
                                <button type="submit" class="btn btn-success px-4 py-1">Отправить</button>
                            </div>
                        </div>
                    </form>
                    </c:if>
                </div>
            </div>
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Сообщения в группе</h5>
                </div>
                <div class="card-body">
                    <form method="post" action="${pageContext.request.contextPath}/send-group-new-message">
                        <div id="wall-messages">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../footers.jsp" %>
</body>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datatables.min.js"></script>
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
        console.log(messages)
        for (let i = 0; i < messages.length; i++) {
            fillWallMessage(accounts[i], avatars[i], messages[i]);
        }
    }

    jQuery(document).ready(getData(0));

    async function getData(page) {
        var requests = [];
        requests.push('/get-size-group-wall-messages/');
        requests.push('/get-group-wall-messages/');
        requests.push('/get-group-senders-avatar/');
        requests.push('/get-group-sender-account/');
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