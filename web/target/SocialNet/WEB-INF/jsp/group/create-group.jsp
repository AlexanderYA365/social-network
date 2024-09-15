<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 30.01.2022
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Создать группу</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container d-flex align-items-center justify-content-center">
    <form:form action="create-group" modelAttribute="group" method="post">
        <div class="form-floating">
            <input name="groupName" id="groupName" class="form-control"/>
            <label for="groupName">Введите желаемое имя группы:</label>
        </div>
        <div class="form-floating">
            <div class="mb-3">
                <label for="logo" class="form-label">Выберите аватар для загрузки:</label>
                <input class="form-control" type="file" id="logo" name="logo">
            </div>
        </div>
        <div class="form-floating">
            <textarea name="info" id="info" class="form-control">
            </textarea>
            <label for="info">Введите описание группы:</label>
        </div>
        <button class="btn btn-secondary" type="submit">отправить</button>
    </form:form>
</div>
<%@ include file="../footers.jsp" %>
</body>
<script>
    function expandTextarea(id) {
        document.getElementById(id).addEventListener('keyup', function () {
            this.style.overflow = 'hidden';
            this.style.height = 0;
            this.style.height = this.scrollHeight + 'px';
        }, false);
    }

    expandTextarea('info');
</script>
</html>
