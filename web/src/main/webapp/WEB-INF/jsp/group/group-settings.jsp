<%--
  Created by IntelliJ IDEA.
  User: alexa
  Date: 13.04.2024
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Настройки группы</title>
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
<div class="container p-0">
    <div class="tab-content">
        <div class="tab-pane fade show active">
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">Фото группы</h5>
                </div>
                <div class="card-body">
                    <form method="POST" enctype="multipart/form-data" action="update-group-logo">
                        <div class="text-center">
                            <img src="data:image/jpg;base64, ${group.logo}"
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
                    <h5 class="card-title mb-0">Описание группы</h5>
                </div>
                <div class="card-body">
                    <form method="POST" enctype="multipart/form-data" id="form-save" modelAttribute="group"
                          action="edit-group-settings"
                          onsubmit="return sendPost();">
                        <input type="hidden" name="logo" value="${group.logo}">
                        <div class="form-floating">
                            <input name="groupName" id="groupName" class="form-control" title='укажите новое имя группы'
                                   value="${group.groupName}"/>
                            <label for="groupName">Имя группы:</label>
                        </div>
                        <div class="form-floating">
                            <input name="info" id="info" class="form-control" title='напишите новое описание группы'
                                   value="${group.info}"/>
                            <label for="info">Описание группы:</label>
                        </div>
                        <input class="btn btn-secondary" type="submit" value="сохранить"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>