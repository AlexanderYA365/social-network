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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/datatables.min.css"/>
</head>
<body>
<%@ include file="../navbar.jsp" %>
<form:form action="show-group" class="modal-start" method="GET">
    <button type="submit" name="groupId" class="btn btn-secondary" value="${members.group.groupId}">Перейти к группе
    </button>
</form:form>

<table border="0" margin="0" padding="0" width="100%"
       class="dataTables_wrapper" id="group-admin">
    <thead>
    <tr>
        <th>Имя пользователя</th>
        <th>Имя</th>
        <th>Отчество</th>
        <th>Фамилия</th>
        <th>Группа</th>
        <th>Роль</th>
        <th>удалить</th>
        <th>Выбрать</th>
    </tr>
    </thead>
</table>

<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js.map"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datatables.min.js"></script>
<script>
    $(document).ready(function () {
        var url = window.location;
        var string = url.toString();
        var newUrl = string.substring(0, string.lastIndexOf("/"));
        const table = $('#group-admin').DataTable({
            processing: true,
            serverSide: true,
            ajax: {
                url: newUrl + '/getGroupMembers',
            },
            columns: [
                {data: 'member.username'},
                {data: 'member.accountDetails.name'},
                {data: 'member.accountDetails.lastName'},
                {data: 'member.accountDetails.surname'},
                {data: 'group.groupName'},
                {data: 'groupRole'},
                {defaultContent: '<button class="btn btn-secondary">Удалить</button>'},
                {
                    defaultContent: '<select name="group-role">' +
                        '<option value="SUBSCRIBER">Подписавшийся</option>' +
                        '<option value="MEMBER">Участник</option>' +
                        '<option value="MODER">Модератор</option>' +
                        '<option value="ADMIN">Администратор</option>' +
                        '</select>'
                },
            ]
        });

        $('#group-admin tbody').on('click', 'select', 'change', function () {
            const data = table.row($(this).parents('tr')).data();
            let selectMember = data.id;
            let role = $(this).find(":selected").val();
            $.ajax({
                type: "GET",
                url: newUrl + '/update-role-member/' + selectMember + '/' + role
            });
        });

        $('#group-admin tbody').on('click', 'button', function () {
            const data = table.row($(this).parents('tr')).data();
            var id = data.id;
            $.ajax({
                type: "DELETE",
                url: newUrl + '/delete-member/' + id
            });
            table.ajax.reload(function (json) {
                $('#myInput').val(json.lastInput);
            });
        });
    });

</script>
<%@ include file="../footers.jsp" %>
</body>
</html>